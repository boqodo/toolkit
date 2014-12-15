package z.cube.utils;


import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

public class ClassFileScanner {
    private static final int PSF_MODIFIERS = Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL;
    private static ClassFileScanner instance=null;

    // 私有化构造函数创建单例
    private ClassFileScanner() {
    }
    public static ClassFileScanner newInstance(){
        if(instance==null){
            instance=new ClassFileScanner();
        }
        return instance;
    }

    /**
     * 加载classpath下所有类中的以`PSF_MODIFIERS`修饰的字段
     * 提取字段名称和值存储为map
     * @see ClassFileScanner#PSF_MODIFIERS
     */
    public static Map<String,Object> loadConstants(){
        return loadConstants(getClasspathName());
    }

    /**
     * 加载rootPath下所有类中的以`PSF_MODIFIERS`修饰的字段
     * @param rootPath      类文件所在目录
     */
    public static Map<String,Object> loadConstants(String rootPath){
        return loadConstants(new File(rootPath));
    }

    /**
     * 加载rootPath下所有类中的以`PSF_MODIFIERS`修饰的字段
     * @param rootPath     类文件所在目录
     * @return
     */
    public static Map<String,Object> loadConstants(File rootPath){
        Map<String, Object> kv = new HashMap<String, Object>();
        newInstance().listClassFile(rootPath, kv);
        return kv;
    }

    private static String getClasspathName() {
        URL classpath=ClassFileScanner.class.getClassLoader().getResource(".");
        return new File(classpath.getFile()).getAbsolutePath()+File.separator;
    }

    /**
     * 分析类信息
     * @param clazzName     类全称字符串
     * @param kv            存放信息的map
     */
    private void analyzeClass(String clazzName, final Map<String, Object> kv) {
        try {
            final Class clazz = Class.forName(clazzName);
            final Map<String, Object> constValueMap = new HashMap<String, Object>(16);

            if (clazz.isEnum()) {
                 /* 1. Enum特殊处理,获取所有实例提取属性字段 2. Annotation注解为接口 */
                constValueMap.putAll(analyzeEnum(clazz));
            } else {
                constValueMap.putAll(analyzeNormal(clazz));
            }
            if (!constValueMap.isEmpty()) kv.put(clazz.getSimpleName(), constValueMap);
        } catch (ClassNotFoundException e) {
            convertException(e,"未找到%s类!",clazzName);
        }
    }

    /**
     * 分析枚举的情况
     */
    private Map<String, Object> analyzeEnum(Class clazz) {
        Map<String, Object> constValueMap = new HashMap<String, Object>(16);

        try {
            // 获取枚举类型的所有实例
            String $VALUES = "$VALUES";
            Field valuesField = clazz.getDeclaredField($VALUES);
            valuesField.setAccessible(true);
            Enum[] enums = (Enum[]) valuesField.get(null);

            // 获取所有枚举实例名称(实例通过字段存放)
            List<String> enumNames = new ArrayList<String>(enums.length);
            for (Enum item : enums) {
                enumNames.add(item.name());
            }

            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
            if (fields.isEmpty()) return constValueMap;

            List<Field> customFields = new ArrayList<Field>(2);
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (PSF_MODIFIERS == field.getModifiers()) {
                    constValueMap.put(fieldName, field.get(null));
                } else if (!enumNames.contains(fieldName)
                        && !$VALUES.equals(fieldName)) {
                    customFields.add(field);
                }
            }

            // 针对具体的枚举实例，获取自定义字段设置的相应值
            for (Enum item : enums) {
                Map<String, Object> fm = new HashMap<String, Object>(2);
                for (Field cf : customFields) {
                    cf.setAccessible(true);
                    Object val = cf.get(item);
                    fm.put(cf.getName(), val);
                }
                constValueMap.put(item.name(), fm);
            }
        } catch (IllegalAccessException e) {
            convertException(e,"未有访问权限!");
        } catch (NoSuchFieldException e) {
            convertException(e,"未在类中查询到相应的字段字段!");
        }

        return constValueMap;
    }

    /**
     * 分析常见类的信息
     */
    private Map<String, Object> analyzeNormal(final Class clazz) {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());    //不取继承父类或接口的字段
        final Map<String, Object> constValueMap = new HashMap<String, Object>(16);
        final boolean unInstance = clazz.isInterface()
                || Modifier.isAbstract(clazz.getModifiers());
        CollectionUtils.forAllDo(fields, new Closure() {
            @Override
            public void execute(Object input) {
                Field field = (Field) input;
                if (field.getModifiers() == PSF_MODIFIERS) {
                    try {
                        field.setAccessible(true);
                        String key = field.getName();

                        Object value = field.get(unInstance ? null : clazz.newInstance());
                        constValueMap.put(key, value);
                    } catch (IllegalAccessException e) {
                        convertException(e,"未有访问权限!");
                    } catch (InstantiationException e) {
                        convertException(e,"未能实例化!");
                    }
                }
            }
        });
        return constValueMap;
    }

    /**
     * 遍历目录寻找class文件
     * TODO: 提取成获取class文件的方法
     * @param classpath
     * @param kv
     */
    private void listClassFile(File classpath, Map<String, Object> kv) {
        File[] files = classpath.listFiles();
        if (kv == null) {
            kv = new HashMap<String, Object>();
        }
        for (File file : files) {
            if (file.isDirectory()) {
                listClassFile(file, kv);
            } else {
                if (FilenameUtils.isExtension(file.getName(), "class")) {
                    String pname = StringUtils.replace(file.getAbsolutePath(), getClasspathName(), "")
                            .replace(File.separatorChar, '.');
                    pname = FilenameUtils.removeExtension(pname);
                    analyzeClass(pname, kv);
                }
            }
        }
    }

    /**
     * 转换异常为运行时异常
     * @param e             异常实例
     * @param message       异常信息
     * @param args          异常信息格式化参数
     */
    private void convertException(Exception e,String message,Object ...args){
        throw new RuntimeException(String.format(message,args),e);
    }
}

package z.cube.utils;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.TruePredicate;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {

    /**
     * 获取加载器中当前所有已加载的类
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Class> getLoadedClasses(){
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class clazz = classLoader.getClass();
            while (clazz != ClassLoader.class) {
                clazz = clazz.getSuperclass();
            }
            Field field = clazz.getDeclaredField("classes");
            field.setAccessible(true);
            return (List<Class>) field.get(classLoader);
        } catch (IllegalAccessException e) {
            throw convertException(e);
        } catch (NoSuchFieldException e) {
            throw convertException(e);
        }
    }
    public static List<Class> listClasses(File classFile) {
        List<Class> store=new  ArrayList<Class>(64);
        listClasses(classFile,store, TruePredicate.getInstance());
        return store;
    }
    public static List<Class> listClasses(File classFile,Predicate filter){
        List<Class> store=new  ArrayList<Class>(64);
        listClasses(classFile,store, filter);
        return store;
    }
    private static void listClasses(File classFile,List<Class> store,Predicate filter) {
        File[] files = classFile.listFiles();
        if(store==null){
            store=new ArrayList<Class>(64);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                listClasses(file, store,filter);
            } else {
                if (FilenameUtils.isExtension(file.getName(), "class")) {
                    String clazzName = StringUtils.replace(file.getAbsolutePath(), getClasspathName(), "")
                            .replace(File.separatorChar, '.');
                    clazzName = FilenameUtils.removeExtension(clazzName);
                    try {
                        Class clazz = Class.forName(clazzName);
                        if(filter.evaluate(clazz)){
                            store.add(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        throw convertException(e);
                    }
                }
            }
        }
    }
    private static String getClasspathName() {
        URL classpath=ClassFileScanner.class.getClassLoader().getResource(".");
        return new File(classpath.getFile()).getAbsolutePath()+File.separator;
    }

    private static RuntimeException convertException(Exception e) {
        String message = e.getMessage();
        if (e instanceof IllegalAccessException) {
            message = "未有访问权限!";
        } else if (e instanceof NoSuchFieldException) {
            message = "未在类中查询到相应的字段字段!";
        }else if(e instanceof ClassNotFoundException){
            message="未找到相应的类!";
        }
        return new RuntimeException(message, e);
    }

}

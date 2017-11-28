package z.cube.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JavaPojo2FlexVO {

    public JavaPojo2FlexVO() {
    }

    // 根据java类对象的类型返回vo类型
    private static String getClassType(Class<?> c) {
        String typeName = c.getSimpleName();

        if (typeName.equals("String") || typeName.equals("Date")) {
            return typeName;
        } else if (typeName.equalsIgnoreCase("BigDecimal")
                || typeName.equalsIgnoreCase("Decimal")
                || typeName.equalsIgnoreCase("Double")
                || typeName.equalsIgnoreCase("Long")) {
            return "Number";
        } else if (typeName.equals("Integer") || typeName.equals("int")) {
            return "int";
        } else if (typeName.equals("Boolean") || typeName.equals("boolean")) {
            return "Boolean";
        } else if (typeName.equals("Timestamp")) {
            return "Date";
        } else if (typeName.equals("List") || typeName.equals("ArrayList")
                || typeName.equals("LinkedList") || typeName.equals("Set")
                || typeName.equals("HashSet")) {
            return "ArrayCollection";

        } else if (!c.isPrimitive()) {
            return typeName;
        } else {
            return "*";// 其它类型的设置为未知类型
        }

    }

    // 重复c字符count次，用于格式化生成的as文件
    private static String repeat(String c, int count) {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < count; i++) {
            temp.append(c);
        }

        return temp.toString();
    }

    /**
     * 生成as文件
     *
     * @param pojoName
     *         java对象名称
     * @param packageName
     *         flex中vo对象的包名,如果为空则为javabean所在包包名
     *
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static void generateAsFile(String pojoName, String packageName,
                                      String folder, boolean bindable) throws ClassNotFoundException,
            IOException {
        Class<?> c = Class.forName(pojoName);
        Field[] fields = c.getDeclaredFields();

        // as的vo对象名称结尾加上VO标志
        File f = new File(folder + c.getSimpleName() + ".as");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        // 包名，没有设置包名就取和java pojo一样的包名
        if (StringUtils.isEmpty(packageName)) {
            packageName = c.getPackage().getName();
        }
        bw.write("package " + packageName + "\n{\n");

        // as vo java pojo映射
        if (bindable) {
            bw.write(repeat(" ", 4) + "[Bindable]\n");
        }
        bw.write(repeat(" ", 4) + "[RemoteClass(alias=\""
                + c.getPackage().getName() + "." + c.getSimpleName() + "\")]\n");
        // 写类
        bw.write(repeat(" ", 4) + "public class " + c.getSimpleName() + "\n");
        bw.write(repeat(" ", 4) + "{\n");

        // 写属性
        for (int i = 0; i < fields.length; i++) {
            Class<?> fieldType = fields[i].getType();
            String typeName = getClassType(fieldType);
            bw.write(repeat(" ", 8) + "private var _" + fields[i].getName()
                    + ":" + typeName + ";\n");
        }

        bw.write("\n\n\n");
        // 写空的构造函数
        bw.write(repeat(" ", 8) + "public function " + c.getSimpleName()
                + "(){}\n\n");

        // 写 setter/getter 方法
        for (int i = 0; i < fields.length; i++) {
            Class<?> fieldType = fields[i].getType();
            String typeName = getClassType(fieldType);
            // setter
            bw.write(repeat(" ", 8) + "public function set "
                    + fields[i].getName() + "(value:" + typeName + "):void{\n");
            bw.write(repeat(" ", 12) + "this._" + fields[i].getName()
                    + " = value;\n");
            bw.write(repeat(" ", 8) + "}\n\n");
            // getter
            bw.write(repeat(" ", 8) + "public function get "
                    + fields[i].getName() + "():" + typeName + "{\n");
            bw.write(repeat(" ", 12) + "return this._" + fields[i].getName()
                    + ";\n");
            bw.write(repeat(" ", 8) + "}\n\n\n");

        }
        bw.write(repeat(" ", 4) + "}\n");
        bw.write("}");
        bw.close();
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param pack
     *
     * @return
     */
    private static Set<Class<?>> getClasses(String pack) {

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader()
                    .getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    System.err.println("file类型的扫描");
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath,
                            recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    System.err.println("jar类型的扫描");
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx)
                                            .replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class")
                                            && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(
                                                packageName.length() + 1,
                                                name.length() - 6);
                                        try {
                                            // 添加到classes
                                            System.out.println(className);
                                            classes.add(Class
                                                    .forName(packageName + '.'
                                                            + className));
                                        } catch (ClassNotFoundException e) {
                                            // log
                                            // .error("添加用户自定义视图类错误 找不到此类的.class文件");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private static void findAndAddClassesInPackageByFile(String packageName,
                                                         String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            System.out.println("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(
                        packageName + "." + file.getName(),
                        file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    // classes.add(Class.forName(packageName + '.' +
                    // className));
                    // 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader()
                            .loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException,
            IOException {
        Set<Class<?>> clasess = null;

        // 以下参数自行设置
        String pojoPack = "cn.zjnu.ssj.domain";    //pojo包名
        String asPack = "cn.zjnu.ssj.acvo";        //as包名
        String filePath = "D:\\dev\\tools\\zcube\\spring-data-jpa\\src\\";                //文件生成位置

        clasess = getClasses(pojoPack);
        for (Class<?> clazz : clasess) {
            JavaPojo2FlexVO.generateAsFile(clazz.getName(), asPack,
                    filePath, true);
        }
    }
}

package z.cube.utils;

import java.lang.reflect.Field;
import java.util.List;

public class ClassUtils {

    /**
     * 获取加载器中当前所有的加载类
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Class> getAllLoadClasses(){
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class clazz = classLoader.getClass();
            while (clazz != ClassLoader.class) {
                clazz = clazz.getSuperclass();
            }
            Field field = clazz.getDeclaredField("classes");
            field.setAccessible(true);
            return (List<Class>) field.get(classLoader);
        }catch (Exception e){
            return null;
        }
    }
}

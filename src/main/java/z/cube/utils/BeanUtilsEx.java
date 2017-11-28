package z.cube.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;


public class BeanUtilsEx {
    public static String getEntityTableName(Class<?> clazz) {
        return AnnotationUtils.findAnnotation(clazz, Table.class).name();
    }
    
    /**
     * 由类字段和具体值组成的Map
     * 
     * {@link BeanUtils#describe} 
     * 该方法是将字段和转为字符串的值组成的map，
     * 并且列表转字符串时，只去列表第一个值
     * @param bean
     * @return
     * @throws Exception
     */
    public static Map<String,Object> describe(Object bean) throws Exception {
		Map<String,Object> description=new HashMap<String,Object>();
		PropertyDescriptor descriptors[] = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			if (PropertyUtils.getReadMethod(descriptors[i]) != null
					&& !"class".equals(name)){
				description.put(name, PropertyUtils.getProperty(bean, name));
			}
		}
		return description;
	}
    
    /**
     * 使用Map中的值填充T属性
     * @see org.apache.commons.beanutils.BeanUtils#populate(Object, Map)
     * @param clazz
     * @param properties
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static <T> T populate(Class<T> clazz,Map properties){
    	T t=null;
    	try {
    		t=clazz.newInstance();
    		BeanUtilsBean.getInstance().populate(t, properties);
		} catch (Exception e) {
			
		}
    	return t;
    }
    /**
     * 使用Map中的值填充T属性
     * @see org.apache.commons.beanutils.BeanUtils#populate(Object, Map)
     * @param t
     * @param properties
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static <T> T populate(T t,Map properties){
    	try {
    		  BeanUtilsBean.getInstance().populate(t, properties);
		} catch (Exception e) {
			
		}
    	return t;
    }
    /**
     * 复制source中非空的字段值到target
     * 注：spring的BeanUtils不过滤非空值
     * @see BeanUtils#copyProperties(Object, Object, String...)
     * @param source
     * @param target
     * @param <T>
     */
    public static  <T> void copyPropertiesIgnoreNullValue(T source,T target){
        PropertyDescriptor[] pds= BeanUtils.getPropertyDescriptors(target.getClass());
        for(PropertyDescriptor targetPd:pds){
            Method writeMethod = targetPd.getWriteMethod();
            if(writeMethod!=null){
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if(value!=null) writeMethod.invoke(target, value);
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }
}

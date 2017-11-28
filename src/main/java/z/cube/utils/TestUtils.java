package z.cube.utils;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;


import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.PodamParameterizedType;

/**
 *	用于测试，生成一个类的实例
 *	
 *	该实例是填充了相应的类型的随机数据
 */
public class TestUtils {
	
	@SuppressWarnings("rawtypes")
	private static Type toGenericTypes(Type type) {
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			return new PodamParameterizedType((Class) pt.getRawType(),
					toGenericTypes(pt.getActualTypeArguments()));
		} else {
			return type;
		}
	}

	private static Type[] toGenericTypes(Type[] types) {
		Type[] rt = new Type[types.length];
		for (int i = 0; i < types.length; i++) {
			Type t = types[i];
			rt[i] = toGenericTypes(t);
		}
		return rt;
	}
	/**
	 * 构建一个类实例
	 * @param rclazz
	 * @return
	 */
	public static <T> T build(Class<T> rclazz){
		return build(rclazz,null);
	}
	
	/**
	 * 构建一个包含泛型的复杂类实例
	 * @param rclazz
	 * @param grType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T build(Class<T> rclazz,Type grType){
		PodamFactory pf = new PodamFactoryImpl();
		Type[] t = null;
		if(grType instanceof ParameterizedType){
			ParameterizedType pt = (ParameterizedType) grType;
			t = toGenericTypes(pt.getActualTypeArguments());
		}else{
			t= new Type[]{grType};
		}
		//接口类无法实例化，需要指定可是实例化的子类
		if(rclazz.isInterface()){
			if(Iterable.class.isAssignableFrom(rclazz)){
				rclazz = (Class<T>) ArrayList.class;
			}else if(Map.class.isAssignableFrom(rclazz)){
				rclazz = (Class<T>) LinkedHashMap.class;
			}
			// 根据需要再补充
		}
		
		return pf.manufacturePojo(rclazz, t);
	}
}

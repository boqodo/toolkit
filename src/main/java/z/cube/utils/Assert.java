package z.cube.utils;

import java.util.Collection;



public class Assert {
	
	public static boolean isNotNull(Object object){
		if (object == null){
			return false;
		}else if(object instanceof Number){
			if(((Number)object).intValue() == 0){
				return false;
			}
		}
		return true;
	}
	public static boolean isNull(Object object){
		return !isNotNull(object);
	}
	public static void notNull(Object object, String message,Object ...formatVals) {
		if (object == null){
			throw new IllegalArgumentException(String.format(message, formatVals));
		}else if(object instanceof Number){
			if(((Number)object).intValue() == 0){
				throw new IllegalArgumentException(String.format(message, formatVals));
			}
		}
	}
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0){
			return true;
		}
		for (int i = 0; i < strLen; i++){
			if (!Character.isWhitespace(str.charAt(i))){
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	public static void notBlank(String str, String message,Object ...formatVals) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0){
			throw new IllegalArgumentException(String.format(message, formatVals));
		}
		for (int i = 0; i < strLen; i++){
			if (!Character.isWhitespace(str.charAt(i))){
				return;
			}
		}
		throw new IllegalArgumentException(String.format(message, formatVals));
	}
	
	public static boolean isArray(Object obj) {
		return obj.getClass().isArray();
	}

	public static boolean isCollection(Object obj) {
		return obj instanceof Collection;
	}
	public static void isTrue(boolean expression, String message,Object ...formatVals) {
		if (!expression){
			throw new IllegalArgumentException(String.format(message, formatVals));
		}
	}
}

package z.cube.utils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateFormatUtils {
	public static final String ORACLE_DATE_FORMAT="yyyy-MM-dd HH24:mi:ss";
	public static final String JAVA_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	public static final String FLEX_DATA_FORMAT="YYYY-MM-DD JJ:NN:SS";
	
	/**
	 * 判断某字符串是否符合需要的日期格式
	 * @param inputStr				字符串
	 * @param parsePatterns		日期格式数组
	 * @return	
	 */
	public static boolean isDateString(String inputStr,String[] parsePatterns){
		return paraseDateString(inputStr,parsePatterns)!=null;
	}
	/**
	 * 判断某字符串是否符合需要的日期格式
	 * 	日期格式使用yyyyMMdd和yyyy-MM-dd
	 * @param inputStr
	 * @return
	 */
	public static boolean isDateString(String inputStr){
		String[] parsePatterns=new String[]{"yyyyMMdd","yyyy-MM-dd"};
		return isDateString(inputStr,parsePatterns);
	}
	/**
	 * 判断某字符串是否为日期字符串
	 * @param inputStr	校验的字符串
	 * @return
	 */
	public static boolean isNotDateString(String inputStr){
		return !isDateString(inputStr);
	}
	/**
	 * 解析字符串返回符合格式化要求的日期
	 * @param inputStr		日期字符串
	 * @param parsePatterns	日期格式化
	 * @return 格式化后的日期
	 */
	public static Date paraseDateString(String inputStr,String[] parsePatterns){
		Date result=null;
		try {
			result=DateUtils.parseDate(inputStr, parsePatterns);
		} catch (ParseException e) {
		}
		return result;
	}
}

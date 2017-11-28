/* ==================================================================   
 * Created Feb 2, 2015 by KingSoft
 * ==================================================================  
 * FAP_NEW
 * ================================================================== 
 * FAP_NEW  License v1.0  
 * Copyright (c) Gsoft S&T Co.ltd HangZhou, 2012-2013 
 * ================================================================== 
 * 杭州掌拓科技有限公司拥有该文件的使用、复制、修改和分发的许可权
 * ================================================================== 
 */
package z.cube.format;

import org.apache.commons.lang.StringUtils;

public class NumberToChineseUtil {
	
	private static final int DECIMAL_DIGITS=Constants.DECIMAL_DIGITS;
	
	private static final String ZERO = "0";
	private static final String CN_ZERO = "零";
	private static final String CN_ONE = "壹";
	private static final String CN_TWO = "贰";
	private static final String CN_THREE = "叁";
	private static final String CN_FOUR = "肆";
	private static final String CN_FIVE = "伍";
	private static final String CN_SIX = "陆";
	private static final String CN_SEVEN = "柒";
	private static final String CN_EIGHT = "捌";
	private static final String CN_NINE = "玖";
	private static final String CN_TEN = "拾";
	private static final String CN_HUNDRED = "佰";
	private static final String CN_THOUSAND = "仟";
	private static final String CN_TEN_THOUSAND = "万";
	private static final String CN_HUNDRED_MILLION = "亿";
	private static final String CN_SYMBOL = "";
	private static final String CN_DOLLAR = "元";
	private static final String CN_TEN_CENT = "角";
	private static final String CN_CENT = "分";
	private static final String CN_INTEGER = "整";
	
	private static final String[] digits=new String[]{
		CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE
	};
	private static final String[] radices=new String[]{
		"", CN_TEN, CN_HUNDRED, CN_THOUSAND
	};
	private static final String[] bigRadices=new String[]{
		"", CN_TEN_THOUSAND, CN_HUNDRED_MILLION
	};
	private static final String[] decimals=new String[]{
		CN_TEN_CENT, CN_CENT
	}; 
	
	public static String substr(String str,int startIndex,int length){
		if(startIndex+length>str.length()){
			return "";
		}
		return str.substring(startIndex,startIndex+length);
	}
	public static String convert(String value){
		String[] parts=value.split("[.]");
		//整数部分
		String integral = null;	
		//小数部分
		String decimal= null;
		if(parts.length==2){
			integral=parts[0];
			decimal=parts[1];
			decimal=substr(decimal,0,DECIMAL_DIGITS);
		}else if(parts.length==1){
			integral=parts[0];
			decimal=""; 
		}else{
			//exception
		}
		String outputCharacters="";
		int zeroCount=0;
		if(Double.valueOf(integral)>0){
			int integralLength=integral.length();
			for (int i=0; i <integralLength ; i++)
			{
				int p = integralLength - i - 1;
				String d = substr(integral,i, 1);
				int quotient = p / 4;
				int modulus = p % 4;
				if(d.equals(ZERO))
				{
					zeroCount ++;
				}
				else
				{
					if (zeroCount > 0)
					{
						outputCharacters += digits[0];
					}
					zeroCount = 0;
					outputCharacters += digits[Integer.valueOf(d)] + radices[modulus];
				}
				if (modulus == 0 && zeroCount < 4)
				{
					outputCharacters += bigRadices[quotient];
				}
			}
			outputCharacters += CN_DOLLAR;
		}
		//小数点后面 
		decimal = StringUtils.leftPad(decimal, DECIMAL_DIGITS, ZERO);
		String str = "";
		for (int j = 0; j < decimal.length(); j++)
		{
			String d = substr(decimal,j, 1);
			if ( d != null&&!"".equals(d))
			{
				str += digits[Integer.valueOf(d)] + decimals[j];
			}
		}
		if(str.indexOf(CN_ZERO+CN_TEN_CENT)>-1 && str.indexOf(CN_ZERO+CN_CENT)>-1)
		{
			outputCharacters += CN_INTEGER;
		}
		else if(!(str.indexOf(CN_ZERO+CN_TEN_CENT)>-1) && str.indexOf(CN_ZERO+CN_CENT)>-1)
		{
			outputCharacters += digits[Integer.valueOf(substr(decimal,0, 1))] + decimals[0] + CN_INTEGER;
		}
		else
		{
			outputCharacters += str;
		}
		if ("".equals(outputCharacters))
		{
			outputCharacters = CN_ZERO + CN_DOLLAR;
		}
		outputCharacters=CN_SYMBOL + outputCharacters;
		
		return outputCharacters;
	}
}

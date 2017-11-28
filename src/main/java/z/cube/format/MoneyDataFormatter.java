/* ==================================================================   
 * Created Jan 30, 2015 by KingSoft
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

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**
 * 金额格式化
 */
public class MoneyDataFormatter implements DataFormatter {

	/* (non-Javadoc)
	 * @see com.ksoft.printer.format.DataFormatter#format(java.lang.Object, java.lang.Object)
	 */
	@Override
	public String format(Object value, Object component) {
		String s="";
		if(value!=null && StringUtils.isNotBlank(value.toString())){
			PrintTextInput moneyText=(PrintTextInput) component;
			
			//根据类型格式化
			String moneyType=moneyText.getMoneyType();
			Double dValue = toDouble(value.toString());
			
			if(PrintTextInput.MONTYTYPE_NORAML.equals(moneyType)){
				s=toNormal(dValue);
			}else if(PrintTextInput.MONTYTYPE_FORMAT.equals(moneyType)){
				s = toFormat(dValue);
			}else if(PrintTextInput.MONTYTYPE_CHINESE.equals(moneyType)){
				s=toChinese(toNormal(dValue));
			}else{
				throw new RuntimeException(String.format("[%s]不支持的金额格式化方式!",moneyType));
			}
			
			//设置货币标志
			boolean isShow=PrintTextInput.SHOWHB_DISPLAY.equals(moneyText.getShowHb());
			if(isShow){
				s=moneyText.getHbType()+s;
			}
		}
		return s;
	}
	private String toFormat(Double dValue) {
		String s;
		DecimalFormat df=new DecimalFormat("###,##0."+StringUtils.leftPad("", Constants.DECIMAL_DIGITS, '0')); 
		s=df.format(dValue);
		return s;
	}
	private String toNormal(Double dValue) {
		return String.format("%."+Constants.DECIMAL_DIGITS+"f", dValue);
	}
	private String toChinese(String dValue) {
		return NumberToChineseUtil.convert(dValue.toString());
	}
	private Double toDouble(String value){
		//除去传入的逗号
		if(value.contains(",")){
			value=value.replaceAll(",", "");
		}
		return Double.valueOf(value);
	}

}

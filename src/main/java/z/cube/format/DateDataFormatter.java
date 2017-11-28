/* ==================================================================   
 * Created Mar 3, 2015 by KingSoft
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

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateDataFormatter implements DataFormatter {
	
	private static final Map<String,String> DATATYPE_FORMATSTYLE=new HashMap<String, String>(){
		{
			this.put(MoveDateInput.DATETYPE_DEFAULT, "yyyy-MM-dd");
			this.put(MoveDateInput.DATETYPE_STYLE2, "yyyy/MM/dd");
			this.put(MoveDateInput.DATETYPE_STYLE3, "yyyy年MM月dd日");
			this.put(MoveDateInput.DATETYPE_STYLE4, "yyyyMMdd");
			this.put(MoveDateInput.DATETYPE_STYLE5, "yyyy");
			this.put(MoveDateInput.DATETYPE_STYLE6, "yyyyMM");
			this.put(MoveDateInput.DATETYPE_STYLE7, "MMdd");
		}
	};

	/* (non-Javadoc)
	 * @see com.ksoft.printer.format.DataFormatter#format(java.lang.Object, java.lang.Object)
	 */
	@Override
	public String format(Object value, Object component) {
		String res="";
		if(value!=null){
			MoveDateInput mdi=(MoveDateInput) component;
			String dateType=mdi.getDateType();
			if(value instanceof String){
				res = parseStringFormat(value, dateType);
			}else if(value instanceof Date){
				res=DateFormatUtils.format((Date)value, DATATYPE_FORMATSTYLE.get(mdi.getDateType()));
			}else if(value instanceof Number){
				Number t=(Number) value;
				res=DateFormatUtils.format(new Date(t.longValue()), DATATYPE_FORMATSTYLE.get(mdi.getDateType()));
			}
			else{
				throw new RuntimeException(String.format("不支持[%s]类型的对象格式化处理!",value.getClass().getSimpleName()));
			}
		}
		return res;
	}

	private String parseStringFormat(Object value, String dateType) {
		String ts=(String) value;
		try {
			Date temp=DateUtils.parseDate(ts, 
					new String[]{
						DATATYPE_FORMATSTYLE.get(MoveDateInput.DATETYPE_DEFAULT),
						DATATYPE_FORMATSTYLE.get(MoveDateInput.DATETYPE_STYLE2),
						DATATYPE_FORMATSTYLE.get(MoveDateInput.DATETYPE_STYLE3),
						DATATYPE_FORMATSTYLE.get(MoveDateInput.DATETYPE_STYLE4)});
			ts=DateFormatUtils.format(temp, DATATYPE_FORMATSTYLE.get(dateType));
		} catch (ParseException e) {
			throw new RuntimeException(String.format("[%s]无法进行日期格式化!",ts));
		}
		return ts;
	}

}

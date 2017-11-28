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


public class DefaultDataFormatter implements DataFormatter {

	/* (non-Javadoc)
	 * @see com.ksoft.printer.util.DataFormatter#format(java.lang.Object, java.lang.Object)
	 */
	@Override
	public String format(Object value, Object component) {
		String s="";
		if(value!=null){
			s=value.toString();
		}
		return s;
	}
}

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

import java.util.HashMap;
import java.util.Map;

public interface DataFormatter {
	@SuppressWarnings("serial")
	public static final Map<String,DataFormatter> FACTORY=new HashMap<String, DataFormatter>(){
		{
			this.put(Constants.TEXTINPUTTYPE_NORMAL, new DefaultDataFormatter());
			this.put(Constants.TEXTINPUTTYPE_MONEY, new MoneyDataFormatter());
			this.put(Constants.TEXTINPUTTYPE_DATE, new DateDataFormatter());
		}
	};
	
	String format(Object value, Object component);
}

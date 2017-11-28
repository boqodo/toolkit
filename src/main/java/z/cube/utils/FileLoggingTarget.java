/* ==================================================================   
 * Created Nov 17, 2014 by KingSoft
 * ==================================================================  
 * FAP_NEW
 * ================================================================== 
 * FAP_NEW  License v1.0  
 * Copyright (c) Gsoft S&T Co.ltd HangZhou, 2012-2013 
 * ================================================================== 
 * 杭州掌拓科技有限公司拥有该文件的使用、复制、修改和分发的许可权
 * ================================================================== 
 */
package z.cube.utils;

import org.apache.log4j.Logger;

import flex.messaging.log.LineFormattedTarget;

public class FileLoggingTarget extends LineFormattedTarget {
	private transient final Logger log ;
	
	public FileLoggingTarget(){
		log=Logger.getLogger(getClass());
	}
	@Override
	protected void internalLog(String s) {
		log.debug(s);
	}
}

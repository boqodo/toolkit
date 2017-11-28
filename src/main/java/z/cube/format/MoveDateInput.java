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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 日期组件
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MoveDateInput extends PrintTextInput{
	
	public static final String SETSYSDATE_OFF =  "0";	//默认不预制
	public static final String DATETYPE_DEFAULT  = "1";	//默认日期格式为2014-01-01
	public static final String DATETYPE_STYLE2 	 = "2";	//2014/01/01
	public static final String DATETYPE_STYLE3 	 = "3";	//2014年01月01日
	public static final String DATETYPE_STYLE4 	 = "4";	//20140101
	public static final String DATETYPE_STYLE5 	 = "5";	//yyyy
	public static final String DATETYPE_STYLE6 	 = "6";	//yyyyMM
	public static final String DATETYPE_STYLE7 	 = "7";	//MMdd
	
	@XmlAttribute private String setSysDate=SETSYSDATE_OFF;	//是否预制，如果是则使用系统日期
	@XmlAttribute private String dateType;					//日期格式化类型
	@XmlAttribute private String fixedDate;					//固定日期
	
	public String getSetSysDate() {
		return setSysDate;
	}
	public void setSetSysDate(String setSysDate) {
		this.setSysDate = setSysDate;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getFixedDate() {
		return fixedDate;
	}
	public void setFixedDate(String fixedDate) {
		this.fixedDate = fixedDate;
	}
	@Override
	public String toString() {
		return "MoveDateInput [setSysDate=" + setSysDate + ", dateType=" + dateType + ", fixedDate=" + fixedDate + "]";
	}
}

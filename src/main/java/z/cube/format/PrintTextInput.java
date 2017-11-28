/* ==================================================================   
 * Created Dec 8, 2014 by KingSoft
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
import javax.xml.bind.annotation.XmlTransient;

/**
 * 文本输入组件
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PrintTextInput {
	private static final String 	TYPE_NORMAL=Constants.TEXTINPUTTYPE_NORMAL;
	private static final String 	TYPE_MONEY=Constants.TEXTINPUTTYPE_MONEY;

	
	@XmlAttribute private String 	type				;	//	
	@XmlAttribute private String 	textName			;	//		
	@XmlAttribute private String 	fontFamily			;	//		
	@XmlAttribute private Integer 	fontSize			;	//		
	@XmlAttribute private String 	color				;	//	
	@XmlAttribute private String 	textAlign			;	//		
	@XmlAttribute private String 	defaultValue		;	//			
	@XmlAttribute private String 	dataType			;	//		
	@XmlAttribute private String 	adaptMode			;	//	适应方式	
	@XmlAttribute private String 	isLock				;	//	
	@XmlAttribute private String 	businessType		;	//			
	@XmlAttribute private String 	businessCode		;	//			
	@XmlAttribute private String 	isPrint				;	//	是否打印，默认值1,表示打印；null或0表示不打印
	@XmlAttribute private Integer 	width				;	//	
	@XmlAttribute private Integer 	height				;	//	
	@XmlAttribute private Integer 	x					;	//	
	@XmlAttribute private Integer 	y					;	//	
	@XmlAttribute private String 	uid					;	//
	
	@XmlAttribute private String 	fontWeight			;	//	粗体
	@XmlAttribute private String 	fontStyle			;	//	斜体
	
	
	// 金额文本框的特殊属性
	public static final   String	SHOWHB_HIDDEN="0"	; 	//不显示货币
	public static final   String	SHOWHB_DISPLAY="1"	; 	//显示货币
	
	// 金额格式类型
	public static final   String	MONTYTYPE_FORMAT="1";	//897,898.00
	public static final   String	MONTYTYPE_NORAML="2";	//897898.00
	public static final   String	MONTYTYPE_CHINESE="3";	//壹仟贰佰伍拾圆柒角捌分
	
	@XmlAttribute private String 	moneyType			;	//金额格式
	@XmlAttribute private String 	showHb=SHOWHB_HIDDEN;	//是否显示货币(默认不显示,0)
	@XmlAttribute private String 	hbType				;	//货币符号类型
	
	
	@XmlTransient private String 	value;				;	//打印值
	@XmlTransient private Integer 	verSpace			;	//间距
	@XmlTransient private String	jsType=this.getClass().getSimpleName();	//对应js中的对象类型
	
	@XmlTransient
	public boolean isMoneyText(){
		return TYPE_MONEY.equals(this.type);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public Integer getFontSize() {
		return fontSize;
	}
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTextAlign() {
		return textAlign;
	}
	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getAdaptMode() {
		return adaptMode;
	}
	public void setAdaptMode(String adaptMode) {
		this.adaptMode = adaptMode;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getVerSpace() {
		return verSpace;
	}
	public void setVerSpace(Integer verSpace) {
		this.verSpace = verSpace;
	}
	public String getJsType() {
		return jsType;
	}
	public void setJsType(String jsType) {
		this.jsType = jsType;
	}
	public String getFontWeight() {
		return fontWeight;
	}
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}
	public String getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getShowHb() {
		return showHb;
	}
	public void setShowHb(String showHb) {
		this.showHb = showHb;
	}
	public String getHbType() {
		return hbType;
	}
	public void setHbType(String hbType) {
		this.hbType = hbType;
	}
	@Override
	public String toString() {
		return "PrintTextInput [type=" + type + ", textName=" + textName + ", fontFamily=" + fontFamily + ", fontSize="
				+ fontSize + ", color=" + color + ", textAlign=" + textAlign + ", defaultValue=" + defaultValue
				+ ", dataType=" + dataType + ", adaptMode=" + adaptMode + ", isLock=" + isLock + ", businessType="
				+ businessType + ", businessCode=" + businessCode + ", isPrint=" + isPrint + ", width=" + width
				+ ", height=" + height + ", x=" + x + ", y=" + y + ", uid=" + uid + "]";
	}
}

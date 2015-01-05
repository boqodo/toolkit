/* ==================================================================   
 * Created Nov 18, 2014 by KingSoft
 * ==================================================================  
 * FAP_NEW
 * ================================================================== 
 * FAP_NEW  License v1.0  
 * Copyright (c) Gsoft S&T Co.ltd HangZhou, 2012-2013 
 * ================================================================== 
 * 杭州掌拓科技有限公司拥有该文件的使用、复制、修改和分发的许可权
 * ================================================================== 
 */
package z.cube.podam;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "req")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "req", propOrder = { "ansback", 
		"main", "detail"})
public class ExReq {
	@XmlAttribute(name = "ansback")
	private String ansback="no";
	@XmlElement(name = "main")
	private ExMain main;
	@XmlElement(name = "detail")
	private ExDetail detail;
	public String getAnsback() {
		return ansback;
	}
	public void setAnsback(String ansback) {
		this.ansback = ansback;
	}
	public ExMain getMain() {
		return main;
	}
	public void setMain(ExMain main) {
		this.main = main;
	}
	public ExDetail getDetail() {
		return detail;
	}
	public void setDetail(ExDetail detail) {
		this.detail = detail;
	}
	
	
}

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
package java.z.cube.podam;

import z.cube.podam.ExPub;
import z.cube.podam.ExReq;

import java.io.Serializable;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "package")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "package", propOrder = { "version","pub", "req" })
public class ExPackage implements Serializable {
	@XmlAttribute(name = "version")
	private String version="1.0";

	@XmlElement(name = "pub")
	private z.cube.podam.ExPub pub;
	@XmlElement(name = "req")
	private z.cube.podam.ExReq req;

	public z.cube.podam.ExPub getPub() {
		return pub;
	}

	public void setPub(ExPub pub) {
		this.pub = pub;
	}

	public z.cube.podam.ExReq getReq() {
		return req;
	}

	public void setReq(ExReq req) {
		this.req = req;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}

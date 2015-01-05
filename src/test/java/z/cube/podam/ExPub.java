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

@XmlRootElement(name = "pub")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pub", propOrder = { "code", 
		"msgnumber", "czjname", "sender", "receiver", "msgstatus" })
public class ExPub {
	@XmlElement(name = "code")
	private String code;
	@XmlElement(name = "msgnumber")
	private String msgnumber;
	@XmlElement(name = "czjname")
	private String czjname;
	@XmlElement(name = "sender")
	private String sender;
	@XmlElement(name = "receiver")
	private String receiver;
	@XmlElement(name = "msgstatus")
	private String msgstatus;

	public ExPub() {
		super();
	}

	public ExPub(String code, String msgnumber, String czjname, String sender, String receiver, String msgstatus) {
		super();
		this.code = code;
		this.msgnumber = msgnumber;
		this.czjname = czjname;
		this.sender = sender;
		this.receiver = receiver;
		this.msgstatus = msgstatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsgnumber() {
		return msgnumber;
	}

	public void setMsgnumber(String msgnumber) {
		this.msgnumber = msgnumber;
	}

	public String getCzjname() {
		return czjname;
	}

	public void setCzjname(String czjname) {
		this.czjname = czjname;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMsgstatus() {
		return msgstatus;
	}

	public void setMsgstatus(String msgstatus) {
		this.msgstatus = msgstatus;
	}

}

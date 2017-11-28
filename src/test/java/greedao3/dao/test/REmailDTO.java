package java.greedao3.dao.test;

import java.io.Serializable;

/**
 * REmailDTO作为网关同移动端的交互传输对象
 * 
 */
public class REmailDTO implements Serializable{

	private String sender;// 发送人
	private String subject;// 主题
	private String arrivetime;// 日期
	private String size;// 大小
	private String altfrom1;// 发送者全称
	private String sendto1;// 收件人
	private String copyto1;// 抄送
	private String blindcopyto1;// 密送
	private String AttachmentNames;// 附件列表，多个使用逗号分隔
	private String path;// 路径
	private String unid;// 文档唯一号
	private String body;// 正文
	private String  typeName;//类型名	
	private String flagcollection; //是否收藏
	private String isTitle;  //是否是title
	private String sourceDocUnid; // 转发文档的唯一号
	private String isRead;//0代表未读，1代表已读。
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getSourceDocUnid() {
		return sourceDocUnid;
	}

	public void setSourceDocUnid(String sourceDocUnid) {
		this.sourceDocUnid = sourceDocUnid;
	}

	public String getIsTitle() {
		return isTitle;
	}

	public void setIsTitle(String isTitle) {
		this.isTitle = isTitle;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getFlagcollection() {
		return flagcollection;
	}

	public void setFlagcollection(String flagcollection) {
		this.flagcollection = flagcollection;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getArrivetime() {
		return arrivetime;
	}

	public void setArrivetime(String arrivetime) {
		this.arrivetime = arrivetime;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAltfrom1() {
		return altfrom1;
	}

	public void setAltfrom1(String altfrom1) {
		this.altfrom1 = altfrom1;
	}

	public String getSendto1() {
		return sendto1;
	}

	public void setSendto1(String sendto1) {
		this.sendto1 = sendto1;
	}

	public String getCopyto1() {
		return copyto1;
	}

	public void setCopyto1(String copyto1) {
		this.copyto1 = copyto1;
	}

	public String getBlindcopyto1() {
		return blindcopyto1;
	}

	public void setBlindcopyto1(String blindcopyto1) {
		this.blindcopyto1 = blindcopyto1;
	}

	public String getAttachmentNames() {
		return AttachmentNames;
	}

	public void setAttachmentNames(String attachmentNames) {
		this.AttachmentNames = attachmentNames;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}

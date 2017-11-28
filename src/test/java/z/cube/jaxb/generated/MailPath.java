package java.z.cube.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JacksonXmlRootElement
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MailPath {
	@XmlValue
	@JacksonXmlText
	private String mailfile;

	public String getMailfile() {
		return mailfile;
	}

	public void setMailfile(String mailfile) {
		this.mailfile = mailfile;
	}

	@Override
	public String toString() {
		return "MailPath [mailfile=" + mailfile + "]";
	}
}

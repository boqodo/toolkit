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
import java.util.List;

@XmlRootElement(name = "main")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExMain {
	@XmlAttribute(name = "fieldsname")
	private String fieldsname;
	@XmlElement(name="value")
	private List<String> value;

	public String getFieldsname() {
		return fieldsname;
	}

	public void setFieldsname(String fieldsname) {
		this.fieldsname = fieldsname;
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}

}

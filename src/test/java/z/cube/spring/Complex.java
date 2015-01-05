package z.cube.spring;

import z.cube.param.Config;
import z.cube.param.SourceType;

@Config(source=SourceType.XML)
public class Complex {
	private String datetime;
	private String townname;
	private String sender;
	private String receiver;
	private Integer code;
	private Integer type;
	
	@Override
	public String toString() {
		return "Complex [datetime=" + datetime + ", townname=" + townname + ", sender=" + sender + ", receiver="
				+ receiver + ", code=" + code + ", type=" + type + "]";
	}
}

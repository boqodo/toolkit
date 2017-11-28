/* ==================================================================   
 * Created Jun 8, 2015 by KingSoft
 * ==================================================================  
 * FAP_NEW
 * ================================================================== 
 * FAP_NEW  License v1.0  
 * Copyright (c) Gsoft S&T Co.ltd HangZhou, 2012-2013 
 * ================================================================== 
 * 杭州掌拓科技有限公司拥有该文件的使用、复制、修改和分发的许可权
 * ================================================================== 
 */
package z.cube.spring;

import org.apache.commons.lang.StringUtils;
import org.hibernate.cfg.DefaultNamingStrategy;

/**
 *  自定义hibernate表名策略
 */
//在applicationContext.xml中的sessionfactory的AnnotationSessionFactoryBean下添加配置
//<property name="namingStrategy">
//	<bean id="customNamingStrategy" class="test.CustomNamingStrategy">
//		<property name="perfix" value="${hibernate.database.userperfix}" />
//	 	<property name="joinChar" value="${hibernate.database.joinchar}" />
//	 </bean>
//</property>
public class CustomNamingStrategy extends DefaultNamingStrategy {

	
	private static final long serialVersionUID = -1027414213827875152L;

	/** * 表名称前缀 */
	private String perfix;

	/** * 表名称连接符 */
	private String joinChar = ".";

	@Override
	public String tableName(String tableName) {
		String temp = tableName;
		if (!StringUtils.contains(temp, joinChar)) {
			temp = perfix + joinChar + temp;
		}
		System.out.println("数据库表名称结构：" + temp);
		return temp;
	}

	public String getPerfix() {
		return perfix;
	}

	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}

	public String getJoinChar() {
		return joinChar;
	}

	public void setJoinChar(String joinChar) {
		this.joinChar = joinChar;
	}

}

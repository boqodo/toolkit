package java.greedao3.dao.test;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import greedao3.dao.DAOProxyFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


public class DAOTest {

	@Test
	public void testName() throws Exception {
		DAOProxyFactory dpf = new DAOProxyFactory();
		// 数据库基础设置
		IEmailDAO emialDAO = dpf.create(IEmailDAO.class);
		List<REmailDTO> rs = emialDAO.getTopBy(5);
		System.out.println(rs);
	}
	
	
	
	@Test
	public final void testPlac(){
		
		// org.springframework.data.jpa.repository.query.StringQuery
		
		String[] keywords = {"like ","in "};
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(StringUtils.join(keywords, "|")); // keywords
		builder.append(")?");
		builder.append("(?: )?"); // some whitespace
		builder.append("\\(?"); // optional braces around paramters
		builder.append("(");
		builder.append("%?(\\?(\\d+))%?"); // position parameter
		builder.append("|"); // or
		builder.append("%?(:(\\w+))%?"); // named parameter;
		builder.append(")");
		builder.append("\\)?"); // optional braces around paramters
		
		Pattern p = Pattern.compile(builder.toString(), CASE_INSENSITIVE);
		
		String query = "update Member a set a.emailPath=:emailPath where a.id=:id and a.id in :list and a.id = ?2";
		Matcher matcher = p.matcher(query);
		
		System.out.println(matcher.replaceAll("?"));
		
		while (matcher.find()) {
			
			String parameterIndexString = matcher.group(4);
			String parameterName = parameterIndexString != null ? null : matcher.group(6);
			Integer parameterIndex = parameterIndexString == null ? null : Integer.valueOf(parameterIndexString);
			String typeSource = matcher.group(1);
			
			System.out.println(parameterIndex != null ?parameterIndex :parameterName);
			System.out.println(typeSource);
		}
		System.out.println(builder.toString());
	}
}

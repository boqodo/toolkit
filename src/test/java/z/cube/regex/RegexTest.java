package java.z.cube.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {

	@Test
	public final void testRegex(){
		String regExp ="\\$\\{[A-Za-z0-9_.]+\\}|#\\{[A-Za-z0-9_.()'* \\[\\]]+\\}";
		Pattern p = Pattern.compile(regExp);
		String text = "${domino.server}/${path22_23.22}/$file/${fileName}/#{xxx.fds()}/#{xxx.fds('22')}"
				+ "/#{ T(java.lang.Math).random() * 100.0 }/#{ systemProperties['user.region'] }";
		Matcher m = p.matcher(text);
		while(m.find()){
			String ms = m.group();
			System.out.println(ms);
		}
	}
}

package java.z.cube.utils;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FirstCharUtils {

	private List<String> lines;
	
	@Before
	public void setUp() throws Exception {
		URL url=this.getClass().getClassLoader().getResource("firstChar.txt");
		lines=FileUtils.readLines(new File(url.getFile()));
	}

	@After
	public void tearDown() throws Exception {
		lines=null;
	}

	@Test
	public final void test() {
		for(String line:lines){
			System.out.println(StringUtils.uncapitalize(line));
		}
	}

}

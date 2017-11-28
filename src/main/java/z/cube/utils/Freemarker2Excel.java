package z.cube.utils;

import java.io.IOException;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class Freemarker2Excel {
	public static final Configuration configuration = new Configuration(); //线程安全
	
	static{
		configuration.setDefaultEncoding("UTF-8");
		// FTL文件所存在的位置
		configuration.setClassForTemplateLoading(Freemarker2Excel.class,
				"/xls");
	}
	
	
	public static final void generate(Writer out,Object dataMap,String ftlName) throws IOException, TemplateException{
		Template t = configuration.getTemplate(ftlName, "UTF-8");
		t.process(dataMap, out);
	}
}

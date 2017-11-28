package java.sqlparse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class JsqlParseTest {
	
	@Test
	public final void test() throws JSQLParserException, IOException{
		InputStream input=getClass().getClassLoader().getResourceAsStream("complex.sql");
		String sql=IOUtils.toString(input);
		
		Statement statement = CCJSqlParserUtil.parse(sql);
		Select selectStatement = (Select) statement;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		PlainSelect ps=(PlainSelect) selectStatement.getSelectBody();
		for(String tl :tableList){
			System.out.println(tl);
		}
	}
}


package java.z.cube;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

public class CreateTableAuto {
	
	@Test
	public final void test1(){
		JdbcDataSource dataSource=new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test_mem");
        dataSource.setUser("sa");
        //Configuration fsfb = new LocalSessionFactoryBuilder(dataSource);
        Configuration fsfb = new Configuration();
		fsfb.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		fsfb.setProperty("hibernate.show_sql","true");
		fsfb.getProperties().put("hibernate.connection.datasource", dataSource);
		fsfb.addAnnotatedClass(User.class);
		SchemaExport export = new SchemaExport(fsfb);
		export.setOutputFile("F:\\dev\\java\\workspace_2017before\\toolkit\\target\\export.sql");
		

		export.create(true, true);
		
//		String[] args = new String[]{
//				"--schemas",
//				"--output=F:\\dev\\java\\workspace_2017before\\toolkit\\target\\export2.sql"
//		};
//		SchemaExport.main(args );
	}

}

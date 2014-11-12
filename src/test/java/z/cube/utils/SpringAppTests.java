package z.cube.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import z.cube.param.ConfigNull;
import z.cube.spring.ConfigService;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class SpringAppTests {
    @Autowired
    private ConfigService configService;

    @Test
    public void testConfig() {
        //ConfigHandlerFactory.registerHandler(SourceType.PROPERTIES,PropertiesConfigHandler.class);
        //ConfigHandlerFactory.registerHandler(SourceType.DATABASE, DatabaseConfigHandler.class);
        configService.configProperties("Properties:before");
        configService.configProperties(ConfigNull.STRING);
        configService.configProperties(ConfigNull.INTEGER);
        configService.configProperties("Properties:after");

        configService.configDatabase(new String[]{"Datebase:before"});
        configService.configDatabase((String[]) ConfigNull.OBJECT);
        configService.configDatabase(ConfigNull.INTEGER);

        configService.configXml(ConfigNull.INTEGER);
    }

    @Test
    public void testXmlConfig() throws Exception {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder = new FileBasedConfigurationBuilder<XMLConfiguration>(
                XMLConfiguration.class).configure(params.xml().setFileName("test.xml").setValidating(false));

        // This will throw a ConfigurationException if the XML document does not
        // conform to its DTD.
        XMLConfiguration config = builder.getConfiguration();
        System.out.println(config.getString("datetime"));
        System.out.println(config.getInt("count"));
        //ConfigurationUtils.unmodifiableConfiguration()
        StrSubstitutor strSubstitutor = new StrSubstitutor();

    }

    @Test
    public void testPropertiestConfig() throws Exception {
        InputStream testfile = SpringAppTests.class.getResourceAsStream("/test.properties");
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.read(new InputStreamReader(testfile));
        System.out.println(config.getString("application.servername"));
        int int1 = config.getInt("session.maxtimeout");
        System.out.println(int1);
        config.setProperty("session.maxtimeout", int1 + 10);
        int int2 = config.getInt("session.maxtimeout");
        System.out.println(int2);
    }

    @Test
    public void testConfiguartionBuilder() throws ConfigurationException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Lisi");
        Parameters params = new Parameters();
        BasicConfigurationBuilder<PropertiesConfiguration> builder =
                new BasicConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class)
                        .configure(
                                params.basic()
                                        .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                                        .setThrowExceptionOnMissing(true)
                        );
        PropertiesConfiguration config = builder.getConfiguration();
        config.addProperty("name", "Lisi");
        System.out.println(config.getString("name"));
    }

    @Test
    public void testProperties() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("colors.properties")
                                .setFileName("sizes.properties"));
        try {
            Configuration config = builder.getConfiguration();
            String backColor = config.getString("colors.background");
            Dimension size = new Dimension(config.getInt("window.width"),
                    config.getInt("window.height"));
            System.out.println(backColor + size);
            config.setProperty("colors.background", "#000000");
            config.addProperty("test", "test");
            builder.save();
        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
    }

    @Test
    public final void teststatic() {
        StaticTest s = new StaticTest();
        System.out.println(s.getS());
        s.setS("xx");
        System.out.println(s.getS());
        StaticTest x = new StaticTest();
        System.out.println(x.getS());
    }
}

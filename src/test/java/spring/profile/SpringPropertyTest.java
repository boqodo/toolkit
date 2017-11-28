package java.spring.profile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

public class SpringPropertyTest {

	@Test  
	public void test() throws IOException {  
	    Map<String, Object> map = new HashMap<>();  
	    map.put("encoding", "gbk");  
	    PropertySource propertySource1 = new MapPropertySource("map", map);  
	    System.out.println(propertySource1.getProperty("encoding"));  
	  
	    ResourcePropertySource propertySource2 = new ResourcePropertySource("resource", "classpath:test.properties"); //name, location  
	    System.out.println(propertySource2.getProperty("session.maxtimeout"));  
	}  
	
	@Test  
	public void test2() throws IOException {  
		
		Map<String, Object> map = new HashMap<>();  
	    map.put("encoding", "gbk");  
	    PropertySource propertySource1 = new MapPropertySource("map", map);  
	    System.out.println(propertySource1.getProperty("encoding"));  
	  
	    ResourcePropertySource propertySource2 = new ResourcePropertySource("resource", "classpath:test.properties"); //name, location  
	    System.out.println(propertySource2.getProperty("session.maxtimeout")); 
	    CompositePropertySource compositePropertySource = new CompositePropertySource("composite");  
	    compositePropertySource.addPropertySource(propertySource1);  
	    compositePropertySource.addPropertySource(propertySource2);  
	    System.out.println(compositePropertySource.getProperty("encoding"));  
	}  
	
	@Test  
	public void test3() throws IOException {  
		Map<String, Object> map = new HashMap<>();  
	    map.put("encoding", "gbk");  
	    PropertySource propertySource1 = new MapPropertySource("map", map);  
	    System.out.println(propertySource1.getProperty("encoding"));  
	  
	    ResourcePropertySource propertySource2 = new ResourcePropertySource("resource", "classpath:test.properties"); //name, location  
	    System.out.println(propertySource2.getProperty("session.maxtimeout"));
	    
	    MutablePropertySources propertySources = new MutablePropertySources();  
	    propertySources.addFirst(propertySource1);  
	    propertySources.addLast(propertySource2);  
	    System.out.println(propertySources.get("resource").getProperty("encoding"));  
	  
	    for(PropertySource propertySource : propertySources) {  
	        System.out.println(propertySource.getProperty("encoding"));  
	    }  
	}  
	
	
	@Test  
	public void test4() throws Exception {  
		Map<String, Object> map = new HashMap<>();  
	    map.put("encoding", "gbk");  
	    PropertySource propertySource1 = new MapPropertySource("map", map);  
	    System.out.println(propertySource1.getProperty("encoding"));  
	  
	    ResourcePropertySource propertySource2 = new ResourcePropertySource("resource", "classpath:test.properties"); //name, location  
	    System.out.println(propertySource2.getProperty("session.maxtimeout"));
	    
	    MutablePropertySources propertySources = new MutablePropertySources();  
	    propertySources.addFirst(propertySource1);  
	    propertySources.addLast(propertySource2);  
	    PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySources);  
	  
	    System.out.println(propertyResolver.getProperty("encoding"));  
	    System.out.println(propertyResolver.getProperty("no", "default"));  
	    System.out.println(propertyResolver.resolvePlaceholders("must be encoding ${encoding}"));  //输出must be encoding gbk  
	}  
	
	@Test  
	public void test5() {  
	    //会自动注册 System.getProperties() 和 System.getenv()  
	    Environment environment = new StandardEnvironment();  
	    System.out.println(environment.getProperty("file.encoding"));  
	}  
}

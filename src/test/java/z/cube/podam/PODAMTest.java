package java.z.cube.podam;

import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import z.cube.cxf.User;
import z.cube.podam.ExPackage;
import z.cube.utils.JAXBUtils;
import z.cube.utils.JsonUtilsEx;

public class PODAMTest {
	@Test
	public final void test(){
		PodamFactory factory = new PodamFactoryImpl(); 
		User user = factory.manufacturePojo(User.class);
		System.out.println(user);
		
		Person person = factory.manufacturePojo(Person.class);
		System.out.println(JsonUtilsEx.generate(person));
		String xml=JAXBUtils.generateFormatXml(person);
		System.out.println(xml);


        z.cube.podam.ExPackage exPackage=factory.manufacturePojo(ExPackage.class);
        String xml2=JAXBUtils.generateFormatXml(exPackage);
        System.out.println(xml2);
	}
}

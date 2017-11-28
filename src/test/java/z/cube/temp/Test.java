package java.z.cube.temp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;

import org.eclipse.persistence.jaxb.JAXBContextProperties;

public class Test {
    public static final String  TEST_ERROR  = "TEST_ERROR";

	public static void main(String[] args) throws TransformerException, Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		Marshaller marshaller = null;
		ClassLoader classLoader = Test.class.getClassLoader();

		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		Map<String, String> test = new HashMap<String, String>();
		test.put("id", "10");
		test.put("publicationTitle", "20");
		test.put("paginazione", "boh");
		test.put("pageSize", "10");
		Map<String, String> test2 = new HashMap<String, String>();
		test2.put("id", "20");
		test2.put("publicationTitle", "30");
		test2.put("paginazione", "gboh");
		test2.put("pageSize", "20");
		results.add(test);
		results.add(test2);


		InputStream modelStream = classLoader.getResourceAsStream("test/oxm.xml");
		URL url=classLoader.getResource("test/oxm.xml");
		System.out.println(url.getFile());
		JAXBContext ctx = null;
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, modelStream);
		try {
			ctx = JAXBContext.newInstance(new Class[] { SearchResult.class }, properties);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		marshaller = ctx.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		SearchResult result = new SearchResult();
		result.setListOfMap(results);
		marshaller.marshal(result, System.out);

	}

}
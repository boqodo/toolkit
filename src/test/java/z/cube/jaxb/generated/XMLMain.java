package java.z.cube.jaxb.generated;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import z.cube.utils.JAXBUtils;

public class XMLMain

{
	@Test
	public final void testXml() throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance("z.cube.jaxb.generated");
        Unmarshaller u = jc.createUnmarshaller();
        InputStream in = getClass().getClassLoader().getResourceAsStream("doc.xml");
		Object o = u.unmarshal(in);
		System.out.println(o);
		String xml = JAXBUtils.generateFormatXml(o);
		System.out.println(ToStringBuilder.reflectionToString(o));
	}
	
	@Test
	public final void testXml2() throws JAXBException{
		InputStream in = getClass().getClassLoader().getResourceAsStream("doc.xml");
		ViewEntries vt = JAXBUtils.parseXml(ViewEntries.class, in);
		System.out.println(vt);
		String xml = JAXBUtils.generateFormatXml(vt);
		System.out.println(xml);
	}
	
	@Test
	public final void testXml3() throws JAXBException, Exception{
		InputStream in = getClass().getClassLoader().getResourceAsStream("mailpath.xml");
		MailPath vt = JAXBUtils.parseXml(MailPath.class, in);
		System.out.println(vt);
		String xml = JAXBUtils.generateFormatXml(vt);
		System.out.println(xml);
		
		XmlMapper mapper = new XmlMapper();
		mapper.registerModule(new JaxbAnnotationModule());
		mapper.writeValue(System.out, vt);
	}
	@Test
	public final void testXml4() throws IOException{
		ObjectMapper mapper = new XmlMapper();
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		mapper.registerModule(module);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		InputStream in = getClass().getClassLoader().getResourceAsStream("mailpath.xml");

		String data= IOUtils.toString(in, Charsets.UTF_8);
		MailPath vt = mapper.readValue(data, MailPath.class);
		System.out.println(vt);
	}
	@Test
	public final void testJson() throws IOException{
		ResultDTO<Boolean> x = ResultDTO.SUCCESS(false);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(x));
		
		String json = "{\"success\":true,\"isSuccess\":false,\"code\":null,\"description\":null,\"data\":false}";
		JavaType jtype = mapper.getTypeFactory()
				.constructParametricType(ResultDTO.class, Boolean.class);
		ResultDTO<Boolean> x1=mapper.readValue(json, jtype);
		System.out.println(x1);
		System.out.println(mapper.writeValueAsString(x1));
	}
	@Test
	public final void testx() throws Exception{
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("yf", "22"));
		parameters.add(new BasicNameValuePair("yf2", "1"));
		parameters.add(new BasicNameValuePair("yf3", "76"));
		parameters.add(new BasicNameValuePair("yf32", "adasd"));
		String url = URLEncodedUtils.format(parameters, Charsets.UTF_8);
		System.out.println(url);
		
		//String urlstr = "http://192.168.1.22:8080/mail/($Inboxmobile)?readviewentries&count=10000&myDate=2323";
		String urlstr = "http://192.168.1.22:8080/mail/($Inboxmobile)";
		URIBuilder u = new URIBuilder(urlstr);
		u.addParameter("2323", "xx");
		u.addParameter("cc", "xx");
		u.addParameter("yy", "xx");
		u.addParameter("tt", "xx");
		String fullurl =u.build().toString();
		System.out.println(fullurl);
	}

}

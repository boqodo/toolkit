package z.cube.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;


public class JaxbUtils {
	
	/**默认字符集为gb18030**/
	private static final String DEFAULT_ENCODING="gb18030";
	/**
	 * 解析报文
	 * @param xmlString xml报文字符串
	 * @return 报文对象Package;解析出错,返回null
	 */
	public static <T> T parseXml(Class<T> clazz, String xmlString){
		try {
			InputStream in=new ByteArrayInputStream(xmlString.getBytes(DEFAULT_ENCODING));
			return JAXB.unmarshal(in,clazz);
		} catch (Exception e) {
			return null;
		}
		
	}
	/**
	 * 生成报文
	 * @return 转xml后的字符串;转换出错,返回null
	 */
	public static String generateXml(Object object){
		try{
			JAXBContext jaxb=JAXBContext.newInstance(object.getClass());
			Marshaller marshaller=jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, DEFAULT_ENCODING);
			//不格式化输出的xml
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);

            ByteArrayOutputStream baos=new ByteArrayOutputStream();
			marshaller.marshal(object,baos);
			return baos.toString(DEFAULT_ENCODING);
		}catch(Exception e){
			return null;
		}
	}


    /**
     * 去除生成的 xml头中 包含的standalone 属性
     * @return
     */
    public static String generateXmlEx(Object object){
        try{
            JAXBContext jaxbContext=JAXBContext.newInstance(object.getClass());
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, DEFAULT_ENCODING);
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLOutputFactory xmlOutputFactory= XMLOutputFactory.newInstance();
//            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(baos, DEFAULT_ENCODING);
//            xmlStreamWriter.writeStartDocument(DEFAULT_ENCODING, "1.0");
//            jaxbMarshaller.marshal(object, xmlStreamWriter);
//            xmlStreamWriter.writeEndDocument();
//            xmlStreamWriter.close();
//            return baos.toString(DEFAULT_ENCODING);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty("jaxb.encoding", DEFAULT_ENCODING);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(baos, (String) jaxbMarshaller.getProperty(Marshaller.JAXB_ENCODING));
            xmlStreamWriter.writeStartDocument((String) jaxbMarshaller.getProperty(Marshaller.JAXB_ENCODING), "1.0");
            jaxbMarshaller.marshal(object, xmlStreamWriter);
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.close();
            return new String(baos.toByteArray(),DEFAULT_ENCODING);
        }catch(Exception e){
            return null;
        }
    }
	
}

package z.cube.utils;

import javax.xml.bind.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class JAXBUtils {

	/** 默认字符集为UTF-8* */
	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 解析报文
	 *
	 * @param xmlString xml报文字符串
	 *
	 * @return 报文对象Package;解析出错,返回null
	 */
	public static <T> T parseXml(Class<T> clazz, String xmlString) {
		try {
			InputStream in = new ByteArrayInputStream(xmlString.getBytes(DEFAULT_ENCODING));
			return JAXB.unmarshal(in, clazz);
		} catch (UnsupportedEncodingException e) {
			throw convertException(e);
		}
	}
    public static <T> T parseXml(Class<T> clazz, InputStream in) {
        return JAXB.unmarshal(in, clazz);
    }

	public static String generateFormatXml(Object object) {
		return generateXml(object, true);
	}

	public static String generateXml(Object object, boolean format) {
		try {
			JAXBContext jaxb = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, DEFAULT_ENCODING);
			//不格式化输出的xml
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(object, baos);
			return baos.toString(DEFAULT_ENCODING);
		} catch (JAXBException e) {
			throw convertException(e);
		} catch (UnsupportedEncodingException e) {
			throw convertException(e);
		}
	}

	private static RuntimeException convertException(Exception e) {
		String message = e.getMessage();
		if (e instanceof UnsupportedEncodingException) {
			message = String.format("不支持(%s)编码格式!", DEFAULT_ENCODING);
		} else if (e instanceof JAXBException) {
			message = "JAXB异常!";
		}else if(e instanceof PropertyException){
			message = "不支持的Marshaller属性!";
		}
		return new RuntimeException(message, e);
	}

	/**
	 * 生成报文
	 *
	 * @return 转xml后的字符串;转换出错,返回null
	 */
	public static String generateXml(Object object) {
		return generateXml(object, false);
	}

	/**
	 * 去除生成的 xml头中 包含的standalone 属性
	 */
	public static String generateXmlEx(Object object) {
		try {
			JAXBContext jaxb = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, DEFAULT_ENCODING);
			//不格式化输出的xml
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(object, baos);
			String result = baos.toString(DEFAULT_ENCODING);
			String xmlHeader = "<?xml version=\"1.0\" encoding=\"%s\"?>";

			return String.format(xmlHeader, DEFAULT_ENCODING) + result;
		} catch (PropertyException e) {
			throw convertException(e);
		} catch (UnsupportedEncodingException e) {
			throw convertException(e);
		} catch (JAXBException e) {
			throw convertException(e);
		}
	}

}

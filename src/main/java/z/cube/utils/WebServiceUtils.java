package z.cube.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 *	Webservice动态调用工具类(基于CXF)
 *
 */
public class WebServiceUtils {
	
	private static final JaxWsDynamicClientFactory CLIENTFACTORY = JaxWsDynamicClientFactory.newInstance();
	private static final Map<String,Client> CACHE=Collections.synchronizedMap(new HashMap<String,Client>(8));

	
	/**
	 * 获取webservice接口实现类实例(动态创建实现类)
	 * @param serviceclazz		webservice接口定义类 用@WebService注解
	 * @param endpointAddress	webservice发布接口地址(注:不需要包含?wsdl)
	 * @return	T	接口实现类实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getServiceClazzInstance(Class<T> serviceclazz,String endpointAddress){
		JaxWsProxyFactoryBean factory=new JaxWsProxyFactoryBean();
		factory.setServiceClass(serviceclazz);
		factory.setAddress(endpointAddress);
		return (T) factory.create();
	}
	
	/**
	 * 动态调用webservice接口
	 * @param wsdlUrl		wsdl url地址(注：需要包括 ?wsdl)
	 * @param methodName	接口方法名
	 * @param paramters		参数数组
	 * @return	Object[]	方法返回值		
	 * @throws Exception
	 */
	public static Object[] dynamicInvoke(String wsdlUrl,String methodName,Object... paramters) throws Exception{
		Client client=null;
		if(CACHE.containsKey(wsdlUrl)){
			client=CACHE.get(wsdlUrl);
		}else{
			client = CLIENTFACTORY.createClient(wsdlUrl);
			resetTimeout(client);
			CACHE.put(wsdlUrl, client);
		}
		//Invokes an operation synchronously 同步的，线程安全
		Object[] result = client.invoke(methodName, paramters);
		return result;
	}
	/**
	 * 重置默认的超时时间设置
	 * @param client
	 */
	private static void resetTimeout(Client client){
		HTTPConduit http = (HTTPConduit) client.getConduit();    
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		//WebService以TCP连接为基础,这个属性可以理解为TCP握手时的时间设置,
		//超过设置的时间就认为是连接超时.以毫秒为单位,默认是30000毫秒,即30秒。
		httpClientPolicy.setConnectionTimeout(60000);    
		httpClientPolicy.setAllowChunking(false); 
		//属性是发送WebService的请求后等待响应的时间,
		//超过设置的时长就认为是响应超时.以毫秒为单位,默认是60000毫秒,即60秒.
		httpClientPolicy.setReceiveTimeout(120000);    
		http.setClient(httpClientPolicy);  
	}
	
}

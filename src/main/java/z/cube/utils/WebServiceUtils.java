package z.cube.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Webservice动态调用工具类(基于CXF)
 */
public class WebServiceUtils {
    private static final String 					WSDL_SUFFIX 	  = "?wsdl";
	private static final Integer                   CONNECTION_TIMEOUT = 60*1000;
    private static final Integer                   RECEIVE_TIMEOUT    = 120*1000;
    private static final JaxWsDynamicClientFactory CLIENTFACTORY      = JaxWsDynamicClientFactory.newInstance();
    private static final Map<String, Client>       CACHE              = new ConcurrentHashMap<String, Client>(8);


    /**
     * 获取webservice接口实现类实例(动态创建实现类)
     *
     * @param serviceclazz    webservice接口定义类 用@WebService注解
     * @param endpointAddress webservice发布接口地址(注:不需要包含?wsdl)
     *
     * @return T    接口实现类实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getServiceClazzInstance(Class<T> serviceclazz, String endpointAddress) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(serviceclazz);
        factory.setAddress(endpointAddress);
        //添加日志输入和输出拦截器
        factory.getInInterceptors().add(new LoggingInInterceptor());   
        factory.getOutInterceptors().add(new LoggingOutInterceptor());   
        return (T) factory.create();
    }

    /**
     * 动态调用webservice接口
     *
     * @param wsdlUrl    wsdl url地址(注：需要包括 ?wsdl)
     * @param methodName 接口方法名
     * @param paramters  参数数组
     *
     * @throws Exception
     * @return Object[]    方法返回值
     */
    public static Object[] dynamicInvoke(String wsdlUrl, String methodName, Object... paramters) throws Exception {
        if(!wsdlUrl.endsWith(WSDL_SUFFIX)){
        	wsdlUrl+=WSDL_SUFFIX;
        }
    	Client client ;
        if (CACHE.containsKey(wsdlUrl)) {
            client = CACHE.get(wsdlUrl);
        } else {
            client = CLIENTFACTORY.createClient(wsdlUrl);
            client.getInInterceptors().add(new LoggingInInterceptor());   
            client.getOutInterceptors().add(new LoggingOutInterceptor()); 
            resetTimeout(client);
            CACHE.put(wsdlUrl, client);
        }
        //Invokes an operation synchronously 同步的，线程安全
       
        QName opName = getQName(methodName, client);  
        Object[] result = client.invoke(opName, paramters);
        return result;
    }

	/**
	 * 获取相互匹配命名空间的操作方法
	 * 不同命名空间引起的问题http://www.iteye.com/topic/1133044
     * 给接口类和实现类注解WebService加上相同的targetNamespace即可
	 * @param methodName	对外提供的操作方法
	 * @param client		实例化后的调用客户端
	 * @return
	 */
	private static QName getQName(String methodName, Client client) {
		org.apache.cxf.endpoint.Endpoint endpoint = client.getEndpoint();  
        QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), methodName);  
        BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();  
        if (bindingInfo.getOperation(opName) == null) {  
            for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {  
                if (methodName.equals(operationInfo.getName().getLocalPart())) {  
                    opName = operationInfo.getName();  
                    break;  
                }  
            }  
        }
		return opName;
	}

    /**
     * 重置默认的超时时间设置
     *
     * @param client
     */
    private static void resetTimeout(Client client) {
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        //WebService以TCP连接为基础,这个属性可以理解为TCP握手时的时间设置,
        //超过设置的时间就认为是连接超时.以毫秒为单位,默认是30000毫秒,即30秒。
        httpClientPolicy.setConnectionTimeout(CONNECTION_TIMEOUT);
        httpClientPolicy.setAllowChunking(false);
        //属性是发送WebService的请求后等待响应的时间,
        //超过设置的时长就认为是响应超时.以毫秒为单位,默认是60000毫秒,即60秒.
        httpClientPolicy.setReceiveTimeout(RECEIVE_TIMEOUT);
        http.setClient(httpClientPolicy);
    }
    
    /**
     * 通过jdk api接口发布webservice
     * @param publishUrlAddress	发布地址
     * @param implObj			接口实现具体实例
     * @return
     */
    public static Endpoint publish(String publishUrlAddress,Object implObj){
    	return Endpoint.publish(publishUrlAddress, implObj); 
    }
    
    /**
     * 通过cxf 发布webservice
     * @param publishUrlAddress	发布地址
     * @param interfaceClazz	接口类
     * @param implObj			接口实现具体实例
     * @return
     */
    public static Server publish(String publishUrlAddress,Class<?> interfaceClazz,Object implObj){
    	//创建WebService服务工厂  
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();  
        //注册WebService接口  
        factory.setServiceClass(interfaceClazz);  
        //发布接口  
        factory.setAddress(publishUrlAddress);  
        factory.setServiceBean(implObj);  
        factory.getInInterceptors().add(new LoggingInInterceptor());   
        factory.getOutInterceptors().add(new LoggingOutInterceptor());   
        //创建WebService  
        Server server= factory.create();  
        return server;
    }
}

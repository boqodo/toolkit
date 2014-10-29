package z.cube.utils;

import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.interceptor.Fault;

public class ExceptionUtilsEx  {
    /**
     * 获取异常信息,不显示异常类名，用于前台页面异常信息显示
     * @param th
     * @return
     */
    public static String getMessageEx(Throwable e){
    	String error;
		if(e instanceof ConnectException){
			error="连接超时或IP地址配置错!";
		}else if(e instanceof SOAPFaultException){
			Throwable root=ExceptionUtils.getRootCause(e);
			if(root instanceof ConnectException){
				error="连接超时或IP地址配置错!";
			}else if(root instanceof SoapFault){
				error=String.format("被调用服务系统异常!(%s)",root.getMessage());
			}else if(Fault.class.equals(root.getClass())){
				error="服务地址配置错!";
			}else{
				error="无法发送消息!请联系管理员检查服务配置是否正确或调用的服务器是否正常启动!";
			}
		}else if(e instanceof InvocationTargetException){
			error=getMessageEx(e.getCause());
		}else if (StringUtils.isNotBlank(e.getMessage())) {
			if(ObjectUtils.equals(ExceptionUtils.getRootCauseMessage(e),ExceptionUtils.getMessage(e))){
				error = e.getMessage();
			}else{
				error=String.format(e.getMessage()+"(%s)",ExceptionUtils.getRootCauseMessage(e));
			}
		} else {
			error = ClassUtils.getShortClassName(e.getClass());
		}
		return error;
    }
}

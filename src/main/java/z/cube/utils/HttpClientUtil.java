package z.cube.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientUtil {
	public void getMethod() throws Exception {
		HttpClient client = new HttpClient();
		StringBuilder sb = new StringBuilder();
		InputStream ins = null;
		// Create a method instance.  
		GetMethod method = new GetMethod("http://weather.gtimg.cn/aqi/01013402.json?callback=city&_=1426839273470");
		// Provide custom retry handler is necessary  
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			// Execute the method.  
			int statusCode = client.executeMethod(method);
			System.out.println(statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				ins = method.getResponseBodyAsStream();
				byte[] b = new byte[1024];
				int r_len = 0;
				while ((r_len = ins.read(b)) > 0) {
					sb.append(new String(b, 0, r_len, method.getResponseCharSet()));
				}
			} else {
				System.err.println("Response Code: " + statusCode);
			}
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
		} finally {
			method.releaseConnection();
			if (ins != null) {
				ins.close();
			}
		}
		System.out.println(sb.toString());
	}

	public void postMethod() throws Exception {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod("http://hao.lenovo.com/mps/api/weather.php?address=%E6%B9%96%E5%B7%9E");
		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gb2312");
		//NameValuePair[] param = { new NameValuePair("age", "11"), new NameValuePair("name", "jay"), };
		//method.setRequestBody(param);
		int statusCode = client.executeMethod(method);
		System.out.println(statusCode);
		System.out.println(new String(method.getResponseBody(),"UTF-8"));
		method.releaseConnection();
	}
	
	public static void main(String[] args) throws Exception {
		HttpClientUtil hcu=new HttpClientUtil();
		hcu.getMethod();
		hcu.postMethod();
	}

}

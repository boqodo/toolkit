package java.greedao3.dao;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import greedao3.dao.anntation.Param;
import greedao3.dao.anntation.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import z.cube.utils.TestUtils;


public class DAOProxyFactory {
	
	private static final Pattern PARAMETER_BINDING_PATTERN;
	
	static{
		String[] keywords = {"like ","in "};
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(StringUtils.join(keywords, "|")); // keywords
		builder.append(")?");
		builder.append("(?: )?"); // some whitespace
		builder.append("\\(?"); // optional braces around paramters
		builder.append("(");
		builder.append("%?(\\?(\\d+))%?"); // position parameter
		builder.append("|"); // or
		builder.append("%?(:(\\w+))%?"); // named parameter;
		builder.append(")");
		builder.append("\\)?"); // optional braces around paramters
		
		PARAMETER_BINDING_PATTERN = Pattern.compile(builder.toString(), CASE_INSENSITIVE);
	}


	@SuppressWarnings("unchecked")
	public <T> T create(Class<T> daoInterface) {
		ClassLoader loader = daoInterface.getClassLoader();
		InvocationHandler h = new InvocationHandler() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				
				Query query = method.getAnnotation(Query.class);
				
				String sql = query.value();
				
				Matcher matcher = PARAMETER_BINDING_PATTERN.matcher(sql);
				
				while (matcher.find()) {
					String parameterIndexString = matcher.group(4);
					String parameterName = parameterIndexString != null ? null : matcher.group(6);
					Integer parameterIndex = parameterIndexString == null ? null : Integer.valueOf(parameterIndexString);
					String typeSource = matcher.group(1);
					
					if("like ".equalsIgnoreCase(typeSource)){
						
					}
					if("in ".equalsIgnoreCase(typeSource)){
						
					}
					
				}
				
				
				
				
				Annotation[][] ass = method.getParameterAnnotations();
				
				Map<Integer,Param> ipm = new HashMap<Integer,Param>(args.length);
				for (int i = 0; i < ass.length; i++) {//每个参数
					Annotation[] as = ass[i];
					
					for (int j = 0; j < as.length; j++) { //参数上的注解
						Annotation a = as[j];
						
						if(Param.class.equals(a.annotationType())){
							ipm.put(i,(Param)a);
						}
					}
				}
				
				if(!ipm.isEmpty()){
					
				}
				
				Class rclazz = method.getReturnType();
				return TestUtils.build(rclazz,method.getGenericReturnType());
			}
		};
		return (T) Proxy.newProxyInstance(loader , new Class[]{daoInterface}, h );
	}

}

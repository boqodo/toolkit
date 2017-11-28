/* ==================================================================   
 * Created Jan 8, 2015 by KingSoft
 * ==================================================================  
 * FAP_NEW
 * ================================================================== 
 * FAP_NEW  License v1.0  
 * Copyright (c) Gsoft S&T Co.ltd HangZhou, 2012-2013 
 * ================================================================== 
 * 杭州掌拓科技有限公司拥有该文件的使用、复制、修改和分发的许可权
 * ================================================================== 
 */
package z.cube.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import flex.messaging.FlexContext;
import flex.messaging.io.amf.ASObject;


public class FlexUtils {
	/**
	 * 获取请求的服务器的真实路径地址
	 * @return 真实路径字符串
	 */
	public static String getRealPath() {
		String reqPath=FlexContext.getHttpRequest().getSession().getServletContext().getRealPath("");
		return reqPath;
	}
	/**
	 * 构建转换为可请求的相对路径地址
	 * @param filePath	服务器文件路径
	 * @return	String  可请求的相对路径地址
	 */
	public static String buildUrl(String filePath){
		return ".."+filePath.replaceAll("\\\\", "/");
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ASObject conv2ASObject(Object object){
		ASObject ao=null;
		if(object instanceof ASObject){
			ao=(ASObject) object;
		}else if(object instanceof Map){
			ao=new ASObject();
			ao.putAll((Map) object);
		}else{
			ao=new ASObject();
			try{
				//BeanUtils.describe 处理列表将第一个元素转为字符串
				ao.putAll(describe(object));
			}catch (Exception e) {
				throw new RuntimeException("Object转ASObject出错!",e);
			}
		}
		return ao;
	}
	public static List<ASObject> conv2ASObjectList(List<Object> objects){
		List<ASObject> ass=new ArrayList<ASObject>(8);
		for(Object object :objects){
			ass.add(conv2ASObject(object));
		}
		return ass;
	}
	public static Map<String,Object> describe(Object bean) throws Exception {
		Map<String,Object> description=new HashMap<String,Object>();
		PropertyDescriptor descriptors[] = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			if (PropertyUtils.getReadMethod(descriptors[i]) != null
					&& !"class".equals(name)){
				description.put(name, PropertyUtils.getProperty(bean, name));
			}
		}
		return description;
	}
}

/* ==================================================================   
 * Created Apr 22, 2015 by KingSoft
 * ==================================================================  
 * FAP_NEW
 * ================================================================== 
 * FAP_NEW  License v1.0  
 * Copyright (c) Gsoft S&T Co.ltd HangZhou, 2012-2013 
 * ================================================================== 
 * 杭州掌拓科技有限公司拥有该文件的使用、复制、修改和分发的许可权
 * ================================================================== 
 */
package z.cube.spring;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 *  Spring集成hibernate加载实体过滤类
 */
//在applicationContext.xml中的sessionfactory的AnnotationSessionFactoryBean下添加配置
//<property name="entityTypeFilters">
//<list value-type="org.springframework.core.type.filter.TypeFilter">
//	<bean id="sepciallEntityTypeFilter" class="test.SpecialEntityTypeFilter"/>
//</list>
//</property>
public class SpecialEntityTypeFilter implements TypeFilter {
	private String[] filterEntityClassNames={
	};

	@Override
	public boolean match(MetadataReader metadatareader, MetadataReaderFactory metadatareaderfactory) throws IOException {
		String clazzName=metadatareader.getClassMetadata().getClassName();
		for(String filterEntityClassName:filterEntityClassNames ){
			if(StringUtils.equals(clazzName, filterEntityClassName)){
				System.out.println("过滤不加载："+clazzName);
				return false;
			}
		}
		return true;
	}

}

package z.cube.param;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import z.cube.spring.Complex;
import z.cube.spring.ConfigService;

import java.lang.reflect.Method;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class MethodParameterInjectInterceptorTest {

    @Test
    public void testInvokeByMock() throws Throwable {
        MethodInvocation methodInvocation= mock(MethodInvocation.class);
        Method method=MethodUtils.getAccessibleMethod(ConfigService.class,
        		"configProperties", Integer.class);
        when(methodInvocation.getMethod()).thenReturn(method);
        Object[] arguments=new Object[]{ConfigNull.INTEGER};
        when(methodInvocation.getArguments()).thenReturn(arguments);

        MethodParameterInjectInterceptor mpii=new MethodParameterInjectInterceptor(buildInitConfig());
        mpii.invoke(methodInvocation);
        
        Object paramObj=arguments[0];
        assertNotNull(paramObj);
        assertFalse(paramObj.equals(ConfigNull.INTEGER));
        
        verify(methodInvocation).getMethod();
        verify(methodInvocation).getArguments();
    }
    private InitConfig buildInitConfig(){
    	InitConfig ic=new InitConfig();
    	ic.setPropertiesFileNames("colors.properties,sizes.properties");
    	ic.setXmlFileNames("test.xml");
    	ic.setTableName("CONFIGURATION");
    	ic.setKeyColumn("KEY");
    	ic.setValueColumn("value");
    	//ic.setDataSource(dataSource);
    	return ic;
    }
    
    @Test
    public void testInvokeByProxy(){
    	AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
    	String expression = "execution(* *(..,@z.cube.param.Config (*),..))" +
    			"||execution(* *(..,(@ z.cube.param.Config *),..))";
		pointcut.setExpression(expression);
        Advice advice = new MethodParameterInjectInterceptor(buildInitConfig());
        Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
        
		// create proxy 
        ConfigService target=new ConfigService();
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvisor(advisor);
		ConfigService proxy = (ConfigService) pf.getProxy();
		proxy.configProperties(ConfigNull.INTEGER);
		proxy.configComplex((Complex) ConfigNull.OBJECT);
    }
}
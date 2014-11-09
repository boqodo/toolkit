package z.cube.spring;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import z.cube.param.InitConfig;
import z.cube.param.MethodParameterInjectInterceptor;


public class ParameterConfigurationPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

    private static final long serialVersionUID = -6101339499333652651L;

    private InitConfig initConfig;


    @Override
    public void afterPropertiesSet() {
        //Pointcut pointcut = Pointcut.TRUE;  //针对final无法拦截处理
        Pointcut pointcut = new AnnotationMatchingPointcut(Service.class, true);
        //TODO:方法嵌套拦截，方法A调用方法B，让B方法也起作用
        Advice advice = new MethodParameterInjectInterceptor(initConfig);
        this.advisor = new DefaultPointcutAdvisor(pointcut, advice);
    }

    public void setInitConfig(InitConfig initConfig) {
        this.initConfig = initConfig;
    }
}

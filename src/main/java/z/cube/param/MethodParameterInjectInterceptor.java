package z.cube.param;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;


public class MethodParameterInjectInterceptor implements MethodInterceptor {
    private InitConfig initConfig;

    public MethodParameterInjectInterceptor(InitConfig initConfig) {
        this.initConfig = initConfig;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method invokedMethod = invocation.getMethod();
        Annotation[][] paramAnns = invokedMethod.getParameterAnnotations();

        Object[] parameters = invocation.getArguments();
        Type[] paramTypes = invokedMethod.getGenericParameterTypes();
        for (int i = 0; i < paramAnns.length; i++) {
            Config config = findConfig(paramAnns[i]);
            //参数值不为空，则不注入
            if (config != null && parameters[i] == null) {
                parameters[i] = findInjectValue(config, paramTypes[i]);
            }
        }
        return invocation.proceed();
    }

    private Object findInjectValue(Config config, Type type) {
        SourceType sourceType = config.source();
        ConfigHandler handler = ConfigHandlerFactory.getConfigHandler(sourceType, initConfig);
        Object val = handler.getValue(config.key());
        //TODO: convert type
        val = ConvertUtils.convert(val, (Class<?>) type);
        return val;
    }

    private Config findConfig(Annotation[] anns) {
        Config temp = null;
        //编译器自动校验是否使用了重复注解
        //为此代码不处理，匹配后直接跳出
        for (Annotation ann : anns) {
            if (ann instanceof Config) {
                temp = (Config) ann;
                break;
            }
        }
        return temp;
    }
}

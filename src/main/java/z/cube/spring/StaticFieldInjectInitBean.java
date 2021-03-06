package z.cube.spring;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Map;


/**
 * 集成Spring静态字段注入
 * <pre>
 * &lt;bean id="staticFieldInject"
 * 		class="test.dynamic.StaticFieldInjectInitBean" lazy-init="false"&gt;
 * 		&lt;property name="staticFieldInjectValueMap"&gt;
 * 			&lt;map&gt;
 * 				&lt;entry key="test.dynamic.StaticFieldInjectTest.MAX"&gt;
 * 					&lt;value type="java.lang.String"&gt;100&lt;/value&gt;
 * 				&lt;/entry&gt;
 * 				&lt;entry key="test.dynamic.StaticFieldInjectTest.MIN"&gt;
 * 					&lt;value type="java.lang.String"&gt;-100&lt;/value&gt;
 * 				&lt;/entry&gt;
 * 				&lt;entry key="test.dynamic.StaticFieldInjectTest.CC"&gt;
 * 					&lt;value type="java.lang.Integer"&gt;8888&lt;/value&gt;
 * 				&lt;/entry&gt;
 * 			&lt;/map&gt;
 * 		&lt;/property&gt;
 * 	&lt;/bean&gt;
 * </pre>
 */
public class StaticFieldInjectInitBean {
    private static final String ERRORSTR = "不正确的静态字段设置!";
    private transient final Logger log;
    private Map<String, Object> staticFieldInjectValueMap;

    public StaticFieldInjectInitBean() {
        log = LoggerFactory.getLogger(this.getClass());
    }

    @PostConstruct
    public void init() {
        if (isNotEmpty(staticFieldInjectValueMap)) {
            inject(staticFieldInjectValueMap);
        } else {
            log.trace("staticFieldInjectValueMap为空!");
        }
    }

    private void inject(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            try {
            	Field field = parse(entry.getKey());
                Class<?> clazz = field.getDeclaringClass();
                Object target = clazz.newInstance();
                Object val = convertType(field.getType(), entry.getValue());
                field.setAccessible(true);
                log.info("设置前##class:{},field:{};value:{}",
                        clazz.getCanonicalName(), field.getName(), field.get(target));

                field.set(target, val);

                log.info("设置后##class:{},field:{};value:{}",
                        clazz.getCanonicalName(), field.getName(), field.get(target));
            } catch (Exception e) {
                log.warn("静态字段设值出现异常:{}", e.getMessage());
            }
        }
    }

    private Object convertType(Class<?> target, Object value) {
        Object temp = null;
        if (target.equals(value.getClass())) {
            temp = value;
        } else {
            temp = ConvertUtils.convert(value, target);
        }
        return temp;
    }


    private Field parse(String clazzFieldStr) {

        int i = clazzFieldStr.lastIndexOf(".");
        validate(i == -1);

        String clazzName = clazzFieldStr.substring(0, i);
        String fieldName = clazzFieldStr.substring(i + 1, clazzFieldStr.length());
        validate(isBlank(clazzName) || isBlank(fieldName));

        try {
            Class<?> clazz = Class.forName(clazzName);
            Field field = clazz.getDeclaredField(fieldName);
            return field;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean isNotEmpty(Map<String, Object> map) {
        return !(map == null || map.isEmpty());
    }

    private boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void validate(boolean flag) {
        if (flag) {
            throw new RuntimeException(ERRORSTR);
        }
    }

    public Map<String, Object> getStaticFieldInjectValueMap() {
        return staticFieldInjectValueMap;
    }

    public void setStaticFieldInjectValueMap(Map<String, Object> staticFieldInjectValueMap) {
        this.staticFieldInjectValueMap = staticFieldInjectValueMap;
    }
}

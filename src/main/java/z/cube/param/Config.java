package z.cube.param;


import java.lang.annotation.*;

import static z.cube.param.SourceType.DATABASE;

/**
 * 编译出现不兼容类型，则通过静态导入解决或是使用javac手动编译；
 *
 * 1. 支持在方法参数上使用该注解
 * 2. 支持在复杂类上使用该注解
 */
@Documented
@Target({ElementType.PARAMETER,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /** 取值的键值 */
    String key() default "";

    /** 取值的目标来源*/
    SourceType source() default DATABASE;

    /** 参数描述 */
    String description() default "";

}

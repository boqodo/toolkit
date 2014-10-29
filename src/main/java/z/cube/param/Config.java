package z.cube.param;


import java.lang.annotation.*;


@Documented
@Target({ElementType.PARAMETER,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /** 取值的键值 */
    String key();

    /** 取值的目标来源 default enum jdk6不支持 */
    SourceType source() default SourceType.DATABASE;

    /** 参数描述 */
    String description() default "";

    /**
     * 数据源类型
     */
    public enum SourceType {
        DATABASE,               //数据库
        XML,                    //XML文件
        PROPERTIES              //属性文件
    }
}

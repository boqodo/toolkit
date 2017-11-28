package z.cube.spring;

import java.lang.annotation.*;


/**
 * 定时任务定义注解
 * 
 * 该注解在类的方法上进行使用，必须要指定name，同时如果该方法有参数必须要写明params
 * 
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskSchedule {
	
    /*** 任务名称,多个方法定时时,任务名称不能相同 */
    public String name();
    /*** 任务参数 
     * <pre>
     *  当参数类型为数组或是集合时，使用逗号拆分参数再转换成对应类型
     * 目前支持参数类型:
     *  @see String
     * 	@see Integer 
     *  @see Long
     *  @see Double
     *  @see java.util.List
     *  @see java.util.Set
     * </pre>
     * */
    public String[] params() default {};
    /*** 任务描述 */
    public String description() default "";
}

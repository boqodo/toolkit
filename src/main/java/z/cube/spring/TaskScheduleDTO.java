
package z.cube.spring;

import java.lang.reflect.Method;


public class TaskScheduleDTO {
	private String name; //任务名称
	private String description; //任务描述

	private String hostClazz; //宿主类
	private String hostMethod; //宿主方法
	private String methodParams; //方法参数
	
	private String uuid;		//类全名#方法名(参数类型全名....)
	
	//辅助字段
	private Method	method;			 //具体的方法

	public TaskScheduleDTO() {
		super();
	}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHostClazz() {
        return hostClazz;
    }

    public void setHostClazz(String hostClazz) {
        this.hostClazz = hostClazz;
    }

    public String getHostMethod() {
        return hostMethod;
    }

    public void setHostMethod(String hostMethod) {
        this.hostMethod = hostMethod;
    }

    public String getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(String methodParams) {
        this.methodParams = methodParams;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

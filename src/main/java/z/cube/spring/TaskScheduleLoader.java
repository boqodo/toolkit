package z.cube.spring;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TaskScheduleLoader implements ApplicationContextAware {
    public static final String              TASKSCHEDULE_LIST = "TaskScheduleList";
    public static final Map<String, Object> SOURCE            = new ConcurrentHashMap<String, Object>(8);
    private static Logger log = Logger.getLogger(TaskScheduleLoader.class);
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        log.debug("TaskScheduleLoader init" + this.applicationContext);
        Collection<TaskScheduleDTO> tsds = findTaskSchedule();
        TaskScheduleLoader.SOURCE.put(TaskScheduleLoader.TASKSCHEDULE_LIST, tsds);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 检索所有由spring托管的用service和component注解的类的方法
     * 提取出方法上使用TaskSchedule注解的方法
     * @return
     */
    private Collection<TaskScheduleDTO> findTaskSchedule() {
        String[] names = applicationContext.getBeanDefinitionNames();

		//存放任务名称,需要保证唯一性
		Map<String,TaskScheduleDTO> stmap=new HashMap<String,TaskScheduleDTO>(16);
		for (String name : names) {
			Class<?> clazz = applicationContext.getType(name);
			if (isServiceClazz(clazz)) {
				for (Method method : clazz.getMethods()) {
					for (Annotation annotation : method.getAnnotations()) {
						log.info("annotation" + annotation.annotationType());
						if (annotation instanceof TaskSchedule) {
							TaskSchedule taskSchedule = (TaskSchedule) annotation;
							
							String taskName = taskSchedule.name();
							if(stmap.containsKey(taskName)){
								throw new RuntimeException("TaskSchedule name 存在重复的情况!");
							}else{
								TaskScheduleDTO dto = new TaskScheduleDTO();
								dto.setName(taskName);
								dto.setDescription(taskSchedule.description());
								dto.setHostClazz(clazz.getName());
								String methodName = method.getName();
								dto.setHostMethod(methodName);
								Class<?>[] parameterTypes = method.getParameterTypes();
								String[] params = convertAndMerge2Params(parameterTypes, taskSchedule.params());
								dto.setMethodParams(StringUtils.join(params, ","));
								String clazzName = clazz.getName();
								dto.setUuid(generateUuid(clazzName,methodName,parameterTypes));
								dto.setMethod(method);
								stmap.put(taskName, dto);
							}
							log.debug(String.format("%s class,%s method", clazz, method));
							log.info(taskName + "%%" + taskSchedule.description());
						}
					}
				}
			}
		}
		return stmap.values();
	}
	
	/**
	 * 生成一个唯一的标识
	 * 使用类名#方法名(参数列表...)
	 * @param clazzName			类全称名
	 * @param methodName		方法名称
	 * @param parameterTypes	参数类型
	 * @return
	 */
	private String generateUuid(String clazzName,String methodName,Class<?>[] parameterTypes){
		String[] params=new String[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> item = parameterTypes[i];
			params[i]=item.getName();
		}
		return String.format("%s#%s(%s)",clazzName,methodName,StringUtils.join(params,","));
	}
	/**
	 * 转换参数和参数类型格式化成字符串，提供前台显示使用
	 * @param clazz		方法参数类型数组
	 * @param params	参数
	 * @return
	 */
	private String[] convertAndMerge2Params(Class<?>[] clazz, String[] params) {
		if (clazz.length != params.length) {
			throw new RuntimeException("TaskSchedule注解定义的params个数跟实际方法的参数个数不一致!");
		}
		String[] temp = new String[clazz.length];
		for (int i = 0; i < clazz.length; i++) {
			Class<?> item = clazz[i];
			temp[i] = String.format("%s;类型要求:%s", params[i], item.getSimpleName());
			log.debug(temp[i]);
		}
		return temp;
	}

	/**
	 * 是否为服务类
	 * (只检查用@Service和@Component注解的类)
	 */
	private boolean isServiceClazz(Class<?> clazz) {
		Annotation an = clazz.getAnnotation(Service.class);
		if (an == null) {
			an = clazz.getAnnotation(Component.class);
		}
		return an != null;
	}
}

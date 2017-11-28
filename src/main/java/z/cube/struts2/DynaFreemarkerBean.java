package z.cube.struts2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.util.ValueStack;
/**
 * 动态bean
 * 在ftl中需要输出一些不能直接通过对象实例获取的值，需要放入request中进行传递值，以便输出
 * 为了减少编写，以及替代在ftl中使用${Request["paramer"]}的情况
 * 通过该类可以直接生产一个动态bean，可直接通过对象实例获取值，如dynaBean.paramer
 * @author zzz
 */
public class DynaFreemarkerBean{
    private static final ThreadLocal<DynaFreemarkerBean> bean=new ThreadLocal<DynaFreemarkerBean>() ;

    private Map<String,Object> map=new HashMap<String,Object>(0);

    private DynaFreemarkerBean(){}
    private static DynaFreemarkerBean getInstance(){
        if (bean.get()==null){
            bean.set(new DynaFreemarkerBean());
        }
        return bean.get();
    }
    /**
     * 设置需要传递的值和键
     * @param key 属性键
     * @param value 属性键对应的值
     * @return DynaFreemarkerBean 以便进行链式添加
     */
    public static DynaFreemarkerBean put(String key,Object value){
        getInstance().map.put(key, value);
        return getInstance();
    }

    /**
     * 根据添加的键和值，生成动态bean
     * @return 返回由put进入的键值对生成的动态类
     */
    public Object create(){
        if(map!=null&&!map.isEmpty()){
            List<DynaProperty> properties=new ArrayList<DynaProperty>(0);
            for(Map.Entry<String, Object> entry : map.entrySet()){
                properties.add(new DynaProperty(entry.getKey()));
            }
            DynaProperty[] dps=properties.toArray(new DynaProperty[properties.size()]);
            DynaClass dynaClzz=new BasicDynaClass("dynaFreemarkerBean",BasicDynaBean.class,dps);
            try {
                DynaBean bean=dynaClzz.newInstance();
                for(DynaProperty property :properties){
                    bean.set(property.getName(), map.get(property.getName()));
                }

                return bean;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //throw new Exception("没有值无法创建，请调用put操作后，再调用create");
        }
        return null;
    }
    /**
     * 直接将动态生成的bean放入valuestack中
     * 在freemarker中可以直接使用 ${dyna.key}获取值
     * 备注：该方法默认变量引用名为dyna，如需要自定义则使用重载的另一个方法
     */
    public  void outToFreemarker(){
    	outToFreemarker("dyna");
    }
    /**
     * 根据holderName,直接将动态生成的bean放入valuestack中
     * 在freemarker中可以直接使用 ${holderName.key}获取值
     * @param holderName
     */
    public  void outToFreemarker(String holderName){
        ValueStack stack=ServletActionContext.getContext().getValueStack();
        stack.set(holderName, create());
    }
    public static void main(String[] args) throws Exception{
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String currentThreadName = Thread.currentThread().getName();
                    String one=new Random().nextInt(2000)+"";
                    DynaBean dynaBean=(DynaBean) put("one", one)
                            .put("two","fasfdsf")
                            .create();
                    System.out.println("thread " + currentThreadName + " set:"+one);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread " + currentThreadName + "read :" + dynaBean.get("one"));
                }
            }).start();
        }
    }
}

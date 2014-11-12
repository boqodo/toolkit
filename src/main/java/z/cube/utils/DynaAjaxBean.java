package z.cube.utils;

import org.apache.commons.beanutils.*;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 动态生成用于ajax的对象
 */
public class DynaAjaxBean {

    public static final String SPLITCHAR = ".";
    public static final String GET = "get";

    /**
     * 根据实现了Iterable接口，可以迭代集合或是其他类，动态生成bean集合
     * @param list  进行循环迭代生成bean的集合
     * @param properties 集合中的对象需要动态生成的属性
     * @return 包含循环生产的动态类的集合
     */
    public static synchronized List createDynaBeanList(Iterable list, String... properties) {
        List beans = new ArrayList(16);
        for (Object item : list) {
            beans.add(createDynaBean(item, properties));
        }
        return beans;
    }

    /**
     * 根据对象，以及对象属性字符串数组，生成对应的动态bean
     * 属性目前只支持字符串或数字类型，以及对象中需要包含对应的get方法并且是public
     * @param object 对象
     * @param properties 属性
     * @return 动态对象
     */
    public static synchronized Object createDynaBean(Object object,String...properties){
        DynaProperty[] dps=new DynaProperty[properties.length];
        for(int i=0;i<properties.length;i++){
            dps[i]=new DynaProperty(properties[i],String.class);
        }
        DynaClass dynaClzz=new BasicDynaClass("one",BasicDynaBean.class,dps);
        try {
            DynaBean bean=dynaClzz.newInstance();
            for(int i=0;i<properties.length;i++){
                Object str=object.getClass().getMethod(GET + StringUtils.capitalize(properties[i])).invoke(object);
                bean.set(properties[i],str+"");
            }
            return bean;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据给定的对象和属性值动态生成单一的动态bean
     *
     * 如：
     *  object :student
     *  properties:name,course.classRoom.number
     *
     *  return： bean
     * 可以组装成一个bean，通过bean.name即可获取学生的姓名；
     * 通过bean.course#classRomm#number即可取得学生课程教师编号；
     * 注意：此处属性使用 # 号进行拼接，该是为了减少创建，集中到一个bean
     * @param object    顶层对象
     * @param properties 属性值
     * @return
     */
    public static synchronized Object createSingleDynaBean(Object object,String...properties){
        String regex="#";
        DynaProperty[] dps=new DynaProperty[properties.length];
        try {
            for(int i=0;i<properties.length;i++){
                if(properties[i].contains(SPLITCHAR)){
                    properties[i]=properties[i].replace(SPLITCHAR, regex);
                }
                dps[i]=new DynaProperty(properties[i],String.class);
            }
            DynaClass dynaClzz=new BasicDynaClass("dyna",BasicDynaBean.class,dps);

            DynaBean bean=dynaClzz.newInstance();
            for(int i=0;i<properties.length;i++){
                if(properties[i].contains(regex)){
                    String[] propertyChain=properties[i].split("["+regex+"]");
                    Object obj=object.getClass().getMethod(GET +StringUtils.capitalize(propertyChain[0])).invoke(object);
                    for(int x=1;x<propertyChain.length;x++){
                        obj=obj.getClass().getMethod(GET +StringUtils.capitalize(propertyChain[x])).invoke(obj);
                    }
                    if(null!=obj){
                        bean.set(properties[i], obj+"");
                    }
                }else{
                    Object str=object.getClass().getMethod(GET +StringUtils.capitalize(properties[i])).invoke(object);
                    bean.set(properties[i],str+"");
                }
            }
            return bean;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 动态生成一个复杂bean，bean中包含其他的动态生成的bean
     *
     * @param object 顶层的对象
     * @param properties 需要动态生成的对象中的属性
     * @return  根据提供的对象和属性生成的动态bean
     */
    public static synchronized Object createComplexDynaBean(Object object, String... properties) {
        Map<String,List<String>> unPros=new HashMap<String,List<String>>(); //存放非基本属性
        Set<DynaProperty> dynaProperties=new HashSet<DynaProperty>(0);  //存放生成动态bean需要的属性
        Set<String> pros=new HashSet<String>(0);    //存放基本属性，不包含点的

        for(String property :properties){
            if(property.contains(SPLITCHAR)){
                String prefix=property.substring(0, property.indexOf(SPLITCHAR));
                String stuff=property.substring(property.indexOf(SPLITCHAR)+1);
                if(unPros.containsKey(prefix)){
                    unPros.get(prefix).add(stuff);
                }else {
                    List<String> value=unPros.get(prefix);
                    if (null==value) {
                        value=new ArrayList<String>(0);
                    }
                    value.add(stuff);
                    unPros.put(prefix,value);
                }
                dynaProperties.add(new DynaProperty(prefix, Object.class));
            }else{
                dynaProperties.add(new DynaProperty(property, Object.class));
                pros.add(property);
            }
        }
        if(!unPros.isEmpty()){
            DynaClass dynaClass=new BasicDynaClass("dynaClass",BasicDynaBean.class, dynaProperties.toArray(new DynaProperty[dynaProperties.size()]));
            try {
                DynaBean dynaBean=dynaClass.newInstance();
                for (String pro : pros){
                    Object value=object.getClass().getMethod(GET +StringUtils.capitalize(pro)).invoke(object);
                    dynaBean.set(pro,String.valueOf(value));
                }

                for(Map.Entry<String,List<String>> entry :unPros.entrySet()){
                    String subclassName= entry.getKey();
                    String[] propertyChina=entry.getValue().toArray(new String[entry.getValue().size()]);

                    Object subClassInstance=object.getClass().getMethod(GET +StringUtils.capitalize(subclassName)).invoke(object);
                    Object subBean= createComplexDynaBean(subClassInstance, propertyChina);

                    dynaBean.set(subclassName,subBean);
                }
                return dynaBean;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            DynaClass dynaClass=new BasicDynaClass("dynaClass",BasicDynaBean.class, dynaProperties.toArray(new DynaProperty[dynaProperties.size()]));
            try {
                DynaBean dynaBean=dynaClass.newInstance();
                for (String pro : pros){
                    Object value=object.getClass().getMethod(GET +StringUtils.capitalize(pro)).invoke(object);
                    dynaBean.set(pro,value);
                }
                return dynaBean;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static void main(String[] args) throws Exception {
        class Address{
            private String local;
            private int id;
            private Address address;
            private Point p;
        }
        class Card{
            private String name;
            private Card card;
            public Address(String local){
                this.local=local;
            }

            public Card(int id,Address address){
                this.id=id;
                this.address=address;
            }

            public Person(){}

            public Person(Point p,String name,Card card){
                this.p=p;
                this.name=name;
                this.card=card;

            }

            public String getLocal() {
                return local;
            }
        }

        class Person{

            public void setLocal(String local) {
                this.local = local;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
                this.address = address;
            }

            public Card getCard() {
                return card;
            }

            public void setCard(Card card) {
                this.card = card;
            }

            public Point getP() {
                return p;
            }

            public void setP(Point p) {
                this.p = p;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
        Person p=new Person(new Point(10,20),"zhangsan",new Card(10,new Address("zhejiang")));
        Object bean= createComplexDynaBean(p, "name", "p.x","p.y", "card.id", "card.address.local");
        String beanstr=JsonUtilsEx.generate(bean);
        System.out.println(beanstr);
    }
}

package java.z.cube.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import z.cube.utils.ClassUtils;
import z.cube.utils.JsonUtilsEx;
import z.cube.utils.Processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassUtilsTest {

    @SuppressWarnings("rawtypes")
	@Test
    public void testGetLoadedClasses() throws Exception {
        List<Class> classes= ClassUtils.getLoadedClasses();
        for(Class clazz:classes){
            System.out.println(clazz);
        }
    }
    @SuppressWarnings("rawtypes")
	@Test
    public void testListClasses() throws Exception{
        List<Class> classes=ClassUtils.listClasses(ResourceUtils.getFile("classpath:"));
        for(Class clazz:classes){
            System.out.println(clazz);
        }
    }
    @SuppressWarnings("rawtypes")
	@Test
    public void testListClassesPredicate() throws Exception{
        List<Class> classes=ClassUtils.listClasses(ResourceUtils.getFile("classpath:"),new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                Class clazz= (Class) object;
                return clazz.isEnum();
            }
        });
        for(Class clazz:classes){
            System.out.println(clazz);
        }
    }
    @Test
    public void testListClassesProcessor()throws Exception{
        Processor<Class,Map<String,String>> processor =new Processor<Class, Map<String, String>>() {
            List<Map<String, String>> store = new ArrayList<Map<String, String>>();

            @Override
            public Map<String, String> process(Class input) {
                Map<String, String> map = new HashMap<String, String>();
                if(input.isInterface()){
                    map.put(input.getPackage().getName(), input.getSimpleName());
                }
                return map;
            }

            @Override
            public void save(Map<String, String> output) {
                if(MapUtils.isNotEmpty(output)){
                    store.add(output);
                }
            }

            @Override
            public Object list() {
                return store;
            }
        };
        ClassUtils.listClasses(ResourceUtils.getFile("classpath:"), processor);
        String json= JsonUtilsEx.generate(processor.list());
        System.out.println(json);
    }
}
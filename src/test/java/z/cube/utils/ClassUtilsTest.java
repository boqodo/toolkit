package z.cube.utils;

import org.apache.commons.collections.Predicate;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.util.List;

public class ClassUtilsTest {

    @Test
    public void testGetLoadedClasses() throws Exception {
        List<Class> classes=ClassUtils.getLoadedClasses();
        for(Class clazz:classes){
            System.out.println(clazz);
        }
    }
    @Test
    public void testListClasses() throws Exception{
        List<Class> classes=ClassUtils.listClasses(ResourceUtils.getFile("classpath:"));
        for(Class clazz:classes){
            System.out.println(clazz);
        }
    }
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
}
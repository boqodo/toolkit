package java.z.cube.utils;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.util.ResourceUtils;
import z.cube.utils.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class JSONAssertTest {
    @Test
    public final void test() throws JSONException {
        String expected = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"bird\",\"fish\"]}],pets:[]}";
        String actual = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"cat\",\"fish\"]}],pets:[]}";
        JSONAssert.assertEquals(expected, actual, false);
    }
    @Test
    public final void test2() throws JSONException {
        String excpected="{name:'Lisi',age:2}";
        String actual="{age:2,name:'Lisi'}";
        JSONAssert.assertEquals(excpected,actual,false);
    }
    @Test
    public final void test3() throws Exception{
        URL url=Thread.currentThread().getContextClassLoader().getResource("config");
        System.out.println(url);
        
        File f=ResourceUtils.getFile("classpath:config");
        System.out.println(f.getAbsolutePath());
        File f2=new File("../../../config/xx.xml");
        System.out.println(f2.getAbsolutePath()+";"+f2.exists());
        
        System.out.println(IOUtils.toString(new FileInputStream(f2)));
    }
    @Test
    public final void test4() throws Exception{
        List<z.cube.utils.Person> ps=new ArrayList<z.cube.utils.Person>(8);
        ps.add(new z.cube.utils.Person("Lisi",38));
        ps.add(new z.cube.utils.Person("LiWu",21));
        ps.add(new z.cube.utils.Person("Qiqi",15));
        ps.add(new Person("Zizi",56));
        
        
        Object obj=PropertyUtils.getProperty(ps, "[0].name");
        System.out.println("--------" + obj);
        BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer( "age" );

        Collection personAges = CollectionUtils.collect(ps, transformer);
        System.out.println(personAges.toString());


        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class cla = classLoader.getClass();
        while (cla != ClassLoader.class) {
            cla = cla.getSuperclass();
        }
        Field field = cla.getDeclaredField("classes");
        field.setAccessible(true);
        Vector v = (Vector) field.get(classLoader);
        for (int i = 0; i < v.size(); i++) {
            System.out.println(((Class)v.get(i)).getName());
        }

    }

}

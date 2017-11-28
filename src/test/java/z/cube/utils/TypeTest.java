package java.z.cube.utils;

import net.vidageek.mirror.dsl.Mirror;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.reflect.*;
import org.joda.convert.StringConvert;
import org.joor.Reflect;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.*;
import z.cube.utils.Person;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Ref;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TypeTest {
    @Test
    public final void test(){
        Class p=int.class;
        System.out.println(p.getComponentType());
        System.out.println(int[].class.getComponentType());

        System.out.println(BeanUtils.isSimpleProperty(p));
        System.out.println(BeanUtils.isSimpleProperty(z.cube.utils.Person.class));
        System.out.println(BeanUtils.isSimpleProperty(String.class));
        System.out.println(BeanUtils.isSimpleProperty(Date.class));
        System.out.println(BeanUtils.isSimpleProperty(float.class));
        System.out.println(BeanUtils.isSimpleProperty(int.class));
        System.out.println(BeanUtils.isSimpleProperty(byte.class));
        System.out.println(BeanUtils.isSimpleProperty(BigDecimal.class));
        System.out.println(BeanUtils.isSimpleProperty(BigInteger.class));

        System.out.println(BeanUtils.isSimpleValueType(p));
        System.out.println(BeanUtils.isSimpleValueType(Person.class));
        System.out.println(BeanUtils.isSimpleValueType(String.class));
        System.out.println(BeanUtils.isSimpleValueType(Date.class));
        System.out.println(BeanUtils.isSimpleValueType(float.class));
        System.out.println(BeanUtils.isSimpleValueType(List.class));

//        Person person=Reflect.on(Person.class).create("Zhan",20).get();
//        System.out.println(person);
//        String name=Reflect.on(person).field("name").get();
//        Integer age =Reflect.on(person).call("Age").get();
//        System.out.println(name+";"+age);
        
       

    }
}

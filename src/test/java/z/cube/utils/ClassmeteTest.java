package java.z.cube.utils;

import com.fasterxml.classmate.*;
import com.fasterxml.classmate.members.RawMethod;
import com.fasterxml.classmate.members.ResolvedMethod;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassmeteTest {
     class StringIntMap extends HashMap<String,Integer> { }

    @Test
    public final void test1(){
        //https://github.com/cowtowncoder/java-classmate
        TypeResolver typeResolver = new TypeResolver();
        Map<String,Long> map=new HashMap<String, Long>();
        ResolvedType type = typeResolver.resolve(map.getClass());
        List<ResolvedType> mapParams = type.typeParametersFor(Map.class);
        ResolvedType keyType = mapParams.get(0);
        ResolvedType valueType = mapParams.get(1);
        System.out.println(keyType);
        System.out.println(valueType);
    }

    @Test
    public final void test2(){
        TypeResolver typeResolver = new TypeResolver();
        ResolvedType type = typeResolver.resolve(StringIntMap.class);
        List<ResolvedType> mapParams = type.typeParametersFor(Map.class);
        ResolvedType keyType = mapParams.get(0);
        ResolvedType valueType = mapParams.get(1);
        System.out.println(keyType);
        System.out.println(valueType);
    }
    @Test
    public final void test3(){
        TypeResolver typeResolver = new TypeResolver();
        ResolvedType listType = typeResolver.resolve(List.class);
        System.out.println(listType.typeParametersFor(List.class));
    }
    @Test
    public final void test4(){
        TypeResolver typeResolver = new TypeResolver();
        ResolvedType arrayListType = typeResolver.resolve(ArrayList.class, String.class);
        MemberResolver memberResolver = new MemberResolver(typeResolver);
        memberResolver.setMethodFilter(new Filter<RawMethod>() {
            @Override public boolean include(RawMethod element) {
                return "size".equals(element.getName());
            }
        });
        ResolvedTypeWithMembers arrayListTypeWithMembers = memberResolver.resolve(arrayListType, null, null);
        ResolvedMethod sizeMethod = arrayListTypeWithMembers.getMemberMethods()[0];
        System.out.println(sizeMethod);
    }
}

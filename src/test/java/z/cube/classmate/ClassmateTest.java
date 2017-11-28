package java.z.cube.classmate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.classmate.MemberResolver;
import com.fasterxml.classmate.ResolvedTypeWithMembers;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.members.RawField;
import com.fasterxml.classmate.members.RawMethod;
import com.fasterxml.classmate.members.ResolvedMethod;

public class ClassmateTest {

	TypeResolver typeResolver=new TypeResolver();
	@SuppressWarnings("serial")
	public class StringIntMap extends HashMap<String,Integer> { }
	@Test
	public final void test(){
		
		ResolvedType type = typeResolver.resolve(StringIntMap.class);
		List<ResolvedType> mapParams = type.typeParametersFor(Map.class);
		ResolvedType keyType = mapParams.get(0);
		ResolvedType valueType = mapParams.get(1);
		System.out.println(keyType+"\r\n"+valueType);
	}
	@Test
	public final void testMethodResolver(){
		ResolvedType arrayListType = typeResolver.resolve(ArrayList.class, String.class);
		MemberResolver memberResolver = new MemberResolver(typeResolver);
		ResolvedTypeWithMembers arrayListTypeWithMembers = memberResolver.resolve(arrayListType, null, null);
		// get static methods
		ResolvedMethod[] staticArrayListMethods = arrayListTypeWithMembers.getStaticMethods();
		// get instance methods
		ResolvedMethod[] arrayListMethods = arrayListTypeWithMembers.getMemberMethods();
	}
	@Test
	public final void testCustom(){
		ResolvedType type =typeResolver.resolve(Item.class);
		List<RawField> fs=type.getMemberFields();
		List<RawMethod> ms=type.getMemberMethods();
		System.out.println(fs+"\r\n"+ms);
	}
}

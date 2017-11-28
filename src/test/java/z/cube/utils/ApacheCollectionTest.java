package java.z.cube.utils;

import org.apache.commons.collections.IterableMap;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ApacheCollectionTest {
    @Test
    public final void test(){
        Map<String,Object> nmap=new HashMap<String, Object>();
        nmap.put("First",1);
        nmap.put("Second",2);
        nmap.put("Third",3);
        nmap.put("Fourth",4);
        IterableMap map = new HashedMap(nmap);
        MapIterator it = map.mapIterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object value = it.getValue();
            System.out.println(key+";"+value);
            //it.setValue(newValue);
        }
    }
}

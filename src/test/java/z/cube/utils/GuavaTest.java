package java.z.cube.utils;

import com.google.common.base.Optional;
import org.junit.Test;
import z.cube.utils.Person;

public class GuavaTest {

    @Test
    public final void test(){
        z.cube.utils.Person p=new z.cube.utils.Person();
        p.setName("Lisi");

        z.cube.utils.Person p2=null;
        Optional<Person> person =Optional.of(p2);
        

        if (person.isPresent()) {

            String n=person.get().getName();
            System.out.println(n);
        }
    }
}

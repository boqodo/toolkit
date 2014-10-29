package z.cube.utils;

import com.google.common.base.Optional;
import org.junit.Test;

public class GuavaTest {

    @Test
    public final void test(){
        Person p=new Person();
        p.setName("Lisi");

        Person p2=null;
        Optional<Person> person =Optional.of(p2);

        if (person.isPresent()) {

            String n=person.get().getName();
            System.out.println(n);
        }
    }
}

package z.cube.param;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import z.cube.podam.ExPackage;
import z.cube.utils.Person;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FlexSessionConfigHandlerTest {

    @Test
    public void testGetValue() throws Exception {
        FlexSession session= mock(FlexSession.class);
        Integer age = 12;
        String name = "Lisi";
        Person person=new Person(name, age);
        when(session.getAttribute("user")).thenReturn(person);
        when(session.getAttribute("age")).thenReturn(person.getAge());

        PodamFactory factory = new PodamFactoryImpl();
        ExPackage exPackage=factory.manufacturePojo(ExPackage.class);
        when(session.getAttribute("exPackage")).thenReturn(exPackage);

        FlexContext.setThreadLocalSession(session);
        FlexSessionConfigHandler configHandler=new FlexSessionConfigHandler();
        configHandler.init(null);
        String nameVal= (String) configHandler.getValue("user.name");
        assertEquals(name,nameVal);
        verify(session).getAttribute("user");


        Integer ageVal= (Integer) configHandler.getValue("age");
        assertEquals(age, ageVal);
        verify(session).getAttribute("age");

        String exVal= (String) configHandler.getValue("exPackage.req.main.value[0]");
        assertEquals(exPackage.getReq().getMain().getValue().get(0), exVal);
        verify(session).getAttribute("exPackage");
    }
}
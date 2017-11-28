package java.z.cube.mockito;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MockitoDemo {

    @Test
    public final void test() {
        // mock creation
        List mockedList = mock(List.class);

        // using mock object ; observe that it didn't throw any "unexpected interaction exception" exception
        mockedList.add("one");
        mockedList.clear();

        // selective & explicit verification
        verify(mockedList).add("one");
        verify(mockedList).clear();

    }
    @Test
    public final void test2(){
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing; before the actual execution
        when(mockedList.get(0)).thenReturn("first");

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not subbed
        System.out.println(mockedList.get(999));
    }
}

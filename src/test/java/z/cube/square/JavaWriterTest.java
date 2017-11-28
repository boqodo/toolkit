package java.z.cube.square;

import com.squareup.javawriter.JavaWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static javax.lang.model.element.Modifier.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.EnumSet;

@RunWith(JUnit4.class)
public class JavaWriterTest {

    @Test
    public final void test() throws IOException {
        JavaWriter writer = new JavaWriter(new OutputStreamWriter(System.out));
        writer.emitPackage("com.example")
                .beginType("com.example.Person", "class", EnumSet.of(PUBLIC, FINAL))
                .emitField("String", "firstName", EnumSet.of(PRIVATE))
                .emitField("String", "lastName", EnumSet.of(PRIVATE))
                .emitJavadoc("Returns the person's full name.")
                .beginMethod("String", "getName", EnumSet.of(PUBLIC))
                .emitStatement("return firstName + \" \" + lastName")
                .endMethod()
                .endType().close();
    }
}

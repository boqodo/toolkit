package java.z.cube.utils;

import org.junit.Test;
import org.springframework.util.ResourceUtils;
import z.cube.utils.ClassFileScanner;
import z.cube.utils.JsonUtilsEx;

import java.io.File;

import static org.junit.Assert.*;

public class ClassFileScannerTest {

    @Test
    public void testLoadConstants() throws Exception {
        System.out.println(JsonUtilsEx.generate(ClassFileScanner.loadConstants()));

        File file= ResourceUtils.getFile("classpath:z/cube/spring");
        System.out.println(JsonUtilsEx.generate(ClassFileScanner.loadConstants(file)));

        File file2= ResourceUtils.getFile("classpath:z/cube/temp");
        System.out.println(JsonUtilsEx.generate(ClassFileScanner.loadConstants(file2.getAbsolutePath())));
    }
}
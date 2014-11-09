package z.cube.utils;

import org.springframework.cglib.core.DebuggingClassWriter;

public class DebugUtils {

    public static void registerCglib(String outDir) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, outDir);
    }
}

package z.cube.utils;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang3.CharEncoding;

import java.io.File;
import java.io.FileReader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YamlUtils {

    public static <T> List<T> parse(String fileName, Class<T> clazz) {
        URL url = YamlUtils.class.getClassLoader().getResource(fileName);
        return parse(new File(url.getFile()), clazz);
    }


    public static <T> List<T> parse(File file, Class<T> clazz) {
        try {
            YamlReader reader = new YamlReader(new FileReader(file));
            List<T> temps = new ArrayList<T>();
            T temp = null;
            while ((temp = reader.read(clazz)) != null) {
                temps.add(temp);
            }
            reader.close();
            return temps;
        } catch (Exception e) {
            String msg = String.format("Yaml parse file to class fail!(file:%s;clazz:%s)",
                    file.getPath(), clazz.getName());
            throw new RuntimeException(msg + ";" + e.getMessage());
        }
    }

    public static <T> void generate(T t, String filepatch) {
        generate(t, new File(filepatch));
    }

    public static <T> void generate(T t, File outPath) {
        try {

            Writer out = new FileWriterWithEncoding(outPath, CharEncoding.UTF_8);
            YamlWriter writer = new YamlWriter(out);
            writer.write(t);
            writer.close();
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

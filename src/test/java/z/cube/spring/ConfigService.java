package z.cube.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import z.cube.param.Config;
import z.cube.param.SourceType;

@Service
public class ConfigService {

    public void configProperties(@Config(key = "colors.background", source = SourceType.PROPERTIES)
                                 String out) {
        System.out.println(out);
    }

    public void configProperties(
            @Config(key = "window.width", source = SourceType.PROPERTIES)
            Integer width) {
        System.out.println("window.width:" + width);
    }

    public void configDatabase(@Config(key = "config", source = SourceType.DATABASE) String[] out) {
        System.out.println(String.format("length:%s;{%s}", out.length, StringUtils.join(out, "#")));
    }

    public void configDatabase(@Config(key = "Second", source = SourceType.DATABASE) Integer f) {
        System.out.println("database=" + f);
    }
}

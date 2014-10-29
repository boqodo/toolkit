package z.cube.utils;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtilsEx {
    private static final ObjectMapper MAPPER = new ObjectMapper();    //ObjectMapper线程安全,不处理

    public static <T> T parse(String jsonString, Class<T> valueType) {
        try {
            return MAPPER.readValue(jsonString, valueType);
        } catch (IOException e) {
            throw new RuntimeException(String.format("解析JSON字符串失败!(json:%s)",jsonString));
        }
    }

    public static String generate(Object object){
        return generate(object,false);
    }
    public static String generate(Object object,boolean isPretty){
        try {
            if(isPretty){
                return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            }
            return MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(String.format("生成JSON字符串失败!(object:%s)",object.toString()));
        }
    }
}

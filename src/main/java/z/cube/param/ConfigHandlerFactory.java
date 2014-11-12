package z.cube.param;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandlerFactory {
    private static final String              INSTANCE_SUFFIX = "#Instance";
    private static final Map<Object, Object> HANDLER_MAP     = Collections.synchronizedMap(new HashMap<Object, Object>());

    static {
        HANDLER_MAP.put(SourceType.DATABASE, DatabaseConfigHandler.class);
        HANDLER_MAP.put(SourceType.PROPERTIES, PropertiesConfigHandler.class);
        HANDLER_MAP.put(SourceType.XML,XmlConfigHandler.class);
    }

    public static void registerHandler(SourceType sourceType, Class<? extends ConfigHandler> clazz) {
        if (!HANDLER_MAP.containsKey(sourceType)) {
            HANDLER_MAP.put(sourceType, clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public static ConfigHandler getConfigHandler(SourceType sourceType, InitConfig initConfig) {
        String instanceKey = sourceType.toString() + INSTANCE_SUFFIX;
        ConfigHandler ch = null;
        if (HANDLER_MAP.containsKey(instanceKey)) {
            ch = (ConfigHandler) HANDLER_MAP.get(instanceKey);
        } else {
            Class<? extends ConfigHandler> clazz = (Class<? extends ConfigHandler>) HANDLER_MAP.get(sourceType);
            try {
                ch = clazz.newInstance();
                ch.init(initConfig);
                HANDLER_MAP.put(instanceKey, ch);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ch;
    }
}

package z.cube.param;


public interface ConfigHandler {

    void init(InitConfig initConfig);

    Object getValue(String key);
}

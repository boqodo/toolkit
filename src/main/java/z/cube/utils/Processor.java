package z.cube.utils;

/**
 * 处理器
 * 将输入类通过process处理后转换成输出类;
 * 调用save方法保存输出类，最后根据list方法获取所有的输出类;
 * @param <In>      输入类
 * @param <Out>     输出类
 */
public interface Processor<In,Out> {

    /**
     * 加工处理方法
     * @param input     输入类
     * @return  输出类
     */
    public Out process(In input);

    /**
     * 保存输出类
     * @param output    输出类
     */
    public void save(Out output);

    /**
     * 获取所有输出类
     * @return  保存所有输出类的对象
     */
    public Object list();
}

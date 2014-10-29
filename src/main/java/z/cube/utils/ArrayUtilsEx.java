package z.cube.utils;

import org.apache.commons.lang3.Validate;

public class ArrayUtilsEx {

    private static final String ARRAY_NOT_EMPTY="list not empty";
    private static final int ZERO=0;

    /**
     * 获取数组的第一个元素
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getFirstElement(T[] list){
        Validate.notEmpty(list,ARRAY_NOT_EMPTY);
        return list[ZERO];
    }

    /**
     * 获取数组的最后一个元素
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getLastElement(T[] list){
        Validate.notEmpty(list,ARRAY_NOT_EMPTY);
        return list[list.length-1];
    }
}

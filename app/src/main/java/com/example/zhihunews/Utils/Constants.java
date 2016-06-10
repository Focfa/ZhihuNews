package com.example.zhihunews.Utils;

import java.util.Arrays;

/**
 * Created by Lxq on 2016/5/25.
 */
public class Constants {

    public static final String ID = "id";
    public static final String DATE ="date";
    public static final String TITLE = "title";
    public static final String POSITION = "position";
    public static final String LIST = "LIST";
    public static final String URL = "url";
    public static final String GA_PREFIX = "ga_prefix";
    public static final String NEWS_TYPE = "newsType";
    public static final String HOT_NEWS = "hotNews";
    public static final String COMMON_NEWS = "commonNews";

    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}

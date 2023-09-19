package top.cutexingluo.tools.utils.se.array;


import org.springframework.lang.NonNull;
import top.cutexingluo.tools.aop.log.LogHandler;
import top.cutexingluo.tools.aop.log.LogType;
import top.cutexingluo.tools.utils.se.map.XTMapUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 简单数组工具类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 18:11
 */
public class XTArrayUtil {
    public static <T> void printlnArray(T[] array) {
        for (T item : array) {
            System.out.println(item);
        }
    }

    public static <T> void printlnList(List<T> list) {
        for (T item : list) {
            System.out.println(item);
        }
    }

    /**
     * 输出列表的值到指定位置
     */
    public static <T> void logList(List<T> list, LogType logType) {
        LogHandler handler = new LogHandler(logType);
        for (T item : list) {
            handler.send(item.toString());
        }
    }

    /**
     * 输出数组的值到指定位置
     */
    public static <T> void logList(T[] array, LogType logType) {
        LogHandler handler = new LogHandler(logType);
        for (T item : array) {
            handler.send(item.toString());
        }
    }

    /**
     * 去重
     */
    public static <T> List<T> distinct(List<T> array) {
        return array.stream().distinct().collect(Collectors.toList());
    }


    /**
     * int[] 转为 List
     */
    public static List<Integer> toList(int[] array) {
        return Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    /**
     * 把values按K，V形式放入
     */
    public static <K, V> void putMapFromDValues(@NonNull Map<K, V> map, Object... values) {
        XTMapUtil.putMapEntriesFromDValues(map, values);
    }
}

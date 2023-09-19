package top.cutexingluo.tools.utils.se.file;

import cn.hutool.core.util.StrUtil;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 替换工具类
 * <p>
 * 专门替换 ${} 字符串
 * </p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 14:42
 */
public class XTPickUtil {

    public static int containsBraces(String line) {
        int index = line.indexOf("${");
        if (index == -1 || index > 0 && line.charAt(index - 1) == '\\') return -1;
        return index;
    }

    // 截取 ${}
    public static String getKeyFromBraces(String line) {
        int index = containsBraces(line);
        if (index == -1) return null;
        int l = index + 1;
        int r = line.indexOf("}", l + 1);
        return line.substring(l + 1, r).trim();
    }

    // 替换
    public static String putValueFromBraces(String line, Predicate<String> isContains, String value) {
        int index = containsBraces(line);
        if (index == -1) return line;
        int l = index + 1;
        int r = line.indexOf("}", l + 1);
        String inner = line.substring(l + 1, r).trim();
        if (StrUtil.isBlank(inner)) return line;
        boolean test = isContains.test(inner);
        if (!test) return line;
        return line.substring(0, index) + value + line.substring(r + 1);
    }

    //替换
    public static String putValueFromBraces(String line, Predicate<String> isContains, Function<String, String> map) {
        int index = containsBraces(line);
        if (index == -1) return line;
        int l = index + 1;
        int r = line.indexOf("}", l + 1);
        String key = line.substring(l + 1, r).trim();
        boolean test = isContains.test(key);
        if (!test) return line;
        return line.substring(0, index) + map.apply(key) + line.substring(r + 1);
    }

    // 取出
    public static String takeValueFromBraces(String line, Function<String, String> map) {
        int index = containsBraces(line);
        if (index == -1) return line;
        int l = index + 1;
        int r = line.indexOf("}", l + 1);
        return line.substring(0, index) + map.apply(line.substring(l + 1, r).trim()) + line.substring(r + 1);
    }
}

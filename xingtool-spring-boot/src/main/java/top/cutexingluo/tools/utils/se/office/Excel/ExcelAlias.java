package top.cutexingluo.tools.utils.se.office.Excel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 22:05
 */
public class ExcelAlias {
    @SafeVarargs
    public static <T> Map<T, T> getAliasMap(T... res) {
        LinkedHashMap<T, T> map = new LinkedHashMap<>();
        for (int i = 0; i < res.length-1; i += 2) {
            map.put(res[i], res[i + 1]);
        }
        return map;
    }
}

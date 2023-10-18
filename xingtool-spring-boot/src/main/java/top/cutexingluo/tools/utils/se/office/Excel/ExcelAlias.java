package top.cutexingluo.tools.utils.se.office.Excel;

import top.cutexingluo.tools.utils.se.map.XTHashMap;
import top.cutexingluo.tools.utils.se.map.XTMapUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据合并
 * <p>建议直接使用 {@link  XTMapUtil}</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 22:05
 */
public class ExcelAlias {
    /**
     * 每两个数据组成一组，顺序保存
     */
    public static Map<Object, Object> getAliasMap(Object... res) {
        LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
        XTMapUtil.putMapEntriesNoResFromDValues(map, res);
        return map;
    }
}

package top.cutexingluo.tools.utils.se.character;

import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;

/**
 * regex 组合 工具类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/20 15:55
 */
public class RegexUtil extends ReUtil implements RegexPool {
    /**
     * 任意匹配即可成功
     *
     * @param regex    正则表达式
     * @param contents 内容
     * @return boolean
     */
    public static boolean isAnyMatch(String regex, CharSequence... contents) {
        for (CharSequence content : contents) {
            // 进行匹配
            if (isMatch(regex, content)) {
                return true;
            }
        }
        return false;
    }
}

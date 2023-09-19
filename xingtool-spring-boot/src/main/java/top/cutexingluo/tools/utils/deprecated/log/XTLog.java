package top.cutexingluo.tools.utils.deprecated.log;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * 老式Log类，历史遗产，不建议使用<br>
 * 推荐使用@Slf4j注解
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 21:43
 */
@Deprecated
public class XTLog {
    /**
     * hutool 日志工具，需导入hutool依赖
     */
    public static final Log LOG = Log.get();
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static Log log;

    public static Log getLog(Class<?> clazz) {
        if (log == null) {
            log = LogFactory.get(clazz);
        }
        return log;
    }

    public static void info(String s, Object... args) {
        if (log != null) log.info(s, args);
    }
}

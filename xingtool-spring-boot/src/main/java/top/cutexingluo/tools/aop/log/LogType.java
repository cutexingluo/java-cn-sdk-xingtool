package top.cutexingluo.tools.aop.log;

/**
 * 日志类型
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/6 18:59
 */
public enum LogType {
    /**
     * System 类型, 输出到终端, 即 System.out.println
     */
    System,
    /**
     * log.debug
     */
    Debug,
    /**
     * log.error
     */
    Error,
    /**
     * log.warn
     */
    Warn,
    /**
     * log.info
     */
    Info,
}

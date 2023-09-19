package top.cutexingluo.tools.aop.log.printlog.custom;

import org.aspectj.lang.ProceedingJoinPoint;
import top.cutexingluo.tools.aop.log.printlog.PrintLog;

/**
 * 打印日志适配器
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/18 16:26
 */
@FunctionalInterface
public interface PrintLogAdapter {

    /**
     * 方法之前执行
     *
     * @param joinPoint 连接点
     * @param printLog  打印日志，里面可提出key值
     */
    void beforeRun(ProceedingJoinPoint joinPoint, PrintLog printLog);

    /**
     * 方法之后执行，返回方法值
     *
     * @param result 结果
     * @return {@link Object}
     */
    default Object afterRun(Object result) {
        return result;
    }
}

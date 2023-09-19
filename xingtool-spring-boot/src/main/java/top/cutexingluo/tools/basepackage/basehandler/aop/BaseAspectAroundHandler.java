package top.cutexingluo.tools.basepackage.basehandler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.jetbrains.annotations.NotNull;

/**
 * Aop around接口
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/16 15:01
 */
@FunctionalInterface
public interface BaseAspectAroundHandler<T> {

//    default Object around(@NotNull ProceedingJoinPoint joinPoint, T t) throws Throwable {
//        return joinPoint.proceed();
//    }

    Object around(@NotNull ProceedingJoinPoint joinPoint, T t) throws Throwable;
}

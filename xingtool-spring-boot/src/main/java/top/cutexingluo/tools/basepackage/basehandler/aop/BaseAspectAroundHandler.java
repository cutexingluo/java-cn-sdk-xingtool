package top.cutexingluo.tools.basepackage.basehandler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

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


    /**
     * 获得任务，手动操作inCatch，否则直接输出异常
     */
    default <V> Callable<V> getTask(ProceedingJoinPoint joinPoint, Consumer<Exception> inCatch) {
        return () -> {
            V result = null;
            try {
                result = (V) getTask(joinPoint).call();
            } catch (Exception e) {
                if (inCatch != null) inCatch.accept(e);
                else e.printStackTrace();
            }
            return result;
        };
    }

    /**
     * 获得任务, 装饰重新抛出
     */
    default <V> Callable<V> getTask(ProceedingJoinPoint joinPoint) {
        return () -> {
            V result = null;
            try {
                if (joinPoint != null) result = (V) joinPoint.proceed();
            } catch (Exception e) {
                throw e;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
            return result;
        };
    }

    /**
     * 获得任务, 如果不允许执行就跳过
     */
    default <V> Callable<V> getTask(ProceedingJoinPoint joinPoint, Supplier<Boolean> canRunTask) {
        return () -> {
            if (canRunTask != null && !canRunTask.get()) {
                return null;
            }
            V result = null;
            try {
                if (joinPoint != null) result = (V) joinPoint.proceed();
            } catch (Exception e) {
                throw e;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
            return result;
        };
    }
}

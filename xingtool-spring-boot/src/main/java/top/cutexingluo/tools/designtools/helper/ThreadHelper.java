package top.cutexingluo.tools.designtools.helper;

import top.cutexingluo.tools.basepackage.baseimpl.XTRunCallUtil;
import top.cutexingluo.tools.designtools.juc.async.XTAsync;
import top.cutexingluo.tools.designtools.juc.async.XTCompletionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 多线程 helper 类
 * <p>1. 通过实现该接口即可使用里面的方法</p>
 * <p>2.一些简单实现。其他自行使用 {@link  XTCompletionService} 和 {@link  XTAsync}</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/10/17 16:05
 * @since 1.0.2
 */
@FunctionalInterface
public interface ThreadHelper {
    /**
     * 配置线程池
     *
     * @return 线程池
     */
    Executor threadPoolExecutor();

    //---------------------XTCompletionService------------------------

    default XTCompletionService<Object> newCompletionService() {
        return new XTCompletionService<>(threadPoolExecutor());
    }

    /**
     * 提交任务
     */
    default <V> Future<V> submit(XTCompletionService<V> completionService, Callable<V> task) {
        return completionService.submit(task);
    }

    /**
     * 提交所有任务
     */
    default <V> ArrayList<Future<V>> submit(XTCompletionService<V> completionService, List<Callable<V>> tasks) {
        return completionService.submitAll(tasks);
    }

    //---------------------XTAsync------------------------

    /**
     * 执行任务
     */
    default <V> CompletableFuture<V> supplyAsync(Callable<V> task) {
        return XTAsync.supplyAsync(XTRunCallUtil.getTrySupplier(task), threadPoolExecutor());
    }

    /**
     * 执行任务
     */
    default <V> CompletableFuture<V> supplyAsync(Callable<V> task, Function<Throwable, V> exceptionHandle) {
        return XTAsync.supplyAsync(XTRunCallUtil.getTrySupplier(task), exceptionHandle, threadPoolExecutor());
    }

    /**
     * 执行系列任务
     *
     * @return CompletableFuture 列表
     */
    default <V> List<CompletableFuture<V>> getParallelFutureJoin(List<Callable<V>> tasks, BiFunction<Throwable, Callable<V>, V> exceptionHandle) {
        return XTAsync.getParallelFutureJoin(tasks, exceptionHandle, threadPoolExecutor());
    }
}

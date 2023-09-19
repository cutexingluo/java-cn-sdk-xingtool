package top.cutexingluo.tools.designtools.juc.async;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * XTAsync.run()<br>
 * 异步工具类
 * <br>继承自 CompletableFuture
 *
 * @author XingTian
 * @date 2023/2/2 18:39
 */

public class XTAsync<T> extends CompletableFuture<T> {

    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return this;
    }

    //**********常用四大件
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    @NotNull
    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return CompletableFuture.runAsync(runnable, executor);
    }

    //回调使用
    //whenComplete((t,u)->{}).exceptionally((e)->{}).get()
    //whenApply, whenRun, whenComplete
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> logic, Function<Throwable, T> exceptionHandle, Executor executor) {
        return CompletableFuture.supplyAsync(logic, executor).exceptionally(exceptionHandle);
    }

    /**
     * 创建单个CompletableFuture任务
     *
     * @param logic           任务逻辑
     * @param exceptionHandle 异常处理
     * @param <T>             类型
     * @return 任务
     */
    public static <T> CompletableFuture<T> createFuture(Supplier<T> logic, Function<Throwable, T> exceptionHandle, Executor executor) {
        return supplyAsync(logic, exceptionHandle, executor);
    }

    // js   then
    public CompletableFuture<T> then(BiConsumer<? super T, ? super Throwable> action) {
        return whenComplete(action);
    }

    // 异常默认返回
    public CompletableFuture<T> errorReturn(Function<Throwable, ? extends T> errorReturnDefault) {
        return exceptionally(errorReturnDefault);
    }

    // catch
    public CompletableFuture<T> jsCatch(Function<Throwable, ? extends T> errorReturnDefault) {
        return exceptionally(errorReturnDefault);
    }

    // handler res有 err有自己处理
    // allOf anyOf
    // thenAccept

    //-------------


    /**
     * 创建并行任务并执行
     *
     * @param list            数据源
     * @param api             API调用逻辑
     * @param exceptionHandle 异常处理逻辑
     * @param <S>             数据源类型
     * @param <T>             程序返回类型
     * @return 处理结果列表
     */
    public static <S, T> List<T> parallelFutureJoin(Collection<S> list, Function<S, T> api, BiFunction<Throwable, S, T> exceptionHandle, Executor executor) {
        //规整所有任务
        List<CompletableFuture<T>> collectFuture = list.stream()
                .map(s -> createFuture(() -> api.apply(s), e -> exceptionHandle.apply(e, s), executor)).collect(Collectors.toList());
        //汇总所有任务，并执行join，全部执行完成后，统一返回
        return collectFuture.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


}


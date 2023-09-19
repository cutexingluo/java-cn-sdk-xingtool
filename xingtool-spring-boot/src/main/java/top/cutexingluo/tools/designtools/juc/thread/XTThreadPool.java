package top.cutexingluo.tools.designtools.juc.thread;


import lombok.Data;
import top.cutexingluo.tools.designtools.juc.utils.XTJUC;

import java.util.List;
import java.util.concurrent.*;

/**
 * 常用，推荐用，<br>
 * JUC 线程池 ，主要用于 获取线程池，直接运行线程池
 * <p>
 *
 * @author XingTian
 * @version 1.0
 * @date 2022-11-21
 */
@Data
public class XTThreadPool {
    private static volatile XTThreadPool instance;
    // 核心是这个ThreadPoolExecutor，封装了一层是避免冲突
    private ThreadPoolExecutor threadPool;

    public XTThreadPool execute(Runnable runnable) {//执行
        threadPool.execute(runnable);
        return this;
    }

    public XTThreadPool shutdown() {//关闭
        threadPool.shutdown();
        return this;
    }

    public void runThreadAndDown(Runnable runnable) {//内部执行
        threadPool.execute(runnable);
        threadPool.shutdown();
    }

    public void executeListAndDown(List<Runnable> list) {
        list.forEach(threadPool::execute);
        threadPool.shutdown();
    }

    public static void runNewToolAndDown(Runnable runnable) {//静态一次执行
        XTThreadPool threadPool = new XTThreadPool();
        threadPool.execute(runnable);
        threadPool.shutdown();
    }

    public static void runNewToolAndDown(List<Runnable> list) {//静态一次执行
        XTThreadPool threadPool = new XTThreadPool();
        list.forEach(threadPool::execute);
        threadPool.shutdown();
    }

    public XTThreadPool(int corePoolSize,
                        int maximumPoolSize,
                        long keepAliveTime,
                        TimeUnit unit,
                        BlockingQueue<Runnable> workQueue,
                        ThreadFactory threadFactory,
                        RejectedExecutionHandler rejectedExecutionHandler) {
        this.threadPool = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, rejectedExecutionHandler);
    }

    public static XTThreadPool getInstance() { //单例
        if (instance == null) {
            synchronized (XTThreadPool.class) {
                if (instance == null) {
                    instance = new XTThreadPool();
                }
            }
        }
        return instance;
    }

    public XTThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPool = threadPoolExecutor;
    }

    //如果 12 核，默认核心线程 3 核，最大 9 核，阻塞 4 个 ，拒绝策略 主线程备用
    public XTThreadPool() {
        this(XTJUC.getCoresNumber() / 4, XTJUC.getCoresNumber() / 4 * 3,
                2, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(4), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public XTThreadPool(int cores, int maxSize, int aliveTimeout, TimeUnit unit, int queueSize,
                        RejectedExecutionHandler rejectedExecutionHandler) {
        this(cores, maxSize,
                aliveTimeout, unit,
                new LinkedBlockingDeque<>(queueSize), Executors.defaultThreadFactory(),
                rejectedExecutionHandler);
    }

    public XTThreadPool(int cores, int maxSize, int aliveTimeout, TimeUnit unit, int queueSize,
                        RejectPolicy rejectPolicy) {
        this(cores, maxSize,
                aliveTimeout, unit,
                new LinkedBlockingDeque<>(queueSize), Executors.defaultThreadFactory(),
                getPolicy(rejectPolicy));
    }

    public XTThreadPool(int cores, int maxSize, int queueSize,
                        RejectedExecutionHandler rejectedExecutionHandler) {
        this(cores, maxSize,
                2, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueSize), Executors.defaultThreadFactory(),
                rejectedExecutionHandler);
    }

    public XTThreadPool(int cores, int maxSize, int queueSize,
                        RejectPolicy rejectPolicy) {
        this(cores, maxSize,
                2, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueSize), Executors.defaultThreadFactory(),
                getPolicy(rejectPolicy));
    }

    public static RejectedExecutionHandler getPolicy(RejectPolicy rejectPolicy) {
        switch (rejectPolicy) {
            case Abort:
                return new ThreadPoolExecutor.AbortPolicy();
            case Discard:
                return new ThreadPoolExecutor.DiscardPolicy();
            case DiscardOldest:
                return new ThreadPoolExecutor.DiscardOldestPolicy();
            default:
                return new ThreadPoolExecutor.CallerRunsPolicy();
        }
    }
}

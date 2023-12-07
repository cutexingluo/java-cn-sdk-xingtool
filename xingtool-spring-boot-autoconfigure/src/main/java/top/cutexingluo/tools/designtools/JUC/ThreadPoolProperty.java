package top.cutexingluo.tools.designtools.JUC;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 线程池基础数据
 * <p>配置文件-线程池</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 16:03
 */
@Data
@ConfigurationProperties(prefix = "xingtools.xt-thread-pool")
@Import(ThreadPoolConfiguration.class)
public class ThreadPoolProperty {
    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxPoolSize;
    /**
     * 活跃时间
     */
    private long keepAliveTime;
    /**
     * 时间单位
     */
    private TimeUnit unit;
    /**
     * 阻塞队列
     */
    private BlockingQueue<Runnable> workQueue;
    /**
     * 线程工厂
     */
    private ThreadFactory threadFactory;
    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler handler;
}

package top.cutexingluo.tools.designtools.JUC;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 16:03
 */
@Data
@ConfigurationProperties(prefix = "xingtools.xt-thread-pool")
@Import(ThreadPoolConfiguration.class)
public class ThreadPoolProperty {
    private int corePoolSize;
    private int maxPoolSize;
    private int keepAliveTime;
    private TimeUnit unit;
    private BlockingQueue<Runnable> workQueue;
    private ThreadFactory threadFactory;
    private RejectedExecutionHandler handler;
}

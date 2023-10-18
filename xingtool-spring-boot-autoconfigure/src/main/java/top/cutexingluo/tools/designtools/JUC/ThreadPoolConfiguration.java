package top.cutexingluo.tools.designtools.JUC;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import top.cutexingluo.tools.designtools.juc.thread.XTThreadPool;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 16:14
 */

@ConditionalOnProperty(prefix = "xingtools.enabled", name = "xt-thread-pool", havingValue = "true", matchIfMissing = false)
@ConditionalOnBean(ThreadPoolProperty.class)
public class ThreadPoolConfiguration {

    @Autowired
    private ThreadPoolProperty threadPoolProperty;

    public ThreadPoolExecutor newThreadPoolExecutor() {
        if (threadPoolProperty == null) {
            threadPoolProperty = new ThreadPoolProperty();
        }
        XTThreadPool threadPool = new XTThreadPool();
        ThreadPoolExecutor threadPoolExecutor = threadPool.getThreadPool();
        if (threadPoolProperty.getCorePoolSize() < 0) {
            threadPoolProperty.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        }
        if (threadPoolProperty.getMaxPoolSize() < 0) {
            threadPoolProperty.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        }
        if (threadPoolProperty.getUnit() == null) {
            threadPoolProperty.setUnit(TimeUnit.SECONDS);
        }
        if (threadPoolProperty.getKeepAliveTime() < 0) {
            threadPoolProperty.setKeepAliveTime(threadPoolExecutor.getKeepAliveTime(threadPoolProperty.getUnit()));
        }
        if (threadPoolProperty.getWorkQueue() == null) {
            threadPoolProperty.setWorkQueue(threadPoolExecutor.getQueue());
        }
        if (threadPoolProperty.getThreadFactory() == null) {
            threadPoolProperty.setThreadFactory(threadPoolExecutor.getThreadFactory());
        }
        if (threadPoolProperty.getHandler() == null) {
            threadPoolProperty.setHandler(threadPoolExecutor.getRejectedExecutionHandler());
        }
        return new ThreadPoolExecutor(
                threadPoolProperty.getCorePoolSize(),
                threadPoolProperty.getMaxPoolSize(),
                threadPoolProperty.getKeepAliveTime(),
                threadPoolProperty.getUnit(),
                threadPoolProperty.getWorkQueue(),
                threadPoolProperty.getThreadFactory(),
                threadPoolProperty.getHandler()
        );
    }


    @ConditionalOnMissingBean
    @Bean
    public XTThreadPool xtThreadPool() {
        return new XTThreadPool(newThreadPoolExecutor());
    }
}

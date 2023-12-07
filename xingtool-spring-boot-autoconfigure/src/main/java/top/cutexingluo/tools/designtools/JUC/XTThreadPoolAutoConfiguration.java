package top.cutexingluo.tools.designtools.JUC;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 配置文件-线程池
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 16:04
 */
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "xt-thread-pool", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(ThreadPoolProperty.class)
public class XTThreadPoolAutoConfiguration {
}

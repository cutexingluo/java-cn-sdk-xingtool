package top.cutexingluo.tools.utils.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/4 17:40
 */
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "springcache", havingValue = "true", matchIfMissing = false)
@Configuration(proxyBeanMethods = false)
@EnableCaching
public class SpringCacheAutoConfiguration {

}

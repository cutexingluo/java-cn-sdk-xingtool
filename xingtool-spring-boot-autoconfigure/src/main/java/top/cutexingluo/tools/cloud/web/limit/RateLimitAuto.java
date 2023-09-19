package top.cutexingluo.tools.cloud.web.limit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;
import top.cutexingluo.tools.utils.ee.redis.RedisConfig;
import top.cutexingluo.tools.utils.ruoyi.annotation.aop.RateLimiterAspect;

import javax.annotation.PostConstruct;

/**
 * 接口限流Aop
 * ruoyi redis版本
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/6 19:46
 */
@Slf4j
@AutoConfigureAfter({RedisConfig.class, RedisTemplate.class})
@ConditionalOnProperty(prefix = "xingtools.cloud.enabled", name = "redis-limit", havingValue = "true", matchIfMissing = false)
@Configuration(proxyBeanMethods = false)
public class RateLimitAuto {
    @PostConstruct
    public void init() {
        if (LogInfoAuto.enabled)
            log.info("Redis RateLimitAop AOP   --->  {}", "自动装配完成");
    }

    @ConditionalOnMissingBean
    @Bean
    public RateLimiterAspect rateLimiterAspect(RedisTemplate<Object, Object> redisTemplate, RedisScript<Long> limitScript) {
        return new RateLimiterAspect(redisTemplate, limitScript);
    }
}

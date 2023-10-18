package top.cutexingluo.tools.aop.xtlock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cutexingluo.tools.designtools.juc.lockAop.XTAopLockAop;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;

/**
 * XTAopLock 注解
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 13:54
 */
//@ConditionalOnBean(XingToolsAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "xt-aop-lock", havingValue = "true", matchIfMissing = true)
//@EnableAspectJAutoProxy
@ConditionalOnClass({RedissonClient.class, Config.class})
@Slf4j
public class XTLockAopAutoConfiguration {
    @ConditionalOnBean(RedissonClient.class)
    @ConditionalOnMissingBean
    @Bean
    public XTAopLockAop xtAopLockAop() {
        if (LogInfoAuto.enabled) log.info("LogInfoAuto ---->  {}", "XT锁注解AOP，自动注入成功");
        return new XTAopLockAop();
    }
}

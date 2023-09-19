package top.cutexingluo.tools.aop.log.systemlog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cutexingluo.tools.aop.systemlog.XTSystemLogAop;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;

/**
 * XTSystemLog日志自动配置
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/27 10:30
 */
@ConditionalOnBean(XingToolsAutoConfiguration.class)
@ConditionalOnProperty(prefix = "xingtools.enabled", value = "xt-systemlog-anno", havingValue = "true",
        matchIfMissing = false)
@Slf4j
@Configuration(proxyBeanMethods = false)
public class XTSystemLogAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public XTSystemLogAop xtSystemLogAop() {
        if (LogInfoAuto.enabled) log.info("XTSystemLog Aop ---->  {}", "接口调用日志 AOP，自动注入成功");
        return new XTSystemLogAop();
    }
}

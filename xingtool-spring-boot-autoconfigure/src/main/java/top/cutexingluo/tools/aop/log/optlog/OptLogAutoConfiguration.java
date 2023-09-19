package top.cutexingluo.tools.aop.log.optlog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;


/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/1 22:59
 */
@ConditionalOnBean(XingToolsAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "xingtools.enabled", value = "optlog-anno", havingValue = "true",
        matchIfMissing = true)
@Slf4j
public class OptLogAutoConfiguration {
    @ConditionalOnMissingBean
    @Bean
    public OptLogAop optLogAop() {
        if (LogInfoAuto.enabled) log.info("OptLogAop ---->  {}", " 自定义操作 AOP，自动注入成功");
        return new OptLogAop();
    }
}

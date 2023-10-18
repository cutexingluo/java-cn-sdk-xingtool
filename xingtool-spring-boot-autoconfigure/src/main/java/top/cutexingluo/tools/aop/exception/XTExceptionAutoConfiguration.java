package top.cutexingluo.tools.aop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;

/**
 * XTException 注解  AOP
 * <p> {@link XTException} 默认开启</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 12:41
 */
@ConditionalOnBean(XingToolsAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "xingtools.xtexception-anno", value = "enabled", havingValue = "true",
        matchIfMissing = true)
//@EnableAspectJAutoProxy
@Slf4j
public class XTExceptionAutoConfiguration {
    @ConditionalOnMissingBean(XTExceptionAop.class)
    @Bean
    public XTExceptionAop xtExceptionAop() {
        if (LogInfoAuto.enabled) log.info("XTExceptionAop ---->  {}", "异常拦截AOP，自动注入成功");
        return new XTExceptionAop();
    }
}

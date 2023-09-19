package top.cutexingluo.tools.aop.log.printlog;

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
@ConditionalOnProperty(prefix = "xingtools.printlog-anno", value = "enabled", havingValue = "true",
        matchIfMissing = true)
//@EnableAspectJAutoProxy
@Slf4j
public class PrintLogAutoConfiguration {
    @ConditionalOnMissingBean
    @Bean
    public PrintLogAop printLogAop() {
        if (LogInfoAuto.enabled) log.info("PrintLogAop ---->  {}", "打印输出AOP，自动注入成功");
        return new PrintLogAop();
    }
}

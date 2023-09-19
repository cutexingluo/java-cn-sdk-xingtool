package top.cutexingluo.tools.aop.transaction;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.cutexingluo.tools.aop.transactional.ExtTransactionalAop;
import top.cutexingluo.tools.aop.transactional.TransactionalUtils;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;


/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 12:56
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean({XingToolsAutoConfiguration.class, DataSourceTransactionManager.class})
@ConditionalOnProperty(prefix = "xingtools.ext-transaction-anno", value = "enabled", havingValue = "true",
        matchIfMissing = true)
@Import({TransactionalUtils.class})
//@EnableAspectJAutoProxy
@EnableTransactionManagement
@Slf4j
public class ExtTransactionAutoConfiguration {
    @ConditionalOnMissingBean
    @Bean
    public ExtTransactionalAop extTransactionAop() {
        if (LogInfoAuto.enabled) log.info("ExtTransactionalAop ---->  {}", "Ext事务注解AOP，自动注入成功");
        return new ExtTransactionalAop();
    }

}

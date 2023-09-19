package top.cutexingluo.tools.utils.spring.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/12 20:09
 */

@Configuration
public class XTSecurityBeanConfig {

    @ConditionalOnMissingBean
//    @Bean("xtSign")
    @Bean
    public XTSign xtSign() {
        return new XTSign();
    }
}

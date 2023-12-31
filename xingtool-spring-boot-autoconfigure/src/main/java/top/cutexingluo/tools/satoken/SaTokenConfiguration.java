package top.cutexingluo.tools.satoken;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * satoken - jwt
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/4 22:54
 */
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "satokenjwt", havingValue = "true", matchIfMissing = false)
@ConditionalOnClass({StpUtil.class, StpLogic.class, StpLogicJwtForSimple.class})
@Configuration(proxyBeanMethods = false)
public class SaTokenConfiguration {
    // Sa-Token 整合 jwt (Simple 简单模式)
    @ConditionalOnMissingBean
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }
}


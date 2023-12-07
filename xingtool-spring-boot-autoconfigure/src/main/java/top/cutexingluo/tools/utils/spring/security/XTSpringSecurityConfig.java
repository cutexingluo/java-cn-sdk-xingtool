package top.cutexingluo.tools.utils.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/27 18:29
 */

@ConditionalOnClass({TokenStore.class, JwtAccessTokenConverter.class})
//@AutoConfigureAfter(XTSecurityBeanProcessor.class)
@ConditionalOnBean({XingToolsAutoConfiguration.class, XTSign.class, XTSecurityBeanProcessor.class})
@Configuration
@Slf4j
public class XTSpringSecurityConfig {


//    private XTSign xtSign;

    private String SIGN_KEY = "xingtian";


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public XTSpringSecurityConfig(XTSign xtSign) {
//        log.info("come here-------- ----> {} ", "装配完成");
        if (LogInfoAuto.enabled) log.info("XTSpringSecurityConfig ----> {} ", "装配完成");
        if (xtSign != null) {
            log.info("已发现Sign_Key , xtSign 更新完成");
            this.SIGN_KEY = xtSign.getSignKey();
        }
    }
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @PostConstruct
//    @ConditionalOnBean(XTSign.class)
//    public void init() {
//        if (LogInfoAuto.enabled) log.info("XTSpringSecurityConfig ----> {} ", "装配完成");
//        if (xtSign != null) {
//            log.info("已发现Sign_Key");
//            this.SIGN_KEY = xtSign.getSignKey();
//        }
//    }

    @ConditionalOnMissingBean
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * JWT令牌校验工具
     */
    @ConditionalOnMissingBean
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置JWT签名密钥。可以是简单的MAC密钥，也可以是RSA密钥
        jwtAccessTokenConverter.setSigningKey(SIGN_KEY);
        return jwtAccessTokenConverter;
    }


}

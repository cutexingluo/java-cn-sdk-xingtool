package top.cutexingluo.tools.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.config.interceptor.JwtInterceptor;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;

/**
 * 添加 拦截器
 *
 * @author XingTian
 * @version v1.0.0
 * @since 2022-11-14
 */

@ConditionalOnBean(XingToolsAutoConfiguration.class)
@ConditionalOnClass({WebMvcConfigurer.class, InterceptorRegistry.class, HandlerInterceptor.class, JwtInterceptor.class})
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "interceptor-config", havingValue = "true", matchIfMissing = false)
@Configuration(proxyBeanMethods = true)
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        WebMvcConfigurer.super.addInterceptors(registry);
        if (LogInfoAuto.enabled) log.info("InterceptorConfig ---->  {}", "拦截器配置，自动注入成功");
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")// 拦截所有请求，通过判断token是否合法决定是否需要登录
                .excludePathPatterns("/user/login", "/user/register", "/file/**"
                        , "/swagger-resources/**", "/webjars/**" // Swagger2
                        , "/v2/**", "/swagger-ui.html/**" // Swagger2
                );//, "/**/export", "/**/import"
    }

    @ConditionalOnMissingBean
    @Bean
    public HandlerInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}

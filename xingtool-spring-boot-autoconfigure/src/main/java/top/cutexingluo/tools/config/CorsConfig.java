package top.cutexingluo.tools.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;
import top.cutexingluo.tools.server.ServerProperty;

@ConditionalOnProperty(prefix = "xingtools.enabled", name = "cors-config", havingValue = "true", matchIfMissing = false)
@ConditionalOnClass({ServerProperty.class, CorsFilter.class, UrlBasedCorsConfigurationSource.class})
@Configuration(proxyBeanMethods = false)
@Slf4j
public class CorsConfig {
    // 当前跨域请求最大的有效时长，这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Value("http://${server.ip:localhost}:${server.port:8080}/")
    private String urlPrefix;
    @Value("${server.targetUrl:*}")
    private String interUrlPrefix;

    @ConditionalOnMissingBean
    @Bean
    public CorsFilter corsFilter() {
        if (LogInfoAuto.enabled) log.info("CorsConfig ---->  {}", "跨域配置，自动注入成功");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(urlPrefix); // 1.设置访问源地址
        corsConfiguration.addAllowedOrigin(interUrlPrefix); // 1.设置服务器访问源地址
        corsConfiguration.addAllowedHeader("*"); // 2.设置访问请求头
        corsConfiguration.addAllowedMethod("*"); // 3.设置访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 4.设置接口跨域设置
        return new CorsFilter(source);
    }
}

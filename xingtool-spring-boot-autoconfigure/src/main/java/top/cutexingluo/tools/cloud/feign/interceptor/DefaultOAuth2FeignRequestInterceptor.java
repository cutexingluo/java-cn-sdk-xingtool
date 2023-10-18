package top.cutexingluo.tools.cloud.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Feign的拦截器
 *
 * @author nagisa , XingTian
 */

@ConditionalOnProperty(prefix = "xingtools.cloud.enabled", name = "retain-feign-request", havingValue = "true", matchIfMissing = false)
@ConditionalOnClass(RequestInterceptor.class)
@ConditionalOnMissingBean(RequestInterceptor.class)
@Component
@Slf4j
public class DefaultOAuth2FeignRequestInterceptor implements RequestInterceptor {

    //请求头中的token
    @Value("${xingtools.cloud.enabled.retain-feign-request-headers}")
    private static List<String> headers;

    @PostConstruct
    public void init() {
        if (LogInfoAuto.enabled)
            log.info("XTCloud  DefaultOAuth2FeignRequestInterceptor --->  {}", "自动装配完成");
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {

        //1.获取请求对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        headers.forEach(header -> {
            //2.从请求头获取到令牌
            String authorization = request.getHeader(header);
            //3.把令牌添加到Fiegn的请求头
            requestTemplate.header(header, authorization);
        });

    }

}
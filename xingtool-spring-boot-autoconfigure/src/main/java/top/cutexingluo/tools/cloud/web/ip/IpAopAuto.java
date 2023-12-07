package top.cutexingluo.tools.cloud.web.ip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;
import top.cutexingluo.tools.utils.ee.web.ip.ipregion.IpAspect;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/6 17:39
 */
@ConditionalOnClass(HttpServletRequest.class)
@Slf4j
@ConditionalOnProperty(prefix = "xingtools.cloud.enabled", name = "ip-search", havingValue = "true")
@Configuration
public class IpAopAuto {

    @PostConstruct
    public void init() {
        if (LogInfoAuto.enabled)
            log.info("IpAopAuto AOP   --->  {}", "自动装配完成");
    }

    @Bean
    @ConditionalOnMissingBean
    public IpAspect ipAspect() {
        return new IpAspect();
    }
}

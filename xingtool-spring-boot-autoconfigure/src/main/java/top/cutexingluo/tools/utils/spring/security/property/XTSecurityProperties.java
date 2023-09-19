package top.cutexingluo.tools.utils.spring.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/15 17:11
 */
@Data
@ConfigurationProperties(prefix = "xingtools.security.enabled")
public class XTSecurityProperties {

    /**
     * 是否用 XT 提供的 Result 类封装 /oauth/token 接口
     * <p>
     * 封装来源 ==>
     * {@link top.cutexingluo.tools.utils.spring.security.controller.TokenController}
     * </p>
     * <p>
     * 需要开启 @EnableXTCloudSecurity 注解 和 该配置
     * </p>
     */
    private boolean overrideOauthToken = false;
}

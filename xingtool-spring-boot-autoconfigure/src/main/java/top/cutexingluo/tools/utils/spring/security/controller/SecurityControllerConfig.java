package top.cutexingluo.tools.utils.spring.security.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.security.Principal;

/**
 * Controller 判定器，
 * 需要注入的Controller Bean的依赖
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/15 17:06
 */
@ConditionalOnClass({Principal.class, OAuth2AccessToken.class})
public class SecurityControllerConfig {
}

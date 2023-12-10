package top.cutexingluo.tools.utils.spring.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.cutexingluo.tools.common.Result;
import top.cutexingluo.tools.common.base.IResult;
import top.cutexingluo.tools.common.utils.GlobalResultFactory;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;

import java.security.Principal;
import java.util.Map;

/**
 * 用 XT 的 Result 封装 /oauth/token 接口
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/15 16:55
 */
@ConditionalOnProperty(value = "xingtools.security.enabled.override-oauth-token", havingValue = "true", matchIfMissing = false)
@ConditionalOnBean({TokenEndpoint.class, SecurityControllerConfig.class})
@ConditionalOnClass({Principal.class, OAuth2AccessToken.class})
@Slf4j
@RestController
public class TokenController implements InitializingBean {

    //令牌请求的端点
    @Autowired
    TokenEndpoint tokenEndpoint;

    @Autowired(required = false)
    GlobalResultFactory globalResultFactory;

    /**
     * 重写/oauth/token 这个默认接口，返回的数据格式统一
     */
    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    public <C, T> IResult<C, T> postAccessToken(Principal principal, @RequestParam
            Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Result error = Result.success(accessToken);
        IResult<Object, Object> result = GlobalResultFactory.selectResult(globalResultFactory, error);
        return (IResult<C, T>) result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (LogInfoAuto.enabled) log.info("XT-TokenController 已覆盖 token 接口");
    }
}

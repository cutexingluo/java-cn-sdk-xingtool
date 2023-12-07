package top.cutexingluo.tools.utils.spring.security.old;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.cutexingluo.tools.cloud.oauth.OAuth2AccessTokenResult;

import java.security.Principal;
import java.util.Map;

/**
 * 重写返回数据
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/18 13:47
 */

//@FrameworkEndpoint
public class XTOldTokenEndpoint extends TokenEndpoint {

//    private final TokenGranter tokenGranter;

    public XTOldTokenEndpoint(AuthorizationServerEndpointsConfiguration endpointsConfiguration) {
        AuthorizationServerEndpointsConfigurer configurer = endpointsConfiguration.getEndpointsConfigurer();
        this.setTokenGranter(configurer.getTokenGranter());
        this.setClientDetailsService(configurer.getClientDetailsService());
    }

//    @Autowired
//    private UserConvertor userConvertor;

    public XTOldTokenEndpoint(TokenGranter tokenGranter, ClientDetailsService clientDetailsService) {
        this.setTokenGranter(tokenGranter);
        this.setClientDetailsService(clientDetailsService);
    }

    @Override
    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam
            Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        // 获取父返回值
        ResponseEntity<OAuth2AccessToken> responseEntity = super.postAccessToken(principal, parameters);
        OAuth2AccessToken accessToken = responseEntity.getBody();

        // 将loginUser显式存入返回数据，不加密
//        LoginUser loginUser = (LoginUser) principal;
//        UserInfoVo infoVo = null;
//        if (loginUser != null) {
//            infoVo = userConvertor.po2infoVo(loginUser.getUser()); //转化
//        }
        // 构建自定义的OAuth2AccessTokenResponse，并从RequestBody替换到response中
        OAuth2AccessTokenResult result = new OAuth2AccessTokenResult();
//        result.setData(new LoginResponse(accessToken, infoVo));

        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(result);
    }


}

package top.cutexingluo.tools.utils.spring.security.old;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import top.cutexingluo.tools.utils.spring.security.XTSignUtil;

import java.util.Date;
import java.util.Map;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/17 21:36
 */
//@Configuration
public class XTOldTokenService implements ResourceServerTokenServices {

    @Autowired
    private DefaultUserAuthenticationConverter myUserAuthenticationConverter;

    @Override
    public OAuth2Authentication loadAuthentication(String token) throws AuthenticationException, InvalidTokenException {

        try {
            // 验证 jwt token
            if (StrUtil.isBlank(token)) {
                throw new InvalidTokenException("token不能为空");
            }
            // 验证jwt是否正确 判断 token 格式是否正确
            if (!JWTUtil.verify(token, XTSignUtil.getSign().getBytes())) {
                throw new InvalidTokenException("Invalid token, 无效的token, token格式错误");
            }
        } catch (JWTException e) {
            throw new AuthenticationServiceException("Invalid token, token格式不正确");
        } catch (InvalidTokenException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }

        // 解析token
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(token);
//        Jwt jwt = JwtHelper.decode(token);
//        String claims = jwt.getClaims();

        // 自定义验证逻辑，例如验证token是否过期等
        Date expiration = accessToken.getExpiration();
        if (expiration != null && new Date().after(expiration)) {
            throw new AuthenticationServiceException("Token has expired, token已过期");
        }
        // 自定义提取载荷数据并保存到SecurityContext逻辑
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();

        // 解析数据
        Authentication userAuthentication = myUserAuthenticationConverter.extractAuthentication(additionalInformation);
        //        String userId = (String) additionalInformation.get("userId");
//        List<GrantedAuthority> authorities = null;
//        // 从token中提取用户权限信息并构造出GrantedAuthority列表
//        if (additionalInformation.get("authorities") != null)
//            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(additionalInformation.get("authorities").toString());
//        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
        // 构造并返回OAuth2Authentication对象
        return new OAuth2Authentication(createOAuth2Request(null, accessToken), userAuthentication);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        return null;
    }

    private OAuth2Request createOAuth2Request(Map<String, String> requestParameters, OAuth2AccessToken token) {
        // 根据token构造一个OAuth2Request对象
        return new OAuth2Request(requestParameters, null, null, true, null, null, null, null, null);
    }
}

package top.cutexingluo.tools.utils.spring.security.old;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import top.cutexingluo.tools.utils.spring.security.XTSignUtil;

/**
 * 身份验证
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/17 20:16
 */
//@Configuration
public class XTOldAuthenticationManager implements AuthenticationManager {

//    @Autowired
//    private TokenStore tokenStore;

    @Autowired
    private DefaultUserAuthenticationConverter myUserAuthenticationConverter;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        System.out.println("当前用户token: " + token);


        try {
            // 验证 jwt token
            if (token == null) {
                throw new InvalidTokenException("Invalid token, token为空");
            }
            // 验证jwt是否正确
            if (!JWTUtil.verify(token, XTSignUtil.getSign().getBytes())) {
                throw new InvalidTokenException("Invalid token, 无效的token, token格式错误");
            }
        } catch (JWTException e) {
            throw new AuthenticationServiceException("Invalid token, token格式不正确");
        } catch (InvalidTokenException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }


        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        System.out.println("payloads :  " + payloads);

//                    Claims claims = Jwts.parser()
//                            .setSigningKey("your-signing-key")  // 设置签名密钥，需与生成Token时的密钥保持一致
//                            .parseClaimsJws(token)
//                            .getBody();

        Authentication userAuthentication = myUserAuthenticationConverter.extractAuthentication(payloads);
        if (userAuthentication == null) {
            throw new AuthenticationServiceException("Invalid token, 无效的token");
        } else {
            authentication.setAuthenticated(true);
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                // 清理个人信息
                ((UsernamePasswordAuthenticationToken) authentication).eraseCredentials();
            }
            return authentication;
        }
    }
}

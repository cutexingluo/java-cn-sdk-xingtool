package top.cutexingluo.tools.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.common.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * XT 认证异常拦截默认封装为 Result
 * <br>
 * xtauthentication入口点
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/17 18:58
 */
public class XTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * @param httpServletRequest  http servlet请求
     * @param httpServletResponse http servlet响应
     * @param e                   异常
     * @throws IOException      ioexception
     * @throws ServletException servlet异常
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 处理认证异常并封装为合适的响应格式返回给前端
        unauthorized(httpServletResponse, e.getMessage());
    }

    public static void unauthorized(HttpServletResponse httpServletResponse, String msg) throws IOException {
        Result error = Result.error(Constants.CODE_401, msg);
        XTResponseUtil.unauthorized(httpServletResponse, error);
    }
}

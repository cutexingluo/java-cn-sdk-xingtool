package top.cutexingluo.tools.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.common.Result;
import top.cutexingluo.tools.common.base.IResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * XT 认证异常拦截默认封装为 Result
 * <br>
 * <p>SpringSecurity 认证端点 </p>
 *
 * @author XingTian
 * @version 1.0.1
 * @date 2023/6/17 18:58
 * @updateFrom 1.0.3
 */
public class XTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * returnResultSource 包装类
     * <p>自定义返回值</p>
     *
     * @since 1.0.3
     */
    IResult<Object, Object> returnResult;

    public XTAuthenticationEntryPoint(IResult<Object, Object> returnResultSource) {
        this.returnResult = returnResultSource;
    }

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

    /**
     * <p>update the static off</p>
     *
     * @since 1.0.3
     */
    public <C, T> void unauthorized(HttpServletResponse httpServletResponse, String msg) throws IOException {
        IResult<C, T> error = null;
        if (returnResult == null) {
            error = (IResult<C, T>) Result.error(Constants.CODE_401, msg);
        } else {
            error = (IResult<C, T>) returnResult;
        }
        XTResponseUtil.unauthorized(httpServletResponse, error);
    }

    public static void unauthorized(HttpServletResponse httpServletResponse, IResult<Object, Object> result) throws IOException {
        XTResponseUtil.unauthorized(httpServletResponse, result);
    }

}

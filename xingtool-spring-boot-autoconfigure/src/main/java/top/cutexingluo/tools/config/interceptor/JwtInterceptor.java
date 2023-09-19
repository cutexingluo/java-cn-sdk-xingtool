package top.cutexingluo.tools.config.interceptor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 拦截器
 * XingTian
 *
 * @Since 2022-11-14
 * 版本v1.0.0
 */

@ConditionalOnClass(HandlerInterceptor.class)
public class JwtInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return toDoInHandlerMethod();
        }
        return toDoNotInHandlerMethod();
    }

    public boolean toDoInHandlerMethod() {
        return true;
    }

    public boolean toDoNotInHandlerMethod() {
        return true;
    }
}

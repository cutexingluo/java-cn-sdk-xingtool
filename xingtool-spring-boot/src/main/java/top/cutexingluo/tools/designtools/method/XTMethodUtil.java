package top.cutexingluo.tools.designtools.method;

import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 方法工具类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/21 18:48
 */
public class XTMethodUtil {

    /**
     * 获取 handler 上是否存在该注解
     * <p> 如果 目标对象不是HandlerMethod 或 不存在注解 则返回false</p>
     * <br>
     * 常用于拦截器
     *
     * @param handler         处理器 (instanceof HandlerMethod)
     * @param annotationClass 注释类
     * @return boolean
     */
    public static <T> boolean isAnnotationPresent(T handler, Class<? extends Annotation> annotationClass) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            return method.isAnnotationPresent(annotationClass);
        }
        return false;
    }
}

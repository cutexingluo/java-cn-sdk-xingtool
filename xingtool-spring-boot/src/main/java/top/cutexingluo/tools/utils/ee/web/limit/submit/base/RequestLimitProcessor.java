package top.cutexingluo.tools.utils.ee.web.limit.submit.base;

import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import top.cutexingluo.tools.designtools.method.ClassMaker;
import top.cutexingluo.tools.designtools.method.XTMethodUtil;
import top.cutexingluo.tools.utils.ee.web.limit.submit.strategy.LimitStrategy;
import top.cutexingluo.tools.utils.ruoyi.utils.ip.IpUtils;
import top.cutexingluo.tools.utils.spring.SpringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 策略 key 处理器
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2024/1/6 20:45
 */
public class RequestLimitProcessor {

    /**
     * 获取注解的策略实例
     */
    public static LimitStrategy getLimitStrategy(@NotNull RequestLimit limit) {
        LimitStrategy limitStrategy = null;
        if (limit.needSpring()) {
            limitStrategy = SpringUtils.getBeanNoExc(limit.strategy());
        }
        if (limitStrategy == null) {
            ClassMaker<? extends LimitStrategy> maker = new ClassMaker<>(limit.strategy());
            limitStrategy = maker.newInstanceNoExc();
        }
        return limitStrategy;
    }

    /**
     * 获取 IP
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        return IpUtils.getIpAddr(request);
    }

    /**
     * 获取 HttpMethod
     */
    public static String getHttpMethod(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        return request.getMethod();
    }

    /**
     * 获取 Request HTTP_URI
     */
    public static String getHttpUri(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        return request.getRequestURI();
    }

    /**
     * 获取 Method 全限定名
     */
    public static Method getMethod(@NotNull ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }

    /**
     * 获取 Method 参数 RequestLimitKey 的组装结果
     */
    public static StringBuilder getRequestLimitKey(@NotNull Method method, @NotNull StringBuilder sb, @NotNull String delimiter) {
        Parameter[] parameters = XTMethodUtil.getParameters(method);
        XTMethodUtil.methodParamsHandler(parameters, RequestLimitKey.class, (params, index, paramRequestLimitKey) -> {
            if (paramRequestLimitKey != null) {
                if (paramRequestLimitKey.add()) {
                    if (StrUtil.isNotBlank(paramRequestLimitKey.key())) {
                        sb.append(delimiter).append(paramRequestLimitKey.key());
                    } else {
                        sb.append(delimiter).append(params[index].getName());
                    }
                }
                if (paramRequestLimitKey.inner()) { // 一层内加值
                    XTMethodUtil.objFieldHandler(params[index], RequestLimitKey.class, ((fields, ind, fieldRequestLimitKey) -> {
                        if (fieldRequestLimitKey != null) {
                            if (fieldRequestLimitKey.add()) {
                                if (StrUtil.isNotBlank(fieldRequestLimitKey.key())) {
                                    sb.append(delimiter).append(fieldRequestLimitKey.key());
                                } else {
                                    sb.append(delimiter).append(fields[ind].getName());
                                }
                            }
                        }
                    }));
                }
            }
        });
        return sb;
    }
}

package top.cutexingluo.tools.cloud.web.limit;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.common.Result;
import top.cutexingluo.tools.common.base.IResult;
import top.cutexingluo.tools.common.utils.GlobalResultFactory;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;
import top.cutexingluo.tools.utils.ee.web.front.WebUtils;
import top.cutexingluo.tools.utils.ee.web.limit.guava.Limit;
import top.cutexingluo.tools.utils.ee.web.limit.submit.base.RequestLimit;
import top.cutexingluo.tools.utils.ee.web.limit.submit.pkg.RequestLimitHandler;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 接口限流AOP <br>
 * 需要导入 guava 包
 * <p>
 * <code>
 * &lt;groupId&gt;com.google.guava &lt;/groupId&gt; <br>
 * &lt;artifactId&gt;guava&lt;/artifactId&gt;
 * </code>
 * </p>
 * <p>1.0.4 及以后版本推荐使用 {@link RequestLimit} 注解 或 {@link RequestLimitHandler} 编程式工具</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/6 16:31
 */

@ConditionalOnBean(XingToolsAutoConfiguration.class)
@ConditionalOnProperty(prefix = "xingtools.cloud.enabled", name = "current-limit", havingValue = "true")
@ConditionalOnClass({RateLimiter.class})
@Slf4j
@Aspect
@Component
public class LimitAop {

    @Autowired(required = false)
    private GlobalResultFactory globalResultFactory;

    @PostConstruct
    public void init() {
        if (LogInfoAuto.enabled)
            log.info("LimitAop AOP   --->  {}", "自动装配完成");
    }

    /**
     * 不同的接口，不同的流量控制
     * map的key为 Limiter.key
     */
    private final Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();

    @Around("@annotation(top.cutexingluo.tools.utils.ee.web.limit.guava.Limit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //拿limit的注解
        Limit limit = method.getAnnotation(Limit.class);
        if (limit != null) {
            //key作用：不同的接口，不同的流量控制
            String key = limit.key();
            RateLimiter rateLimiter = null;
            //验证缓存是否有命中key
            if (!limitMap.containsKey(key)) {
                // 创建令牌桶
                rateLimiter = RateLimiter.create(limit.permitsPerSecond());
                limitMap.put(key, rateLimiter);
                log.info("新建了令牌桶={}，容量={}", key, limit.permitsPerSecond());
            }
            rateLimiter = limitMap.get(key);
            // 拿令牌
            boolean acquire = rateLimiter.tryAcquire(limit.timeout(), limit.timeunit());
            // 拿不到命令，直接返回异常提示
            if (!acquire) {
                log.debug("令牌桶={}，获取令牌失败", key);
                this.responseFail(limit.msg());
                return null;
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 直接向前端抛出异常
     *
     * @param msg 提示信息
     */
    private <C, T> void responseFail(String msg) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        IResult<C, T> err = globalResultFactory == null ?
                (IResult<C, T>) Result.error(Constants.CODE_403, msg) :
                globalResultFactory.newResult(Constants.CODE_403.intCode(), msg, null);
        WebUtils.renderString(response, JSONUtil.toJsonStr(err));
    }
}

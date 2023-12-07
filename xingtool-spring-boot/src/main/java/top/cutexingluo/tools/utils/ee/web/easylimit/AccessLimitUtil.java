package top.cutexingluo.tools.utils.ee.web.easylimit;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import top.cutexingluo.tools.common.Result;
import top.cutexingluo.tools.utils.ee.redis.RYRedisCache;
import top.cutexingluo.tools.utils.ee.redis.RYRedisUtil;
import top.cutexingluo.tools.utils.ee.web.front.WebUtils;
import top.cutexingluo.tools.utils.ee.web.ip.util.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/17 20:30
 */
@Slf4j
public class AccessLimitUtil {

    public static RedisTemplate<String, Object> redisTemplate;
    public static RYRedisCache redisCache;
    protected static ApplicationContext applicationContext;
    protected static boolean showLog = true;
    protected static boolean firstTime = true;


    public static void setShowLog(boolean showLog) {
        AccessLimitUtil.showLog = showLog;
    }

    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        AccessLimitUtil.redisTemplate = redisTemplate;
    }

    public static void setRedisCache(RYRedisCache redisCache) {
        AccessLimitUtil.redisCache = redisCache;
        firstTime = false;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        AccessLimitUtil.applicationContext = applicationContext;
    }

    public static boolean checkRedis() throws Exception {
        boolean b = RYRedisUtil.checkRedisSource(redisCache, redisTemplate, applicationContext);
        firstTime = false;
        if (!b) {
            throw new RuntimeException("" +
                    "条件一： RYRedisCache未设置 或者" +
                    "RedisTemplate未设置。" +
                    "条件二： 如果两个均未设置，则需设置ApplicationContext，" +
                    "如果ApplicationContext已设置，" +
                    "但找不到相应的bean！");
        }
        return b;
    }

    /**
     * 限制过滤器，需要@AccessLimit 注解
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     * @throws Exception 异常
     */
    public static boolean limitFilter(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (firstTime && !checkRedis()) { //第一次对配置进行检查
            return false;
        }
        boolean result = true;
        // Handler 是否为 HandlerMethod 实例
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            //方法上没有访问控制的注解，直接通过
            if (accessLimit != null) {
                result = limitAll(request, response,
                        accessLimit.seconds(), accessLimit.maxCount(), accessLimit.msg());
            }
        }
        return result;
    }

    /**
     * 限制所有, 默认redis key 为 ip + ":" + method + ":" + requestUri
     *
     * @param request     请求
     * @param response    响应
     * @param interval    时间间隔
     * @param maxCount    最大计数
     * @param outLimitMsg 超出限制消息
     * @return boolean
     * @throws Exception 异常
     */
    public static boolean limitAll(HttpServletRequest request, HttpServletResponse response,
                                   int interval, int maxCount, String outLimitMsg) throws Exception {
        String ip = IpUtils.getIpAddress(request);
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        String redisKey = ip + ":" + method + ":" + requestUri;
        return limit(response, redisKey, interval, maxCount, outLimitMsg);
    }

    /**
     * 限制IP, 默认 redis key = "limitIp:"+ip
     *
     * @param request     请求
     * @param response    响应
     * @param interval    时间间隔
     * @param maxCount    最大计数
     * @param outLimitMsg 出限制味精
     * @return boolean
     * @throws Exception 异常
     */
    public static boolean limitIP(HttpServletRequest request, HttpServletResponse response,
                                  int interval, int maxCount, String outLimitMsg) throws Exception {
        String ip = IpUtils.getIpAddress(request);
        String redisKey = "limitIp:" + ip;
        return limit(response, redisKey, interval, maxCount, outLimitMsg);
    }

    /**
     * 限流基础方法
     *
     * @param response    响应
     * @param redisKey    复述,关键
     * @param interval    时间间隔
     * @param maxCount    最大计数
     * @param outLimitMsg 超出限制消息
     * @return boolean
     * @throws Exception 异常
     */
    public static boolean limit(HttpServletResponse response,
                                String redisKey,
                                int interval, int maxCount, String outLimitMsg) throws Exception {
        return limit(response, redisKey, interval, maxCount, Result.error(outLimitMsg));
    }

    /**
     * 限制
     * 限流基础方法
     *
     * @param response       响应
     * @param redisKey       复述,关键
     * @param interval       时间间隔
     * @param maxCount       最大计数
     * @param responseResult 响应结果
     * @return boolean
     * @throws Exception 异常
     */
    public static <T> boolean limit(HttpServletResponse response,
                                    String redisKey,
                                    int interval, int maxCount, T responseResult) throws Exception {
        if (firstTime && !checkRedis()) { //第一次对配置进行检查
            return false;
        }
        boolean result = true;
        try {
            Long count = redisCache.incrementCacheValue(redisKey, 1L);
            // 第一次访问
            if (Objects.nonNull(count) && count == 1) {
                redisCache.expire(redisKey, interval, TimeUnit.SECONDS);
            } else if (count > maxCount) {
                WebUtils.renderString(response, JSON.toJSONString(responseResult));
                if (showLog) log.warn(redisKey + "请求次数超过每" + interval + "秒" + maxCount + "次");
                result = false;
            }
        } catch (RedisConnectionFailureException e) {
            throw e;
        }
        return result;
    }
}

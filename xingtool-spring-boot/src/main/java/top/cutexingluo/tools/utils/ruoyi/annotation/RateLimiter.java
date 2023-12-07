package top.cutexingluo.tools.utils.ruoyi.annotation;


import top.cutexingluo.tools.utils.ruoyi.constraint.CacheConstants;
import top.cutexingluo.tools.utils.ruoyi.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流注解
 * <p>1.如果在拦截器使用工具类方法，则使用AccessLimit</p>
 * <p>若选择这种方式(编程式)，则调用AccessLimitUtil方法</p>
 * <p>2.如果想自动拦截Aop, 则使用Limit注解 或者 ruoyi 的RateLimiter注解</p>
 * <p>若开启该aop则需要开启EnableXingToolsCloudServer，并开启相应配置</p>
 *
 * @author ruoyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     */
    public String key() default CacheConstants.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    public int time() default 60;

    /**
     * 限流次数
     */
    public int count() default 100;

    /**
     * 限流类型
     */
    public LimitType limitType() default LimitType.DEFAULT;
}

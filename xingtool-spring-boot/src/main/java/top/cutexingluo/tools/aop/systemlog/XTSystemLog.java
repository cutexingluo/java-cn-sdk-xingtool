package top.cutexingluo.tools.aop.systemlog;

import org.springframework.core.annotation.AliasFor;
import top.cutexingluo.tools.aop.log.methodlog.MethodLog;
import top.cutexingluo.tools.aop.log.xtlog.base.WebLog;

import java.lang.annotation.*;

/**
 * 接口调用日志
 * <p>网络接口层面使用 @XTSystemLog -> {@link XTSystemLog} </p>
 * <p>方法调用层面使用 @MethodLog ->  {@link MethodLog}</p>
 * <p><b>1.0.4 版本后推荐使用综合型注解 {@link WebLog}</b></p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/27 9:06
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XTSystemLog {

    /**
     * 业务名称
     *
     * @return {@link String}
     */
    @AliasFor("value")
    String businessName() default "";

    @AliasFor("businessName")
    String value() default "";

    /**
     * 是否展示请求参数
     *
     * @return boolean
     */
    boolean showRequestArgs() default true;

    /**
     * 是否展示返回参数
     *
     * @return boolean
     */
    boolean showResponseArgs() default true;
}

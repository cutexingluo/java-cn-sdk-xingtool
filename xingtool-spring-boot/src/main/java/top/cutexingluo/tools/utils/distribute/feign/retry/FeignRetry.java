package top.cutexingluo.tools.utils.distribute.feign.retry;

import java.lang.annotation.*;

/**
 * feign 重试注解
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/6 15:45
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FeignRetry {

    Backoff backoff() default @Backoff();

    int maxAttempt() default 3;

    Class<? extends Throwable>[] include() default {};
}

package top.cutexingluo.tools.aop.transactional.async;

import java.lang.annotation.*;

/**
 * 子线程
 * <p>子线程最后一个参数必须为当前线程</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/9/18 15:30
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SonTransaction {
    String value() default "";
}
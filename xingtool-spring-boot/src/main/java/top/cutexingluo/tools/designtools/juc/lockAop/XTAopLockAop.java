package top.cutexingluo.tools.designtools.juc.lockAop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 普通注解锁切面
 * <p>
 *
 * @author XingTian
 * @version 1.0.1
 * @update 2023-4-6
 * @date 2022-11-21
 */

@ConditionalOnClass({RedissonClient.class, Config.class})
@ConditionalOnBean(RedissonClient.class)
@Aspect
//@Component
public class XTAopLockAop {

    @Autowired
    RedissonClient redissonClient;

    @Around("@annotation(xtAopLock)")
    public Object around(ProceedingJoinPoint joinPoint, XTAopLock xtAopLock) {
        XTAopLock lockAnno = AnnotationUtils.getAnnotation(xtAopLock, XTAopLock.class);
        if (lockAnno == null) throw new RuntimeException("XTAopLock 注解发生错误");
        Object result = null;
        result = new XTAopLockHandler(lockAnno, redissonClient).lock(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        });
        return result;
    }
}

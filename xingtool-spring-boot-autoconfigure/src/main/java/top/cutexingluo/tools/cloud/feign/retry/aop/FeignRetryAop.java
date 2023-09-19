package top.cutexingluo.tools.cloud.feign.retry.aop;

import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import top.cutexingluo.tools.utils.distribute.feign.retry.FeignRetry;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 需要导入spring-retry 包
 * <br>
 * 使用方法：<br> @FeignRetry(maxAttempt = 6, backoff = @Backoff(delay = 500L, maxDelay = 20000L, multiplier = 4))
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/6 15:49
 */

@ConditionalOnClass({RetryableException.class, RetryTemplate.class})
@Aspect
@Slf4j
@Component
public class FeignRetryAop {

    @PostConstruct
    public void init() {
        log.info("FeignRetryAop init ---> {}", "自动装配完成");
    }

    @Around("@annotation(top.cutexingluo.tools.utils.distribute.feign.retry.FeignRetry)")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getCurrentMethod(joinPoint);
        FeignRetry feignRetry = method.getAnnotation(FeignRetry.class);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(prepareBackOffPolicy(feignRetry));
        retryTemplate.setRetryPolicy(prepareSimpleRetryPolicy(feignRetry));

        return retryTemplate.execute(arg0 -> {
                    int retryCount = arg0.getRetryCount();
                    log.info("Sending request method: {}, max attempt: {}, delay: {}, retryCount: {}",
                            method.getName(),
                            feignRetry.maxAttempt(),
                            feignRetry.backoff().delay(),
                            retryCount
                    );
                    return joinPoint.proceed(joinPoint.getArgs());
                },
                context -> {
                    //重试失败后执行的代码
                    log.info("重试了{}次后，最终还是失败了======", context.getRetryCount());
                    return "failed callback";
                }
        );


    }

    private BackOffPolicy prepareBackOffPolicy(FeignRetry feignRetry) {
        if (feignRetry.backoff().multiplier() != 0) {
            ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
            backOffPolicy.setInitialInterval(feignRetry.backoff().delay());
            backOffPolicy.setMaxInterval(feignRetry.backoff().maxDelay());
            backOffPolicy.setMultiplier(feignRetry.backoff().multiplier());
            return backOffPolicy;
        } else {
            FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
            fixedBackOffPolicy.setBackOffPeriod(feignRetry.backoff().delay());
            return fixedBackOffPolicy;
        }
    }


    private SimpleRetryPolicy prepareSimpleRetryPolicy(FeignRetry feignRetry) {
        Map<Class<? extends Throwable>, Boolean> policyMap = new HashMap<>();
        policyMap.put(RetryableException.class, true);  // Connection refused or time out
//        try {
//            if (Class.forName("com.netflix.client.ClientException") != null) {
//                policyMap.put(ClientException.class, true);      // need nacos
//// Load balance does not available (cause of RunTimeException)
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        if (feignRetry.include().length != 0) {
            for (Class<? extends Throwable> t : feignRetry.include()) {
                policyMap.put(t, true);
            }
        }
        return new SimpleRetryPolicy(feignRetry.maxAttempt(), policyMap, true);
    }

    private Method getCurrentMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}

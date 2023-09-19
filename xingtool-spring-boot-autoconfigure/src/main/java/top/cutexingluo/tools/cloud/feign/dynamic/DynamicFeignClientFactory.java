package top.cutexingluo.tools.cloud.feign.dynamic;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import top.cutexingluo.tools.auto.cloud.XTSpringCloudAutoConfiguration;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/6 15:23
 */
@ConditionalOnBean(XTSpringCloudAutoConfiguration.class)
@ConditionalOnProperty(prefix = "xingtool.cloud.enabled", name = "dynamic-feign", havingValue = "true", matchIfMissing = false)
@ConditionalOnClass(FeignClientBuilder.class)
@Component
@Import(DynamicClient.class)
public class DynamicFeignClientFactory<T> {

    private FeignClientBuilder feignClientBuilder;

    public DynamicFeignClientFactory(ApplicationContext appContext) {
        this.feignClientBuilder = new FeignClientBuilder(appContext);
    }

    public T getFeignClient(final Class<T> type, String serviceId) {
        return this.feignClientBuilder.forType(type, serviceId).build();
    }
}

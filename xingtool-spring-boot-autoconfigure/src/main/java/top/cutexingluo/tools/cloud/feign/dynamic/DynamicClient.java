package top.cutexingluo.tools.cloud.feign.dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import top.cutexingluo.tools.auto.cloud.XTSpringCloudAutoConfiguration;

/**
 * 动态 Feign 调用 <br>
 * 需要开启配置，否则自行配置Bean: DynamicFeignClientFactory 和 DynamicClient
 * <br>
 * 使用方法：
 * <code>dynamicClient.executePostApi("user", "/system/test", new HashMap());</code>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/6 15:26
 */
@ConditionalOnBean(XTSpringCloudAutoConfiguration.class)
@ConditionalOnProperty(prefix = "xingtool.cloud.enabled", name = "dynamic-feign", havingValue = "true", matchIfMissing = false)
@AutoConfigureAfter(DynamicFeignClientFactory.class)
@Component
public class DynamicClient {
    @Autowired
    private DynamicFeignClientFactory<DynamicService> dynamicFeignClientFactory;

    /**
     * 远程 feign  post 调用
     *
     * @param feignName feign 名称
     * @param url       接口
     * @param params    参数
     * @return 返回值
     */
    public Object executePostApi(String feignName, String url, Object params) {
        DynamicService dynamicService = dynamicFeignClientFactory.getFeignClient(DynamicService.class, feignName);
        return dynamicService.executePostApi(url, params);
    }

    /**
     * 远程 feign  get 调用
     *
     * @param feignName feign 名称
     * @param url       接口
     * @param params    参数
     * @return 返回值
     */
    public Object executeGetApi(String feignName, String url, Object params) {
        DynamicService dynamicService = dynamicFeignClientFactory.getFeignClient(DynamicService.class, feignName);
        return dynamicService.executeGetApi(url, params);
    }
}

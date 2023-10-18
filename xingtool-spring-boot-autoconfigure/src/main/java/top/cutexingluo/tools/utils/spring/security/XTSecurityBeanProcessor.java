package top.cutexingluo.tools.utils.spring.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * signKey处理器
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/12 20:59
 */
@AutoConfigureAfter(XTSecurityBeanConfig.class)
@Configuration
public class XTSecurityBeanProcessor implements BeanFactoryPostProcessor {

    public static String signKey = "xingtian";

    private XTSign xtSign;

    public static void setSignKey(String value) {
        signKey = value;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // 获取需要设置属性的bean
        XTSign xtSign = (XTSign) configurableListableBeanFactory.getBean("xtSign");
        // 设置属性
        xtSign.setSignKey(signKey);
        this.xtSign = xtSign;
    }

//    @ConditionalOnClass({TokenStore.class, JwtAccessTokenConverter.class, XTSpringSecurityConfig.class})
//    @Bean
//    public XTSpringSecurityConfig xtSpringSecurityConfig() {
//        return new XTSpringSecurityConfig(xtSign);
//    }
}

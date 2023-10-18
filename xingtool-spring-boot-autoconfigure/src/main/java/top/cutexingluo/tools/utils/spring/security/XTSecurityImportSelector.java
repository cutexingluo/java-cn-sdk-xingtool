package top.cutexingluo.tools.utils.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;
import top.cutexingluo.tools.utils.spring.EnableXTCloudSecurity;
import top.cutexingluo.tools.utils.spring.security.controller.SecurityControllerConfig;
import top.cutexingluo.tools.utils.spring.security.property.XTSecurityProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/12 19:47
 */
@Slf4j
@EnableConfigurationProperties(XTSecurityProperties.class)
public class XTSecurityImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        log.info("SecurityConfig importing .....");
//        log.info("" + importingClassMetadata.getClassName());
//        log.info("" + importingClassMetadata.hasAnnotation(EnableXTCloudSecurity.class.getName()));
        Class<EnableXTCloudSecurity> annoType = EnableXTCloudSecurity.class;
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(annoType.getName(), false);
        if (annotationAttributes == null) log.error("EnableXTCloudSecurity annotationAttributes is null");
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationAttributes);
        if (attributes == null) log.error("attributes is null");
        Assert.notNull(attributes, () -> {
            return String.format("@%s is not present on importing class '%s' as expected", annoType.getSimpleName(), importingClassMetadata.getClassName());
        });
        // 获取属性成功后
        List<String> classNames = new ArrayList<>(2);
        String sign_key = attributes.getString("sign_key");
        if (sign_key == null) log.error("sign_key is null");
        classNames.add(XTSecurityBeanConfig.class.getName());

        if (!"xingtian".equals(sign_key)) {
            // 将注解值设置为属性
            XTSecurityBeanProcessor.setSignKey(sign_key);
        }
        classNames.add(XTSecurityBeanProcessor.class.getName()); //添加signKey处理器

        classNames.add(SecurityControllerConfig.class.getName()); // 是否覆盖接口

//        // 返回要导入的类数组
//        return new String[]{SomeClass.class.getName()};

        if (LogInfoAuto.enabled) {
            log.info("XTSecurityConfig 已开启！");
        }
        return classNames.toArray(new String[0]);
    }

//    , BeanDefinitionRegistryPostProcessor
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
//        // 手动添加需要注入注解值的 bean 到注册表中
//        beanDefinitionRegistry.registerBeanDefinition("xtSpringSecurityConfig", BeanDefinitionBuilder.rootBeanDefinition(XTSpringSecurityConfig.class).getBeanDefinition());
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//    }
}

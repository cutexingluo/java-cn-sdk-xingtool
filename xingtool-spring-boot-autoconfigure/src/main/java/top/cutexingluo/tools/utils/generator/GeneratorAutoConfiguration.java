package top.cutexingluo.tools.utils.generator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cutexingluo.tools.utils.generator.java.OldVmCodeGenerator;

/**
 * 代码生成器
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/5 14:23
 */
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "vm-generator", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(OldVmGeneratorProperty.class)
@ConditionalOnBean(OldVmCodeGenerator.class)
@Configuration(proxyBeanMethods = false)
public class GeneratorAutoConfiguration {

    @Autowired
    private OldVmGeneratorProperty oldVmGeneratorProperty;

    @ConditionalOnMissingBean
    @Bean
    public OldVmCodeGenerator oldVmCodeGenerator() {
        OldVmCodeGenerator oldVmCodeGenerator = new OldVmCodeGenerator();
        oldVmCodeGenerator.setAuthor(oldVmGeneratorProperty.getAuthor());
        oldVmCodeGenerator.setBasePath(oldVmGeneratorProperty.getBasePath());
        oldVmCodeGenerator.setTableName(oldVmGeneratorProperty.getTableName());
        oldVmCodeGenerator.setPackageName(oldVmGeneratorProperty.getPackageName());
        oldVmCodeGenerator.setDbProp(oldVmGeneratorProperty.getDbProp());
        return oldVmCodeGenerator;
    }
}

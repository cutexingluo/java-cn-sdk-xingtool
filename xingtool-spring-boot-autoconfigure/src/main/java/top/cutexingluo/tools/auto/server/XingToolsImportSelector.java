package top.cutexingluo.tools.auto.server;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.context.annotation.Primary;
import org.springframework.core.type.AnnotationMetadata;
import top.cutexingluo.tools.aop.xtlock.XTLockAopAutoConfiguration;
import top.cutexingluo.tools.config.CorsConfig;
import top.cutexingluo.tools.config.MybatisPlusConfig;
import top.cutexingluo.tools.config.SwaggerConfig;
import top.cutexingluo.tools.designtools.JUC.XTThreadPoolAutoConfiguration;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;
import top.cutexingluo.tools.satoken.SaTokenConfiguration;
import top.cutexingluo.tools.utils.generator.GeneratorAutoConfiguration;
import top.cutexingluo.tools.utils.spring.SpringCacheAutoConfiguration;
import top.cutexingluo.tools.utils.spring.SpringUtilsAutoConfiguration;

import java.util.function.Predicate;


/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 20:07
 */
@Slf4j
@Primary
//@Configuration
@AutoConfigureAfter({LogInfoAuto.class, XingToolsAutoConfiguration.class})
public class XingToolsImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(@NotNull AnnotationMetadata annotationMetadata) {
        log.info("XingToolsServer 启动成功 !  请享受你的日常的乐趣 !");
        return new String[]{
                XTLockAopAutoConfiguration.class.getName(),
//                GlobalExceptionHandler.class.getName(),   // 不会在这里配置
                CorsConfig.class.getName(),
//                InterceptorConfig.class.getName(),
                MybatisPlusConfig.class.getName(),
                SwaggerConfig.class.getName(),
                XTThreadPoolAutoConfiguration.class.getName(),
                SaTokenConfiguration.class.getName(),
//                RedisConnectionConfig.class.getName(),  // 不会在这里配置
//                RedissonConfig.class.getName(), // 不会在这里配置
                GeneratorAutoConfiguration.class.getName(),
                SpringUtilsAutoConfiguration.class.getName(),
                SpringCacheAutoConfiguration.class.getName()
        };
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return ImportSelector.super.getExclusionFilter();
    }
}

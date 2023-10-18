package top.cutexingluo.tools.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.cutexingluo.tools.designtools.log.LogInfoAuto;


/**
 * Swagger2 配置
 */
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "swagger-config", havingValue = "true", matchIfMissing = false)
@ConditionalOnClass({Docket.class, DocumentationType.class, ApiInfo.class})
@Configuration
@EnableSwagger2
//@EnableSwagger2WebMvc
@EnableOpenApi
@Slf4j
public class SwaggerConfig {

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public Docket restApi() {
        if (LogInfoAuto.enabled) log.info("SwaggerConfig ---->  {}", "Swagger配置，自动注入成功");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("标准接口")
                .apiInfo(apiInfo("Spring Boot中使用Swagger2构建RESTful APIs", "1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.xing.springboot.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://ip:port/swagger-ui.html
     * 访问地址：http://ip:port/swagger-ui/index.html
     *
     * @return
     */
    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
//                .description("更多请关注: https://blog.csdn.net/123456")
//                .termsOfServiceUrl("https://blog.csdn.net/123456")
//                .contact(new Contact("xingtian", "https://blog.csdn.net/123456", "123456@qq.com"))
                .version(version)
                .build();
    }
}


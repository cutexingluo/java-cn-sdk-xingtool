package top.cutexingluo.tools.auto.cloud;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/3 22:50
 */
@AutoConfigureAfter(XingToolsAutoConfiguration.class)
@Import({XTCloudImportSelector.class})
@Configuration
public class XTSpringCloudAutoConfiguration {
    public static final String INFO = "@author XingTian\n" +
            "  @version 1.0.0\n" +
            " @since 2023/5/3";
}

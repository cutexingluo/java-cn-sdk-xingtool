package top.cutexingluo.tools.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 17:08
 */
@Data
@ConfigurationProperties(prefix = "server")
@Configuration
public class ServerProperty {
    private String ip = "localhost";
    private String targetUrl = "/**";
}

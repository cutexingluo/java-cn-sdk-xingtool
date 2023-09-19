package top.cutexingluo.tools.utils.generator;


import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.cutexingluo.tools.common.database.DBProp;
import top.cutexingluo.tools.utils.se.file.XTPath;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/5 14:20
 */
@Data
@ConditionalOnProperty(prefix = "xingtools.generator", havingValue = "true", matchIfMissing = true)
@ConfigurationProperties(prefix = "xingtools.generator")
public class OldVmGeneratorProperty {

    private String author ="";
    //    // 数据库名字
//    private static String dataBaseName = "xing";
    // 项目绝对路径地址  spring
    @NotNull
    private String basePath = XTPath.getProjectPath(this.getClass());
    // 表名
    @NotNull
    private String tableName;
    // 生成的父包名
    @NotNull
    private String packageName = "com.example";
    // 数据库信息
    @NotNull
    private DBProp dbProp;
}

package top.cutexingluo.tools.common.database;

import lombok.Builder;
import lombok.Data;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/4 23:00
 *
 * 表结构
 */
@Data
@Builder
public class TableColumn {
    private String columnName;
    private String dataType;
    private String columnComment;
}
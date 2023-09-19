package top.cutexingluo.tools.common.data;

/**
 * 数据类型
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/1 23:53
 */
//public interface XTDataType {
//    String QUARTER = "QUARTER";  //一刻钟，四季之一
//
//    String DICT_TYPE_ICON = "icon"; // 图片类型
//}

public enum XTDataType {
    QUARTER("QUARTER", "四季之一"),
    DICT_TYPE_ICON("icon", "图片类型");

    private String name;
    private String content;

    XTDataType(String name) {
        this.name = name;
    }

    XTDataType(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

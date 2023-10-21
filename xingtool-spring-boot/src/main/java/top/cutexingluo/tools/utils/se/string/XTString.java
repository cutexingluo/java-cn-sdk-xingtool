package top.cutexingluo.tools.utils.se.string;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 * <p>继承 CPPString , 兼容 c++ string 方法名称</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/10/20 20:15
 * @since 1.0.2
 */
public class XTString extends CPPString implements BaseString {

    public XTString() {
        super();
    }

    /**
     * 默认不检测 null 值
     *
     * @param string 字符串
     */
    public XTString(String string) {
        super(string);
    }

    /**
     * @param string       字符串
     * @param autoFillNull 是否自动将空字符串 null 赋值为 ""
     */
    public XTString(String string, boolean autoFillNull) {
        super(string, autoFillNull);
    }

    public XTString(BaseString baseString) {
        super(baseString);
    }

    @Override
    public String toString() {
        return this.string;
    }


    @Override
    public boolean isEmpty() {
        return StrUtil.isEmpty(this.string);
    }

    @Override
    public boolean isBlank() {
        return StrUtil.isBlank(this.string);
    }

    @Override
    public boolean isNotEmpty() {
        return StrUtil.isNotEmpty(this.string);
    }

    @Override
    public boolean isNotBlank() {
        return StrUtil.isNotBlank(this.string);
    }

}

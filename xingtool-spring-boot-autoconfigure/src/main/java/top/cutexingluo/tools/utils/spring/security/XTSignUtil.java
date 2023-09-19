package top.cutexingluo.tools.utils.spring.security;

/**
 * XTSign 工具类，获取 sign 值
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/17 18:06
 */
public class XTSignUtil {


    /**
     * 得到签
     *
     * @return {@link String}
     */
    public static String getSign() {
        return XTSecurityBeanProcessor.signKey;
    }
}

package top.cutexingluo.tools.kotlin.base

/**
 *
 * kotlin 适配基础工具类
 *
 * @author XingTian
 * @date 2024/3/27 13:22
 * @version 1.0.0
 * @since 1.0.4
 */
object KtBaseLib {

    /**
     *去除 null (?)
     * 设置默认值
     */
    @JvmStatic
    fun <T> defaultIfNull(defaultValue: T, act: () -> T?): T {
        return act() ?: defaultValue
    }


}
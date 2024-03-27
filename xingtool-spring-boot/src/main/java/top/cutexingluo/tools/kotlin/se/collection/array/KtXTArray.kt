package top.cutexingluo.tools.kotlin.se.collection.array

/**
 *
 * kotlin Array 基础类
 * @author XingTian
 * @date 2024/3/27 13:24
 * @version 1.0.0
 * @since 1.0.4
 */
open class KtXTArray<T> constructor(_size: Int) {
    var size = _size
    var arr: Array<T>? = null
    var isR = false
    inline fun <reified P> initAll() {
        arr = arrayOfNulls<P>(size) as Array<T>
        isR = true
    }

    operator fun get(index: Int): T? = arr?.get(index).takeIf { isR }
    operator fun set(i: Int, value: T) {
        arr?.set(i, value)
    }

    operator fun plusAssign(a: T) {
        arr?.plusElement(a)
    }
}
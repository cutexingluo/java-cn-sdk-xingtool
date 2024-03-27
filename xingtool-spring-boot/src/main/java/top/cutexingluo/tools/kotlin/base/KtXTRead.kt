package top.cutexingluo.tools.kotlin.base

import top.cutexingluo.tools.kotlin.se.collection.array.KtXTArray
import java.util.*

/**
 *OI 读取类
 *
 * @author XingTian
 * @date 2024/3/27 13:28
 * @version 1.0.0
 * @since 1.0.4
 */
open class KtXTRead(scanner: Scanner) {
//    companion object {
//        @JvmStatic
//        val instance: KtXTRead by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { KtXTRead() }
//    }

    val read = scanner // Scanner(System.`in`)
    inline fun <reified T> Read(): T? {
        if (!read.hasNext()) {
            return null
        }
        return Check<T>()
    }

    inline fun <reified T> Check(): T? {
        return when (T::class) {
            Int::class -> read.nextInt() as T
            Double::class -> read.nextDouble() as T
            String::class -> read.next() as T
            else -> null
        }
    }

    inline fun <reified T> ReadArr(arr: KtXTArray<T>, num: Int = arr.size) {
        for (i in 1..num) {
            if (read.hasNext()) {
                var tmp = Check<T>()
                if (tmp != null) {
                    arr[i] = tmp
                }
            }
        }
    }

    inline fun <reified T> ReadArr(arr: Array<T>, num: Int = arr.size) {
        for (i in 1..num) {
            if (read.hasNext()) {
                var tmp = Check<T>()
                if (tmp != null) {
                    arr[i] = tmp
                }
            }
        }
    }
}
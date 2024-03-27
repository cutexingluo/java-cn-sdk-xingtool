package top.cutexingluo.tools.kotlin.se.collection.stack


/**
 * 函数参数模拟栈
 * <p>防止 kotlin 爆栈</p>
 *
 * @param N 栈大小
 * @author XingTian
 * @date 2024/3/27 13:38
 * @version 1.0.0
 * @since 1.0.4
 */
open class KtFunStack(N: Int) {

    val h = Array(N) { 0 }
    val f = Array(N) { Array(2) { 0 } }
    val G = Array(N) { arrayListOf<Int>() }
    val fa = Array(N) { -1 }

    fun funStack(x: Int) {
        val s = arrayListOf<Pair<Int, Int>>()
        s.add(x to 0)
        while (s.isNotEmpty()) {
            val r = s.size - 1
            val now = s[r]
            var se = now.second
            val u = now.first
            when (se) {
                0 -> {
                    se++
                    f[u][0] = 0;f[u][1] = h[u]
                    for (i in G[u].size - 1 downTo 0) {
                        s.add(G[u][i] to 0)//push
                    }
                    s[r] = u to se//update
                }
                else -> {
                    val p = fa[u]
                    if (p != -1) {
                        f[p][0] += f[u][0].coerceAtLeast(f[u][1])
                        f[p][1] += f[u][0]
                    }
                    s.removeAt(r)//pop
                }
            }
        }
    }
}
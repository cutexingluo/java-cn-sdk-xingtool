package top.cutexingluo.tools.basepackage.base;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 17:18
 */
@FunctionalInterface
public interface BaseAroundRunnable {
    void run(Runnable now, Runnable before, Runnable after) ;
}
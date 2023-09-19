package top.cutexingluo.tools.basepackage.base;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 17:59
 */

@FunctionalInterface
public interface BinRunnable {
    Runnable getRunnable(Runnable before,Runnable after);
}

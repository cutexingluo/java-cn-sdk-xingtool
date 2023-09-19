package top.cutexingluo.tools.basepackage.baseimpl;

import java.util.concurrent.Callable;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 18:04
 */
public class XTRunCallUtil {
    public static Runnable getRunnable(Runnable now, Runnable before, Runnable after) {
        return new XTRunnable(now,before,after);
    }
    public static Runnable getRunnable(Runnable now,Runnable after) {
        return new XTRunnable(now,null,after);
    }
    public static Runnable getTryRunnable(Runnable now, Runnable before,Runnable after) {
        return () -> {
            if (before != null) before.run();
            try {
                if (now != null) now.run();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (after != null) after.run();
            }
        };
    }
    public static Runnable getTryRunnable(Runnable now, Runnable after) {
        return getTryRunnable(now, null, after);
    }

    //最标准 Callable 套用模板
    public static <V> XTCallable<V> getTryCallable(Callable<V> now, Runnable before, Runnable after) {
        return new XTCallable<V>(now, before, after);
    }

    public static <V> XTCallable<V> getTryCallable(Callable<V> now, Runnable after) {
        return getTryCallable(now, null, after);
    }

}

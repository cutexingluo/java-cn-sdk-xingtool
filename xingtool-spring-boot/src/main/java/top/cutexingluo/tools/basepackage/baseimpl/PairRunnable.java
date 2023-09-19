package top.cutexingluo.tools.basepackage.baseimpl;

import top.cutexingluo.tools.basepackage.base.BasePairRunnable;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 17:47
 */
public class PairRunnable  implements BasePairRunnable,Runnable {
    private Runnable now;
    private Runnable after;

    @Override
    public void run(Runnable before, Runnable after) {
        if(before!=null)before.run();
        if(after!=null)after.run();
    }

    @Override
    public void run() {
        this.run(this.now, this.after);
    }
}

package top.cutexingluo.tools.basepackage.baseimpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cutexingluo.tools.basepackage.base.ComRunnable;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 17:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XTRunnable implements Runnable , ComRunnable {

    private Runnable now,before,after;

    public XTRunnable(Runnable now){
        this.now = now;
    }

    @Override
    public void run() {
        getRunnable().run();
    }


    public Runnable getRunnable() {
        return ()->{
            if(before!=null)before.run();
            if(now!=null)now.run();
            if(after!=null)after.run();
        };
    }
    public Runnable getRunnable(Runnable now) {
        this.now=now;
        return getRunnable();
    }

    @Override
    public Runnable getRunnable(Runnable now, Runnable before, Runnable after) {
        this.before= before;
        this.after= after;
        this.now= now;
        return getRunnable();
    }

}

package top.cutexingluo.tools.basepackage.baseimpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cutexingluo.tools.basepackage.base.ComCallable;

import java.util.concurrent.Callable;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 23:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XTCallable <T> implements Callable<T> , ComCallable<T> {
    Callable<T> now;
    Runnable before,after;
    public XTCallable(Callable<T> now){
        this.now = now;
    }
    public Callable<T> getCallable() {
        return ()->{
            T res = null;
            if(before!=null)before.run();
            try {
                if(now!=null) res=now.call();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(after!=null)after.run();
            }
            return res;
        };
    }

    @Override
    public Callable<T> getCallable(Callable<T> now, Runnable before, Runnable after) {
        this.now = now;this.before = before;this.after = after;
        return getCallable();
    }

    @Override
    public T call() throws Exception {
        return getCallable().call();
    }
}

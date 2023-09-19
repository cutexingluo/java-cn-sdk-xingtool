package top.cutexingluo.tools.designtools.juc.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * @author XingTian
 */
public class XTSyncQueue<T> extends SynchronousQueue<T> {
    public void putOne(T item) throws InterruptedException {
        super.put(item);
    }

    public T takeOne() throws InterruptedException {
        return super.take();
    }

    public T getOne() throws InterruptedException {
        return super.take();
    }
}

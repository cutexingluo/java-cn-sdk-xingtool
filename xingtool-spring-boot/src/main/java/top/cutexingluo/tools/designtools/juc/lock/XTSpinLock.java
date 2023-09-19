package top.cutexingluo.tools.designtools.juc.lock;

import java.util.concurrent.atomic.AtomicReference;

public class XTSpinLock {
    private AtomicReference<Thread> spinLock;

    XTSpinLock() {
        spinLock = new AtomicReference<>();
    }

    public void lock() {
        Thread thread = Thread.currentThread();
        while (!spinLock.compareAndSet(null, thread)) ;
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        spinLock.compareAndSet(thread, null);
    }

    public Runnable getLockRunnable(Runnable runnable) {
        return () -> {
            lock();
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        };
    }
}

package top.cutexingluo.tools.designtools.juc.lockAop;

import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.exception.ServiceException;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * handler，可直接调用
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/6 21:13
 */
public class XTAopLockHandler {
    private final XTAopLock lockAnno;
    private final RedissonClient redissonClient;

    public XTAopLockHandler(XTAopLock lockAnno, RedissonClient redissonClient) {
        this.lockAnno = lockAnno;
        this.redissonClient = redissonClient;
    }

    public <T> T lock(Callable<T> task) {
        Lock lock;
        RReadWriteLock readWriteLock;
        switch (lockAnno.type()) {
            case RLock:
                if (redissonClient == null)
                    throw new ServiceException(Constants.CODE_500.getCode(), "No  RedissonClient");
                lock = redissonClient.getLock(lockAnno.value());
                break;
            case RReadLock:
                if (redissonClient == null)
                    throw new ServiceException(Constants.CODE_500.getCode(), "No  RedissonClient");
                readWriteLock = redissonClient.getReadWriteLock(lockAnno.value());
                lock = readWriteLock.readLock();
                break;
            case RWriteLock:
                if (redissonClient == null)
                    throw new ServiceException(Constants.CODE_500.getCode(), "No  RedissonClient");
                readWriteLock = redissonClient.getReadWriteLock(lockAnno.value());
                lock = readWriteLock.writeLock();
                break;
            default:
                lock = new ReentrantLock(lockAnno.isFair());
        }
        T result = null;
        if (lockAnno.tryTimeout() != -1) {
            try {
                boolean b = lock.tryLock(lockAnno.tryTimeout(), TimeUnit.SECONDS);
                if (!b) return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else lock.lock();
        try {
            result = task.call();
//            result=joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }
}

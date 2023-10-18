package top.cutexingluo.tools.designtools.juc.lockAop;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.redisson.api.RedissonClient;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.exception.ServiceException;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * handler，可直接调用
 * <p>建议直接使用 {@link  top.cutexingluo.tools.designtools.juc.lock.handler.XTLockHandler}</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/6 21:13
 * @update 1.0.2
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class XTAopLockHandler {
    private XTAopLock lockAnno;
    private RedissonClient redissonClient;

    public XTAopLockHandler(XTAopLock lockAnno, RedissonClient redissonClient) {
        this.lockAnno = lockAnno;
        this.redissonClient = redissonClient;
    }

    public XTAopLockHandler(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public <T> T lock(XTLockMeta lockMeta, Callable<T> task) throws Exception {
        if (lockMeta.lockType == XTLockType.NonLock) {
            return task.call();
        }
        Lock lock;
        ReadWriteLock readWriteLock;
        String lockName = lockMeta.getName();
        switch (lockMeta.lockType) {
            case RLock:
                if (redissonClient == null)
                    throw new ServiceException(Constants.CODE_500.getCode(), "No  RedissonClient");
                if (StrUtil.isBlank(lockName)) throw new IllegalArgumentException("XTLockMeta don't have name string");
                lock = redissonClient.getLock(lockName);
                break;
            case RReadLock:
                if (redissonClient == null)
                    throw new ServiceException(Constants.CODE_500.getCode(), "No  RedissonClient");
                if (StrUtil.isBlank(lockName)) throw new IllegalArgumentException("XTLockMeta don't have name string");
                readWriteLock = redissonClient.getReadWriteLock(lockName);
                lock = readWriteLock.readLock();
                break;
            case RWriteLock:
                if (redissonClient == null)
                    throw new ServiceException(Constants.CODE_500.getCode(), "No  RedissonClient");
                if (StrUtil.isBlank(lockName)) throw new IllegalArgumentException("XTLockMeta don't have name string");
                readWriteLock = redissonClient.getReadWriteLock(lockName);
                lock = readWriteLock.writeLock();
                break;
            case ReentrantReadLock:
                readWriteLock = new ReentrantReadWriteLock(lockMeta.isFair());
                lock = readWriteLock.readLock();
            case ReentrantWriteLock:
                readWriteLock = new ReentrantReadWriteLock(lockMeta.isFair());
                lock = readWriteLock.writeLock();
            default: //ReentrantLock
                lock = new ReentrantLock(lockMeta.isFair());
        }
        T result = null;
        if (lockMeta.getTryTimeout() != -1) {
            try {
                boolean b = lock.tryLock(lockMeta.getTryTimeout(), TimeUnit.SECONDS);
                if (!b) return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else lock.lock();
        try {
            if (task != null) result = task.call();
//            result=joinPoint.proceed();
        } finally {
            lock.unlock();
        }
        return result;
    }

    public <T> T lock(Callable<T> task) throws Exception {
        XTLockMeta meta = new XTLockMeta(lockAnno.name(), lockAnno.isFair(), lockAnno.lockType(), lockAnno.tryTimeout());
        return lock(meta, task);
    }

    public <T> Callable<T> getCallable(XTLockMeta lockMeta, Callable<T> task) {
        return () -> lock(lockMeta, task);
    }

}

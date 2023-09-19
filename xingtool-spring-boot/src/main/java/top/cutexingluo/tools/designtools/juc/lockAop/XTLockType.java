package top.cutexingluo.tools.designtools.juc.lockAop;

public enum XTLockType {
    ReentrantLock, // 本地可重入锁
    RLock, //分布式普通锁
    RReadLock, //分布式读锁
    RWriteLock, //分布式写锁


//    ReentrantReadLock, //不支持注解
//    ReentrantWriteLock,
//    StaticLock,
}

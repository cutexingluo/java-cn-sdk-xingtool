package top.cutexingluo.tools.designtools.juc.thread;

public enum RejectPolicy {
    Abort, //不处理抛出异常
    CallerRuns, //哪里来的去哪里，主线程执行
    Discard, //拒绝
    DiscardOldest, //拒绝队列第一个（最老的）
}

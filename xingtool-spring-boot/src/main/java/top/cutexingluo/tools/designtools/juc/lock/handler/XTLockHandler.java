package top.cutexingluo.tools.designtools.juc.lock.handler;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import top.cutexingluo.tools.basepackage.basehandler.CallableHandler;
import top.cutexingluo.tools.designtools.juc.lockAop.XTAopLockHandler;
import top.cutexingluo.tools.designtools.juc.lockAop.XTLockMeta;
import top.cutexingluo.tools.designtools.juc.lockAop.XTLockType;
import top.cutexingluo.tools.designtools.juc.lockAop.XTLockTypeUtil;
import top.cutexingluo.tools.utils.spring.SpringUtils;

import java.util.concurrent.Callable;

/**
 * 锁处理万能类
 * <p>可以通过new生成实例，然后调用 decorate 包装为新的任务</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/10/1 16:56
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Accessors(chain = true)
public class XTLockHandler implements CallableHandler {

    private XTLockMeta lockMeta;
    /**
     * 是否RedissonClient
     */
    private boolean openRedissonClient = false;

    //    @Autowired(required = false)
    private RedissonClient redissonClient;


    /**
     * 使用 RedissonClient
     * <p>这个构造只能用于 @Bean 的方式</p>
     */
    @Autowired(required = false)
    public XTLockHandler(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void init() {
        if (openRedissonClient && redissonClient == null) {
            redissonClient = SpringUtils.getBean(RedissonClient.class);
            if (redissonClient == null) {
                throw new IllegalStateException("RedissonClient is null !!!");
            }
        }
    }

    /**
     * 使用 RedissonClient
     */
    public XTLockHandler(XTLockMeta lockMeta, boolean openRedissonClient, RedissonClient redissonClient) {
        this.lockMeta = lockMeta;
        this.openRedissonClient = openRedissonClient;
        this.redissonClient = redissonClient;
    }

    /**
     * openRedissonClient = true 自动获取bean
     */
    public XTLockHandler(XTLockMeta lockMeta, boolean openRedissonClient) {
        this.openRedissonClient = openRedissonClient;
        this.lockMeta = lockMeta;
    }

    /**
     * 默认本地锁
     */
    public XTLockHandler(XTLockMeta lockMeta) {
        this(lockMeta, false);
    }


    /**
     * @param handlerMeta 使用XTLockHandlerMeta包装的数据
     */
    public XTLockHandler(XTLockHandlerMeta handlerMeta) {
        this(handlerMeta.getLockMeta(), handlerMeta.isOpenRedissonClient(), handlerMeta.getRedissonClient());
    }


    @Override
    public <T> Callable<T> decorate(Callable<T> task) {
        if (!openRedissonClient) {
            XTLockType lockType = XTLockTypeUtil.toReentrant(lockMeta.getLockType());
            lockMeta.setLockType(lockType);
        } else {
            init();
        }
        XTAopLockHandler lockHandler = new XTAopLockHandler(redissonClient);
        return lockHandler.getCallable(lockMeta, task);
    }
}

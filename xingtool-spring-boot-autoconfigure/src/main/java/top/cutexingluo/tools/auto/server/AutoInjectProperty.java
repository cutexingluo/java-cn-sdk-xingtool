package top.cutexingluo.tools.auto.server;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import top.cutexingluo.tools.utils.ee.redis.RedisSerializerEnum;

/**
 * xingtools.enabled 配置文件开启
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 18:52
 */
@Data
@Primary
@ConfigurationProperties(prefix = "xingtools.enabled")
@Slf4j
public class AutoInjectProperty {

    /**
     * 启动日志是否开启
     */
    private boolean logInfo = true;

    /**
     * spring getBean spring工具类是否开启，默认开启
     */
    private boolean springutils = true;


    /**
     * 代码生成器配置类,默认开启
     */
    private boolean vmGenerator = true;

    /**
     * <p>
     * aop Lock锁注解是否开启 依赖于redissonConfig 所以上面redisconfig需要开启<br><br>
     * </p>
     *
     *
     * 默认开启
     */
    private boolean xtAopLock = true;

    // 全局异常拦截是否开启
    /**
     * 全局异常拦截是否开启，推荐开启<br>
     * 默认关闭
     */
    private boolean globalException = false;


    /**
     * 自定义操作注解是否开启 <br>
     * Add @MethodLog annotation.
     */
    private boolean optlogAnno = false;

    /**
     * 方法调用日志注解是否开启 <br>
     * Add @MethodLog annotation.
     */
    private boolean methodlogAnno = false;

    /**
     * 接口调用日志注解是否开启 <br>
     * Add @XTSystemLog annotation.
     */
    private boolean xtSystemlogAnno = false;

    /**
     * <p> 异步线程（含事务，锁）综合注解是否开启</p>
     * Add @MainThread  and  @SonThread
     */
    private boolean asyncThreadAopAnno = false;

    /**
     * redis默认配置 并注入RedisTemplate，默认关闭
     * 开启后需要导入 redis 相关依赖包，例如spring-boot-starter-data-redis
     */
    private boolean redisconfig = false;


    /**
     * redisTemplate 序列化方式，默认jackson， 目前支持jackson, fastjson 两种
     */
    private RedisSerializerEnum redisconfigSetting = RedisSerializerEnum.jackson;

    /**
     * redis注入一系列的Redis工具类，默认关闭
     * 例如RYRedisCache,QGRedisUtils,RedisUtil,XTRedisUtil等工具类全部自动注入
     * <br>
     * 开启前需要把 redisconfig 项打开，并导入redis相关依赖
     */
    private boolean redisconfigUtil = false;


    //********以上一般都会遇到

    //sa-token整合jwt，默认关闭
    /**
     * sa-token整合jwt，默认关闭
     */
    private boolean satokenjwt = false;


    // redisson 分布式锁是否开启 默认关闭
    /**
     * 分布式锁是否开启 默认关闭<br>
     * 需要引入 redisson 依赖<br>
     * 打开后会自动装配RedissonClient (会利用redis端口)
     * - 推荐使用 redisson-aop 配置
     */
    private boolean redissonConfig = false;

    /**
     * 分布式锁Aop是否开启 默认关闭<br>
     * 需要引入 redisson 依赖 和 lock4j-redisson-spring-boot-starter依赖 <br>
     * <p>
     * <b>打开后，可使用@Lock4j 注解 和 LockTemplate</b>
     * </p>
     * <ul>
     *     <li>1.使用方式：<br>   @Lock4j(keys = {"#key"}, acquireTimeout = 10, expire = 10000
     *             ,executor = XTRedissonExecutor.class
     *     )</li>
     *     <li>
     *         2.使用方式：<br>    @Autowired <br>
     *     private LockTemplate lockTemplate;
     *     </li>
     * </ul>
     */
    private boolean redissonAop = false;

    /**
     * 全局异常拦截扩展是否开启，支持sa-token扩展<br>
     * 默认关闭
     */
    private boolean globalExceptionExt = false;

    // springcache 配置是否开启
    /**
     * springcache  自动配置类 是否开启，默认关闭
     */
    private boolean springcache = false;

    // XTThreadPool全局单例 线程池
    /**
     * XTThreadPool全局单例 线程池 默认关闭
     */
    private boolean xtThreadPool = false;
    //MybatisPlus 分页插件
    /**
     * MybatisPlus 分页插件自动配置 使用分页插件推荐开启<br> 默认关闭
     */
    private boolean mybatisPlusConfig = false;
    // 跨域拦截
    /**
     * 跨域拦截默认配置是否开启<br>默认关闭
     */
    private boolean corsConfig = false;

    // 拦截器
    /**
     * 默认拦截器配置，默认关闭
     */
    private boolean interceptorConfig = false;
    // swagger
    /**
     * 默认swagger配置，默认关闭
     */
    private boolean swaggerConfig = false;
}

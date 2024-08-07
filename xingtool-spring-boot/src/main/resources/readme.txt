
@Author XingTian
@Version v1.0.5-SNAPSHOT
@Since 2023-9-26
@Update 2024-7-7



更新公告

2024-4-8 ~ 2024-7-7  v1.0.5
更改部分
1.允许 OptData 直接接受 null 值
2. RedisLockUtil 更改包名为 redis

新增部分
1.添加 Method Proxy 系列方法
2.新增 Serializer 序列化接口，及其子类 多种序列化类
3.新增 model 包，模型含有状态机等多种结构
4.扩展 OptBundle 系列方法

支持部分 SpringBoot3
新增使用文档

2023-12-29 ~ 2024-3-27  v1.0.4

bug修复
1.紧急修复XTCallable 的 getSupplier 和 canRunTask 问题，并修复逻辑。
2.紧急修复 XTStrUtil findFirstOf错误调用自身的问题。 1.0.2-1.0.3 两个版本不要使用该方法。
3.修复单 pick 问题, 解决并发注解 @MainThread @SonThread 事务问题, 修复 TreeUtil 树转列表的问题
4.修复 ResultUtil 对 R类 的支持问题

更改部分
1.修改 @MainThread 默认时间策略为  GetResultAfterLastSon, 和原来没有什么差别。
2.修改了 XTMethodUtil 类方法名称 isAnnotationPresent => isHandlerMethodAnnotationPresent。
3.修改所有限流注解/工具的位置。
4.修改 XTLog 实现, 以及新增一系列 web 的 key 接口 和日志接口。
5.修改XTThreadPool、ThreadData默认核心线程，并修改ThreadHelper 命名防止冲突。

新增部分
1.新增 @StrJson 注解 返回数据时返回指定json字符串，并新增 StrJsonStrategy 接口的实现类 SensitiveSerializer 类用于敏感字符串脱敏，可自行实现StrJsonStrategy接口。
2.新增 SocketServer 和 SocketClient 等类 和 XTCollUtil 集合工具类。
3.新增 @RequestLimit 限流注解 和 RequestLimitHandler 工具类，可以取代 @Limit 和 @AccessLimit 注解,  提供了两个策略，可自定义策略，使用灵活。
4.新增 @WebLog注解 和 WebHandler 工具类，用于自定义策略日志打印，可以取代 @MethodLog 和 @XTSystemLog , 匹配, 模式串用法更灵活。
5.新增 OptBundle 类 用于执行链操作 , 和 OptionalResult 用于扩展 Optional 类。
6.新增 kotlin 依赖 临时支持 kotlin 。



2023-10-24 ~ 12-25  v1.0.3

bug修复
1.修复了 AccessLimitUtil.limitIP 加载 Ipdb 错误 的bug
更改部分
1.修改了XTCompletionService实现，以及ThreadLocalHelper文件位置
2.所有web 拦截类 从 Result 改为返回 IResult 接口，并且添加 GlobalResultFactory 接口用于全局返回结果，使用时需要注册到容器。
新增部分
1.新增top.cutexingluo.tools.utils.se.algo.cpp包，里面包含各种算法（有些未测试），例如数论，几何，数据结构，图论，字符串等
2.新增 BoolUtil  用于使 java 适配 c++性质。
3.新增XTArrayUtil一些方法，用于移动数组元素，新增 XTSetUtil 的 Set 工具类。
4.新增ClassMaker类，用于转化和反射，可以配合XTObjUtil。
5.增加了 CommonResult 类，用于返回通用结果, 四大返回类重新继承了该类。
6.新增红黑树 RBTree，迭代器默认中序遍历，即默认升序排序。属性全为protected，方便子类继承。常规推荐使用 TreeMap
7.新增各种迭代器用于适配多种情况。可自行继承使用。
8.新增启用 server 的 banner 和 cloud server 的 banner



2023-10-1 ~ 10-16  v1.0.2
1.添加了Supplier接口
2.添加多线程注解AOP @MainThread @SonThread
3. 调整 XTAsync 类 , 并且添加 ThreadHelper接口，更快速使用。
4. 添加 XTString 工具类，可以通过 C++ 方法名称的方式使用。
5.重构代码（位置和代码），减小 Bean 数量，以下为 v1.0.2 重构更新日志：

1.移除 XTExceptionAop 两个静态方法
2.添加 BaseAspectAroundHandler 接口默认方法
3.规范类的命名，例如ThreadLocal的工具类，规范了各工具类的用法注释
4.更改了 XTCallable 和 XTRunnable 的部分方法，添加 TryCatchHelper 等helper接口，方便直接使用工具类。例如 LockHelper, ThreadHelper 等
5.更改 XTProxy 的实现
6.更改了 XTResponseUtil 的参数, 使之更通用
7.新增 TreeUtil  树转列表
8.更改 LogInfo 类 转为 LogInfoDisable 类
9.为 RedisConfig , SpringSecurity Oauth2 添加用法注释
10.XTCallOtherUtil 更名为 XTCodeInteropUtil，关于生成其他语言代码的工具类
11.添加RabbitMQ系列初始工具类, 以及用法Test类, 可以不使用，没有太多优化的地方。

2023-9-26 v1.0.1
正式版发布，中央仓库 ,  依赖最低版本不能低于 v1.0.1

2023-9-26 v1.0.0
测试仓库版

2023-4-6 beta - v1.2.1
整理重构代码，整理返回类。

2023-4-6 beta - v1.2.0
整理重构代码，依赖处理。

2023-2-3 beta - v1.1.0
整合工具类为springboot包
取消XTUtils类，JUC和锁 默认包过时

2022-11-26 beta - v1.0.3
增加JUC集合,本地方法类

2022-11-14 beta - v1.0.2
增加aop注解，XTFile工具类

2022-10-19 beta - v1.0.1
更新设计工具等工具类

2022-10-18 beta - v1.0.0
默认XTUtils工具和异常配置类

@Author XingTian
@Version v1.0.2-SNAPSHOT
@Since 2022-10-18



更新公告
2023-10-1 ~ 10-16  v1.0.2-SNAPSHOT
1.添加了Supplier接口
2.添加多线程注解AOP @MainThread @SonThread
3.调整XTAsync类
4.重构代码（位置和代码），减小 Bean 数量
5. 以下为 v1.0.2 重构更新日志：
        1.移除 XTExceptionAop 两个静态方法
        2.添加 BaseAspectAroundHandler 接口默认方法
        3.规范类的命名，例如ThreadLocal的工具类，规范了各工具类的用法注释
        4.更改了 XTCallable 和 XTRunnable 的部分方法，添加TryCatchHelper接口
        5.更改 XTProxy 的实现
        6.更改了XTResponseUtil, 使之更通用
        7.新增 TreeUtil  树转列表
        8.更改 LogInfo 类 转为 LogInfoDisable 类
        9.为 RedisConfig , SpringSecurity Oauth2 添加用法注释
        10.XTCallOtherUtil 更名为 XTCodeInteropUtil

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
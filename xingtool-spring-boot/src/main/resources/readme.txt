
@Author XingTian
@Version v1.0.3-SNAPSHOT
@Since 2023-9-26
@Update 2023-10-16



更新公告
2023-10-24 v1.0.3
1.修改了XTCompletionService实现，以及ThreadLocalHelper文件位置
2.新增top.cutexingluo.tools.utils.se.algo.cpp包，里面包含各种算法（有些未测试），例如数论，几何，数据结构，图论，字符串等
3.新增 BoolUtil  用于适配 c++性质。
4.新增XTArrayUtil一些方法，用于移动数组元素。
5.新增ClassMaker类，用于转化和反射，可以配合XTObjUtil。
6.修复了 AccessLimitUtil.limitIP 加载 Ipdb 错误 的bug



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
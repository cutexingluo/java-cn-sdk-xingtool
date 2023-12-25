# XingTool 工具包

### 相关介绍
XingTool sdk 工具包，v1.0.3正式版发布。( 依赖的版本不能低于 1.0.1 )
星天制作的 Java 工具包，是基于springboot 2 制作的 ,  基于JDK1.8，它是一个整合各工具类的整合starter。

里面包含多种工具，补丁，base接口，aop自动装配，注解，以及 acm型的算法。

**它有以下特性**：

- 1.在基础包中，提供了很多工具类，整合了若依，ican等大佬的common工具包，是一个hutool的扩展（因为带了hutool工具）。

- 2.在autoconfigue包中，提供了一些aop和自动装配的类，一些可以通过配置文件开启（方便注入），也可以手动注入bean。

- 3.有几个使用方式。
  1). 可以通过Util.静态类的方式调出静态方法，一般由XT开头的类，例如XTObjUtil等，以便直接调出类名、方法，根据类名意思了解该功能，好处是暂时不用查文档（我暂时没写（滑稽）），并且和hutool互补，能够加快开发效率。
  2). 可以通过new的方式获得非单例对象，也是常用的方式。
  3). 在启动类上加上@EnableXingToolsServer，开启自动注入，比如默认开启的四个注解aop(例如@Printlog注解，以及各种日志异常拦截aop等),其他的配置或者aop需要在配置文件中开启，以xingtools开头即可点出，例如

```properties
# 开启跨域
xingtools.enabled.cors-config=true
# 开启 redis
xingtools.enabled.redisconfig=true
# 开启 redis 的多个工具类，需 @Autowired 注入使用
xingtools.enabled.redisconfig-util=true
# 使用jackson2序列化(默认，可以不用配置)
xingtools.enabled.redisconfig-setting=jackson
```
> 注意这些只是进行了简单的配置，也就是大多数人的需求，也暂时不用每次自行创建配置类等。

> 所有aop和配置都可以通过配置类或者自行bean导入，导入启动后，会有日志说明配置启动成功，也可以用xingtools.enabled.log-info=false把自动导入的日志关闭。**由于减少侵入和大小占用，有些相应的配置解释会提供工件名，请自行配置相关依赖。**

- 4).也就是SpringSecurity,SpringCould的相关配置,可以通过配置@EnableXTCloudSecurity，@EnableXingToolsCloudServer，开启相关Security和Could的简化配置，精简你的项目。

综上不管是简单作为工具包，或者简化你的SpringBoot,SpringCloud等项目，该Starter放到你的依赖里也是一种不错的选择。



#### 安装教程

> 使用 Maven 导入依赖

Maven 依赖 (无版本号)

```xml
<dependency>
	<groupId>top.cutexingluo.tools</groupId>
	<artifactId>xingtool-spring-boot-starter</artifactId>
	<version>x.x.x</version>
</dependency>
```

Maven 最优依赖 v1.0.2版本

```xml
<dependency>
	<groupId>top.cutexingluo.tools</groupId>
	<artifactId>xingtool-spring-boot-starter</artifactId>
	<version>1.0.2</version>
</dependency>
```

Maven 最低依赖 v1.0.1版本：

```xml
<dependency>
	<groupId>top.cutexingluo.tools</groupId>
	<artifactId>xingtool-spring-boot-starter</artifactId>
	<version>1.0.1</version>
</dependency>
```

#### 使用说明

1.  静态工具类，new对象
2.  @EnableXingToolsServer等注解启动装配，使用配置文件快捷开启对应配置



如有bug，欢迎反馈。



### 更新公告

**2023-12-25 v1.0.3**

bug修复

1. 修复了 AccessLimitUtil.limitIP 加载 Ipdb 错误 的bug

更改部分

1. 修改了XTCompletionService实现，以及ThreadLocalHelper文件位置
2. 所有web 拦截类 从 Result 改为返回 IResult 接口，并且添加 GlobalResultFactory 接口用于全局返回结果，使用时需要注册到容器。

新增部分

1. 新增top.cutexingluo.tools.utils.se.algo.cpp包，里面包含各种算法（有些未测试），例如数论，几何，数据结构，图论，字符串等
2. 新增 BoolUtil  用于使 java 适配 c++性质。
3. 新增XTArrayUtil一些方法，用于移动数组元素，新增 XTSetUtil 的 Set 工具类。
4. 新增ClassMaker类，用于转化和反射，可以配合XTObjUtil。
5. 增加了 CommonResult 类，用于返回通用结果, 四大返回类重新继承了该类。
6. 新增红黑树 RBTree，迭代器默认中序遍历，即默认升序排序。属性全为protected，方便子类继承。常规推荐使用 TreeMap
7. 新增各种迭代器用于适配多种情况。可自行继承使用。
8. 新增启用 server 的 banner 和 cloud server 的 banner

**2023-10-21 v1.0.2**

1. 添加了 Supplier 接口,。与之对应各种适配类的调整。
2. 添加多线程注解AOP @MainThread @SonThread
3. 调整 XTAsync 类 , 并且添加 ThreadHelper接口，更快速使用。
4. 添加 XTString 工具类，可以通过 C++ 方法名称的方式使用。
5. 重构代码（位置和代码）。以下为 v1.0.2 重构更新日志：
```txt
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
```
**2023-9-26 v1.0.1**
正式版发布，中央仓库 ,  依赖最低版本不能低于 v1.0.1

1. 含有基础日志、事务、线程、异常等AOP，需要通过配置文件开启。

2. 包含基础包 Callable , Runnable 等系列封装的函数式接口及其实现类，如 XTRunCallUtil 工具类。

3. 含有系列注解，如 StrStatus,  IntStatus 等注解，可以对类属性进行验证。
4. 含有MVC 封装返回值的封装接口及其四个实现类，可以应对任何 code , msg , data 三件套的情况。
5. 含有传统设计方法封装工具类，包含 XTBuilder, 反射, 多线程, 锁等工具类。
6. 含有拦截器封装的工具类，例如 Oauth2 Authentication 的过滤 ，IP 限流 等工具类。
7. 支持 SpringCloud Oauth2 的简化配置，例如 @EnableXTCloudSecurity 开启 JWT 自动导入Bean。
8. 含有各配置的开启，例如 MybatisPlus 分页插件和 Redis 的配置 及其 四个以上的Redis工具类和其他工具类。
9. 对RuoYi、ican工具类的大部分支持，对 hutool 工具包默认导入以及扩展。
10. 对一些其他语言的扩展工具类支持，例如 JS 系列的方法名称,, 以及其他语言的操作，生成。
11. 对加密解密的各种封装，以及对token解析的封装，但还是建议使用 hutool工具。
12. 对许多工具类的扩展，包含字符，文件，IO，map等，以及各种基本类的对应工具类。

在 properties/yml 配置文件输入 xingtools 即可查看相关自动配置。

在该版本中，使用 @EnableXingToolsServer 开启自动配置后，默认开启 @XTException 简单异常拦截AOP 和 @PrintLog 简单日志AOP，如果不需要请自行关闭配置。

# XingTool 工具包

#### 介绍
XingTool工具包，v1.0.1正式版发布。
星天制作的Java工具包，是基于springboot制作的，它是一个整合各工具类的整合starter。

**它有以下特性**：

- 1.在基础包中，提供了很多工具类，整合了若依，ican等大佬的common工具包，是一个hutool的扩展（因为带了hutool工具）。

- 2.在autoconfigue包中，提供了一些aop和自动装配的类，一些可以通过配置文件开启（方便注入），也可以手动注入bean。

- 3.有几个使用方式。
  1). 可以通过Util.静态类的方式调出静态方法，一般由XT开头的类，例如XTObjUtil等，以便直接调出方法，根据类名意思了解该功能，好处是暂时不用查文档（我暂时没写（滑稽）），并且和hutool互补，能够加快开发效率。
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

Maven依赖 v1.0.1版本：
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

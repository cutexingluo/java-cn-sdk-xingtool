package top.cutexingluo.tools.designtools.proxy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.lang.reflect.InvocationHandler;

/**
 * 代理接口<br>
 * 实现了的代理处理器<br>
 * 可以 被继承或使用 的代理类
 *
 * @author XingTian
 * 版本v1.0.0
 */
@ConditionalOnClass(ProxyInvocationHandler.class)
public class XTProxy extends ProxyInvocationHandler implements InvocationHandler {

    @Override
    public void beforeTodo() {

    }

    @Override
    public void afterDone() {

    }
}

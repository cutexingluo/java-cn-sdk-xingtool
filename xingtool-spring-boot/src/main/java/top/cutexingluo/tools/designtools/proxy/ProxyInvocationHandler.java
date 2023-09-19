package top.cutexingluo.tools.designtools.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理，父类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 18:29
 */
public abstract class ProxyInvocationHandler implements InvocationHandler {
    private Object target;

    public ProxyInvocationHandler setTarget(Object target) {
        this.target = target;
        return this;
    }

    public Object getProxy() { //获得代理的实例 接口
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { //运行
        beforeTodo();
        Object result = method.invoke(target, args);
        afterDone();
        return result;
    }

    public abstract void beforeTodo();

    public abstract void afterDone();

    public Object run(Object proxy, Method method, Object[] args) throws Throwable { // 运行
        return invoke(proxy, method, args);
    }
}

package top.cutexingluo.tools.designtools.method;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

/**
 * 简易的类生成器
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/10/24 20:27
 * @since 1.0.3
 */
@Data
@AllArgsConstructor
public class ClassMaker<T> {
    private Class<T> clazz;

    /**
     * 转化
     *
     * @param obj   对象
     * @param clazz 类型
     * @return 转化后的对象，抛出异常则返回 null
     */
    public static <T> T cast(Object obj, Class<T> clazz) {
        return cast(obj, clazz::cast);
    }

    /**
     * 转化
     *
     * @param obj    对象
     * @param filter 过滤器
     * @return 转化后的对象，抛出异常则返回 null
     */
    public static <T> T cast(Object obj, Function<Object, T> filter) {
        if (obj == null) return null;
        try {
            if (filter == null) return (T) obj;
            return filter.apply(obj);
        } catch (ClassCastException | NullPointerException e) { // 特殊的转换会空指针异常
            return null;
        }
    }

    /**
     * 转化
     *
     * @param obj 对象
     * @return 转化后的对象，抛出异常则返回 null
     */
    public T cast(Object obj) {
        return cast(obj, clazz);
    }


    public T newInstance() {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T newInstanceNoExc() {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
        }
        return null;
    }

    public Constructor<T> getConstructor(Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Constructor<T> getConstructorNoExc(Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException ignored) {
        }
        return null;
    }

    /**
     * 生成对象
     */
    public static <V> V newInstance(Constructor<V> constructor, Object... initargs) {
        try {
            return constructor.newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成对象, 不输出内容
     */
    public static <V> V newInstanceNoExc(Constructor<V> constructor, Object... initargs) {
        try {
            return constructor.newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }
}

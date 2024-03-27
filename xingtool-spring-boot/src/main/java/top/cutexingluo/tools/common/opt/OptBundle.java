package top.cutexingluo.tools.common.opt;

import lombok.Data;
import top.cutexingluo.tools.common.base.IData;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * OptBundle 类
 * <p>类似 Opt 或 Optional</p>
 * <p>可用于执行链策略, 责任链设计模式</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2024/3/22 17:04
 */
@Data
public class OptBundle<T, Meta> implements IData<T> {

    /**
     * 执行函数
     */
    public interface OptAction<T, Meta> extends Function<OptData<T, Meta>, OptRes<T>> {
    }

    /**
     * 执行消费者
     */
    public interface OptConsumer<T, Meta> extends Consumer<OptData<T, Meta>> {
    }


    protected OptData<T, Meta> data;

    public OptBundle(OptData<T, Meta> data) {
        this.data = data;
    }


    /**
     * 获取内部的值
     */
    @Override
    public T data() {
        return data.value;
    }

    public boolean hasValue() {
        return data.value != null;
    }

    public boolean hasMeta() {
        return data.meta != null;
    }

    public boolean hasType(Class<?> type) {
        return type != null && type.isInstance(data.value);
    }

    /**
     * 比较clazz 变量
     */
    public boolean hasClazz(Class<?> type) {
        return data.clazz != null && data.clazz.equals(type);
    }


    /**
     * 比较clazz 变量
     * <p>clazz 是否是 type 子类</p>
     */
    public boolean isSubClazzFrom(Class<?> type) {
        return type != null && type.isAssignableFrom(data.clazz);
    }


    public void update(Object obj, boolean updateClazzIfPresent) {
        data.update(obj, updateClazzIfPresent);
    }

    public void updateCheckPresent(Object obj, boolean updateClazzIfPresent) {
        data.updateCheckPresent(obj, updateClazzIfPresent);
    }

    // then

    /**
     * then 方法, 返回值会赋值
     *
     * @param updateClazzIfPresent true=> clazz 存在才赋值 clazz 变量
     */
    public OptBundle<T, Meta> then(boolean updateClazzIfPresent, OptAction<T, Meta> action) {
        if (action != null) {
            OptRes<T> res = action.apply(data);
            data.update(res, updateClazzIfPresent);
        }
        return this;
    }

    public OptBundle<T, Meta> then(OptAction<T, Meta> action) {
        return then(false, action);
    }

    /**
     * thenCheckPresent 方法 res 不为空才赋值
     *
     * @param updateClazzIfPresent true=> clazz 存在才赋值 clazz 变量
     */
    public OptBundle<T, Meta> thenCheckPresent(boolean updateClazzIfPresent, OptAction<T, Meta> action) {
        if (action != null) {
            OptRes<T> res = action.apply(data);
            data.updateCheckPresent(res, updateClazzIfPresent);
        }
        return this;
    }

    public OptBundle<T, Meta> thenCheckPresent(OptAction<T, Meta> action) {
        return thenCheckPresent(false, action);
    }


    // pick

    /**
     * pick 读取数据
     */
    public OptBundle<T, Meta> pick(OptConsumer<T, Meta> consumer) {
        if (consumer != null) {
            consumer.accept(data);
        }
        return this;
    }

    /**
     * pick 读取数据
     * <p>value 存在才读取数据</p>
     */
    public OptBundle<T, Meta> filterPick(OptConsumer<T, Meta> consumer) {
        if (consumer != null && hasValue()) {
            consumer.accept(data);
        }
        return this;
    }

    /**
     * pick 读取数据
     * <p>value 不存在才读取数据</p>
     */
    public OptBundle<T, Meta> emptyPick(OptConsumer<T, Meta> consumer) {
        if (consumer != null && !hasValue()) {
            consumer.accept(data);
        }
        return this;
    }

    // then filter

    /**
     * filterThen 方法
     * <p>value 存在才执行函数</p>
     * <p>返回值会赋值</p>
     *
     * @param updateClazzIfPresent true=> clazz 存在才赋值 clazz 变量
     */
    public OptBundle<T, Meta> filterThen(boolean updateClazzIfPresent, OptAction<T, Meta> action) {
        if (action != null && hasValue()) {
            OptRes<T> res = action.apply(data);
            data.update(res, updateClazzIfPresent);
        }
        return this;
    }

    public OptBundle<T, Meta> filterThen(OptAction<T, Meta> action) {
        return filterThen(false, action);
    }

    /**
     * filterThen 方法
     * <p>value 存在才执行函数</p>
     * <p>返回值 value 存在才会赋值</p>
     *
     * @param updateClazzIfPresent true=> clazz 存在才赋值 clazz 变量
     */
    public OptBundle<T, Meta> filterThenPresent(boolean updateClazzIfPresent, OptAction<T, Meta> action) {
        if (action != null && hasValue()) {
            OptRes<T> res = action.apply(data);
            data.updateCheckPresent(res, updateClazzIfPresent);
        }
        return this;
    }

    public OptBundle<T, Meta> filterThenPresent(OptAction<T, Meta> action) {
        return filterThenPresent(false, action);
    }


    /**
     * emptyThen 方法
     * <p>value 不存在才执行函数</p>
     * <p>返回值会赋值</p>
     *
     * @param updateClazzIfPresent true=> clazz 存在才赋值 clazz 变量
     */
    public OptBundle<T, Meta> emptyThen(boolean updateClazzIfPresent, OptAction<T, Meta> action) {
        if (action != null && !hasValue()) {
            OptRes<T> res = action.apply(data);
            data.update(res, updateClazzIfPresent);
        }
        return this;
    }

    public OptBundle<T, Meta> emptyThen(OptAction<T, Meta> action) {
        return emptyThen(false, action);
    }


    /**
     * emptyThen 方法
     * <p>value 不存在才执行函数</p>
     * <p>返回值 value 存在才会赋值</p>
     *
     * @param updateClazzIfPresent true=> clazz 存在才赋值 clazz 变量
     */
    public OptBundle<T, Meta> emptyThenPresent(boolean updateClazzIfPresent, OptAction<T, Meta> action) {
        if (action != null && !hasValue()) {
            OptRes<T> res = action.apply(data);
            data.updateCheckPresent(res, updateClazzIfPresent);
        }
        return this;
    }

    public OptBundle<T, Meta> emptyThenPresent(OptAction<T, Meta> action) {
        return emptyThenPresent(false, action);
    }


    //------ 语义区 ------- methods --------

    /**
     * alias  filterThenPresent 方法
     * <pre>
     *     语义是：
     *    * 如果不为空才进行下去
     *    * 返回值不为空则赋值
     *    * alias filterThenPresent
     *    * 语义为继续，代表语句继续执行
     * </pre>
     */
    public OptBundle<T, Meta> checkThen(OptAction<T, Meta> action) {
        return this.filterThenPresent(true, action);
    }

    /**
     * alias  filterThen 方法
     * <pre>
     *     语义是：
     *    * 如果不为空才进行下去
     *    * 返回值无论怎样都赋值 (替换)
     *    * alias filterThen
     *    * 语义为继续，代表语句继续执行
     * </pre>
     */
    public OptBundle<T, Meta> checkThenReplace(OptAction<T, Meta> action) {
        return this.filterThen(true, action);
    }

    /**
     * alias  emptyThenPresent 方法
     * <pre>
     *     语义是：
     *    * 如果为空才进行下去
     *    *  返回值不为空则赋值
     *    * alias emptyThenPresent
     *    * 语义为合并，代表语句重新开始
     * </pre>
     */
    public OptBundle<T, Meta> combineEmpty(OptAction<T, Meta> action) {
        return this.emptyThenPresent(true, action);
    }


    /**
     * alias  emptyThen 方法
     * <pre>
     *     语义是：
     *    * 如果为空才进行下去
     *    * 返回值无论怎样都赋值 (替换)
     *    * alias emptyThen
     *    * 语义为合并，代表语句重新开始
     * </pre>
     */
    public OptBundle<T, Meta> combineEmptyReplace(OptAction<T, Meta> action) {
        return this.emptyThen(true, action);
    }
}

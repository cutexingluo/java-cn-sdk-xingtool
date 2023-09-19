package top.cutexingluo.tools.designtools.builder;

/**
 * 建造者Builder接口
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 18:38
 */
@FunctionalInterface
public interface Builder<T> {
    default Builder<T> getBuilder() {
        return this;
    }

    T build();
}
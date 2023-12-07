package top.cutexingluo.tools.utils.se.algo.cpp.base;

/**
 * 节点接口
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/10/24 11:36
 */
public interface BaseBiNode<T> extends BaseNode<T> {


    /**
     * 父节点
     */
    T parentNode();
}

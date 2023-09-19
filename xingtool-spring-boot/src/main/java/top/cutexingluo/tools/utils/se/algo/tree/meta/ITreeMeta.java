package top.cutexingluo.tools.utils.se.algo.tree.meta;

import java.util.List;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/8/11 19:41
 */
public interface ITreeMeta<T> {

    /**
     * 得到children
     *
     * @return {@link List}<{@link T}>
     */
    List<T> getChildren();

    /**
     * 是否存在children
     *
     * @return boolean
     */
    boolean hasChildren();
}

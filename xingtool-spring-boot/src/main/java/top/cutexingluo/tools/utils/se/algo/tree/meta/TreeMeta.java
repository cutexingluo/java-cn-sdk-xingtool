package top.cutexingluo.tools.utils.se.algo.tree.meta;

import lombok.Data;
import top.cutexingluo.tools.utils.se.algo.tree.TreeNode;
import top.cutexingluo.tools.utils.se.algo.tree.TreeUtil;

import java.util.List;

/**
 * 树节点 元信息
 * <p>该类实例用作 {@link TreeUtil} 里面的对象</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/8/11 18:35
 */
@Data
public class TreeMeta<IdType, T extends TreeNode<IdType>> implements ITreeMeta<T> {

    public TreeMeta(T currentNode, List<T> allNodes, TreeNodeHandler<IdType, T> handler) {
        this.currentNode = currentNode;
        this.allNodes = allNodes;
        this.handler = handler;
    }

    /**
     * 自动设置孩子节点
     */
    private boolean autoSetChildren = true;

    /**
     * 当前节点
     */
    private T currentNode;

    /**
     * 所有节点
     */
    private List<T> allNodes;

    /**
     * 节点处理器
     */
    private TreeNodeHandler<IdType, T> handler;

    /**
     * 保护，防止使用
     */
    protected TreeNodeHandler<IdType, T> getHandler() {
        return handler;
    }

    /**
     * 孩子列表, 使用 getChildren方法获取
     */
    private List<T> childrenList;

    /**
     * 得到children并赋给childrenList
     *
     * @return {@link List}<{@link T}>
     */
    @Override
    public List<T> getChildren() {
        if (childrenList == null) {
            childrenList = TreeUtil.getChildrenByDfs(currentNode, allNodes, handler);
        }
        return childrenList;
    }

    /**
     * 是否通过getChildrenList方法获取了childrenList
     *
     * @return boolean
     */
    @Override
    public boolean hasChildren() {
        return childrenList != null;
    }
}

package top.cutexingluo.tools.utils.se.algo.tree;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.cutexingluo.tools.utils.se.algo.tree.meta.ListNodeHandler;
import top.cutexingluo.tools.utils.se.algo.tree.meta.TreeListMeta;
import top.cutexingluo.tools.utils.se.algo.tree.meta.TreeMeta;
import top.cutexingluo.tools.utils.se.algo.tree.meta.TreeNodeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tree 树形工具
 * <p>可以实现列表转树形操作</p>
 * <p>实体类通过实现 TreeNode 接口, 即可使用该工具类</p>
 * <p>对应接口 {@link  TreeNode}</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/15 17:24
 */
public class TreeUtil {

    //--------------------- list to tree -----------------------------

    /**
     * 使用 dfs 生成 树形结构
     *
     * @param nodes        所有节点列表
     * @param rootParentId 根节点的parentId
     * @return {@link List}<{@link T}>
     */
    public static <IdType, T extends TreeNode<IdType>> List<T> buildByDfs(@NotNull List<T> nodes, IdType rootParentId) {
        return nodes.stream().filter(node -> {
                    if (node.nodeParentId() == null) {
                        return rootParentId == null;
                    }
                    return node.nodeParentId().equals(rootParentId);
                })
                .peek(node -> node.setNodeChildren(getChildrenByDfs(node, nodes))).collect(Collectors.toList());
    }

    /**
     * 使用 dfs 生成 树形结构
     * <p> 可以实现 handler 进行对每个节点进行精细操作 </p>
     *
     * @param nodes        所有节点列表
     * @param rootParentId 根节点的parentId
     * @param handler      处理程序
     * @return {@link List}<{@link T}>
     */
    public static <IdType, T extends TreeNode<IdType>> List<T> buildByDfs(@NotNull List<T> nodes, IdType rootParentId, TreeNodeHandler<IdType, T> handler) {
        return nodes.stream().filter(node -> {
                    if (node.nodeParentId() == null) {
                        return rootParentId == null;
                    }
                    return node.nodeParentId().equals(rootParentId);
                })
                .peek(node -> runTreeNodeHandler(node, nodes, handler)).collect(Collectors.toList());
    }

    /**
     * 通过dfs
     * 返回当前节点的所有子结点
     *
     * @param currentNode 当前节点
     * @param allNodes    所有节点
     * @return {@link List}<{@link T}>
     */
    public static <IdType, T extends TreeNode<IdType>> List<T> getChildrenByDfs(@NotNull T currentNode, @NotNull List<T> allNodes) {
        return allNodes.stream()
                .filter(node -> currentNode.nodeId().equals(node.nodeParentId()))
                .peek(node -> node.setNodeChildren(getChildrenByDfs(node, allNodes)))
                .collect(Collectors.toList());
    }


    /**
     * 通过dfs
     * 返回当前节点的所有子结点
     * <p>handler 操作每一个节点 </p>
     *
     * @param currentNode 当前节点
     * @param allNodes    所有节点
     * @param handler     处理程序
     * @return {@link List}<{@link T}>
     */
    public static <IdType, T extends TreeNode<IdType>> List<T> getChildrenByDfs(@NotNull T currentNode, @NotNull List<T> allNodes, TreeNodeHandler<IdType, T> handler) {
        return allNodes.stream()
                .filter(node -> currentNode.nodeId().equals(node.nodeParentId()))
                .peek(node -> runTreeNodeHandler(node, allNodes, handler)).collect(Collectors.toList());
    }

    /**
     * 树节点处理程序运行
     * <p>handler 操作每一个节点 </p>
     *
     * @param node     节点
     * @param allNodes 所有节点
     * @param handler  处理程序
     */
    public static <IdType, T extends TreeNode<IdType>> void runTreeNodeHandler(T node, @NotNull List<T> allNodes, @Nullable TreeNodeHandler<IdType, T> handler) {
        if (node == null) {
            return;
        }
        if (handler == null) { // handler为空
            node.setNodeChildren(getChildrenByDfs(node, allNodes));
        } else {
            TreeMeta<IdType, T> treeMeta = new TreeMeta<>(node, allNodes, handler);
            // 自定义 操作节点
            handler.handle(treeMeta);
            if (treeMeta.isAutoSetChildren()) {
                if (treeMeta.hasChildren()) {
                    node.setNodeChildren(treeMeta.getChildrenList());
                } else {
                    node.setNodeChildren(getChildrenByDfs(node, allNodes, handler));
                }
            }
        }
    }

    //---------------------tree to list -----------------------------

    /**
     * 通过dfs
     * 将树形转为平面列表形
     *
     * @param treeNodes    树形节点
     * @param rootParentId 根父ID
     * @return {@link List}<{@link T}>
     */
    public static <IdType, T extends TreeNode<IdType>> List<T> flatListByDfs(@NotNull List<T> treeNodes, IdType rootParentId) {
        return flatListByDfs(treeNodes, rootParentId, null);
    }

    /**
     * 通过dfs
     * 将树形转为平面列表形
     *
     * @param treeNodes    树形节点
     * @param rootParentId 根父ID
     * @param handler      处理程序
     * @return {@link List}<{@link T}>
     */
    public static <IdType, T extends TreeNode<IdType>> List<T> flatListByDfs(@NotNull List<T> treeNodes, IdType rootParentId, ListNodeHandler<IdType, T> handler) {
        ArrayList<T> newList = new ArrayList<>();
        treeNodes.stream().filter(node -> {
            if (node.nodeParentId() == null) {
                return rootParentId == null;
            }
            return node.nodeParentId().equals(rootParentId);
        }).peek(node -> flatListHandler(node, newList, handler));
        return newList;
    }

    /**
     * 平铺列表处理程序
     * <p>将当前节点及其所有子节点转为列表</p>
     * <p>将 Tree 形结构转化为 List 形</p>
     *
     * @param node    节点
     * @param newList 新列表 ( 用于装新数据 )
     * @param handler 处理程序（可以自行设置是否将孩子节点设置为空 / 操作节点）
     */
    public static <IdType, T extends TreeNode<IdType>> void flatListHandler(T node, @Nullable List<T> newList, ListNodeHandler<IdType, T> handler) {
        if (node == null) {
            return;
        }
        if (handler == null) {
            List<TreeNode<IdType>> children = node.nodeChildren(); // 获取孩子
            node.setNodeChildren(null);
            if (newList != null) newList.add(node);
            if (children != null) {
                children.forEach(child -> flatListHandler((T) child, newList, null));
            }
        } else {
            List<TreeNode<IdType>> children = node.nodeChildren(); // 获取孩子
            TreeListMeta<IdType, T> meta = new TreeListMeta<>(node, newList);
            handler.handle(meta);
            if (meta.isAutoSetChildrenToNull()) node.setNodeChildren(null);
            if (newList != null && meta.isAutoAddThisNode()) newList.add(node);
            if (children != null) {
                children.forEach(child -> flatListHandler((T) child, newList, handler));
            }
        }

    }

}

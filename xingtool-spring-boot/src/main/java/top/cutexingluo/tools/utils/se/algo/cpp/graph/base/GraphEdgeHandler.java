package top.cutexingluo.tools.utils.se.algo.cpp.graph.base;

import top.cutexingluo.tools.utils.se.algo.cpp.graph.GPreNode;

import java.util.Arrays;

/**
 * 图数据基础存边类
 * <p>存边法</p>
 * <p>必须从下标为1开始</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/10/27 16:24
 * @since 1.0.3
 */
public class GraphEdgeHandler extends GraphData {

    /**
     * 图(存边)
     */
    protected GPreNode[] E;

    /**
     * 所有节点最新连边索引
     */
    protected int[] head;


    /**
     * @param maxId 从1开始的最大id, 也就是序列化后的节点数量
     */
    public GraphEdgeHandler(int maxId) {
        super(maxId);
        E = new GPreNode[maxId + 1];
        head = new int[maxId + 1];
    }

    @Override
    public void clear() {
        super.clear();
        Arrays.fill(E, null);
        Arrays.fill(head, 0);
    }

    /**
     * 加边，默认w 权值为1
     * <p>不建议使用</p>
     *
     * @param u 出发点
     * @param v 结束点
     */
    @Override
    public void addEdge(int u, int v) {
        addEdge(u, v, 1);
    }

    /**
     * 加边
     *
     * @param u 出发点
     * @param v 结束点
     * @param w 权值或者索引等其他数据
     */
    public void addEdge(int u, int v, int w) {
        super.addEdge(u, v); // edge ++;
        E[edge] = new GPreNode(v, w, head[u]); // 赋值
        head[u] = edge;
    }
}

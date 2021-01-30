package graph;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author: lsj
 * @date: 2021/1/30 13:48
 * Description: 稀疏图。适合用邻接表存储。例如：
 *    -
 *   |0|: [1]             0---1
 *   |1|: [0, 2, 3]         / |
 *   |2|: [1, 3]          3---2
 *   |3|: [1, 2]
 *    -
 */
public class SparseGraph {
    /**
     * 边的数量
     */
    private int edges;
    /**
     * 点的数量
     */
    private int points;

    /**
     * 无向图还是有向图
     */
    private boolean directed;

    /**
     * 邻接表，内层ArrayList中存储相邻的节点
     */
    private ArrayList<ArrayList<Integer>> graph;

    public SparseGraph(int points, boolean directed){
        //jvm: -ea
        assert points >= 0 : "points numbers should be positive.";

        this.points = points;
        edges = 0;
        this.directed = directed;

        graph = new ArrayList<>(points);
        for (int i = 0; i < points; i++) {
            graph.add(new ArrayList<>());
        }
    }

    public int getEdges() {
        return edges;
    }

    public int getPoints() {
        return points;
    }

    /**
     * 向图中添加一条边
     * @param v point v
     * @param w point w
     */
    public void addEdge(int v, int w){
        assert v >= 0 && v < points: "illegal argument v.";
        assert w >= 0 && w < points: "illegal argument w.";

        //由于hasEdge是O(n)的，如果要去掉平行边，那么addEdge也就变成了O(n)
//        if(hasEdge(v, w)){
//            return;
//        }

        graph.get(v).add(w);

        if(v != w && !directed){
            graph.get(w).add(v);
        }
        edges++;
    }

    /**
     * 验证图中是否有从v到w的边
     * @param v point v
     * @param w point w
     * @return true or false
     */
    public boolean hasEdge(int v, int w){
        assert v >= 0 && v < points: "illegal argument v.";
        assert w >= 0 && w < points: "illegal argument w.";

        return graph.get(v).contains(w);
    }

    /**
     * 返回图中一个顶点的所有邻接点。这个操作相比邻接矩阵快。
     * @param v point v
     * @return all edges of v
     */
    public Iterator<Integer> getAdjacentPoints(int v){
        assert v >= 0 && v < points: "illegal argument v.";
        return graph.get(v).iterator();
    }


}

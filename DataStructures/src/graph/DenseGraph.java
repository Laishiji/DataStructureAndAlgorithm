package graph;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author: lsj
 * @date: 2021/1/30 13:48
 * Description: 稠密图，如完全图。适合用邻接矩阵存储。
 */
public class DenseGraph {
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
     * 这里仅实现无权图，因此用布尔类型即可
     */
    private boolean[][] graph;

    public DenseGraph(int points, boolean directed) {
        //jvm: -ea
        assert points >= 0 : "points numbers should be positive.";

        this.points = points;
        edges = 0;
        this.directed = directed;

        //初始化为所有值为false的布尔矩阵，表示没有任何边
        graph = new boolean[points][points];
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

        if(hasEdge(v, w)){
            return;
        }

        graph[v][w] = true;

        if(!directed){
            //如果是无向图
            graph[w][v] = true;
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
        return graph[v][w];
    }

    /**
     * 返回图中一个顶点的所有邻接点
     * @param v point v
     * @return all edges of v
     */
    public Iterator<Integer> getAdjacentPoints(int v){
        assert v >= 0 && v < points: "illegal argument v.";

        ArrayList<Integer> adjacent = new ArrayList<>();

        for (int i = 0; i < points; i++) {
            if(graph[v][i]){
                adjacent.add(i);
            }
        }
        return adjacent.iterator();
    }
}

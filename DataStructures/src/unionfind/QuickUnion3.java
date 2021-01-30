package unionfind;

/**
 * @author: lsj
 * @date: 2021/1/30 11:51
 * Description: 优化QuickUnion2。
 * 1. 合并时比较树高而非集合大小，树高小的指向树高大的。以如下两棵树合并为例：
 *         7            8
 *      / / | \         |
 *     0 1  2  3        5
 *                      |
 *                      6
 * 若前者指向后者，则合并后树高为3；若后者指向前者，合并后树高为4。这种情况下基于集合size的优化就略逊一筹。
 * 2. find操作添加路径压缩。
 * 3. 并查集的时间复杂度：log(*n)，近乎O(1)
 *                  0                 n <= 1
 *     log(*n)= {
 *                 1+log(*(log n))    n > 1
 */
public class QuickUnion3 {
    /**
     * 存储元素的父亲节点索引
     */
    private final int[] parent;

    /**
     * rank[i]表示以元素i为根的树的高度。
     */
    private final int[] rank;

    public QuickUnion3(int size){
        parent = new int[size];
        rank = new int[size];

        //初始化，每个元素的父亲都是自己
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int size(){
        return parent.length;
    }

    /**
     * 找到元素p的根节点
     * @param p element p
     * @return root of element p
     */
    private int find(int p){
        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("ps is out of bound.");
        }

        //当元素p指向自己的时候，p就是根节点
        while(p != parent[p]){
            //p的父亲指向p的父亲的父亲。路径压缩，降低树高。将
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    /**
     * 查看两个元素是否同属同一集合。
     * @param p element p
     * @param q element q
     * @return true or false
     */
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }

    /**
     * 合并两个元素。
     * @param p element p
     * @param q element q
     */
    public void unionElements(int p, int q){
        int pRoot = find(p);
        int qRoot = find(q);

        if(pRoot == qRoot){
            return;
        }

        //树高小的指向树高大的
        if(rank[pRoot] < rank[qRoot]){
            //p的根节点指向q的根节点
            parent[pRoot] = qRoot;
        }else if(rank[pRoot] > rank[qRoot]){
            parent[qRoot] = pRoot;
        }else{
            //两树高度相等，则随意选一棵树指向另一棵树，被指向的树的高度+1
            parent[pRoot] = qRoot;
            rank[qRoot]++;
        }

    }
}

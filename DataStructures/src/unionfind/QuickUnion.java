package unionfind;

/**
 * @author: lsj
 * @date: 2021/1/30 10:43
 * Description: 这种并查集的实现可以看作是一棵树，不过这种树是孩子节点指向父亲节点。
 *              union操作时间复杂度O(treeHeight)，find操作时间复杂度O(treeHeight)，因此叫UnionFind。
 * 1. element           0 1 2 3 4 5
 *
 *    index(element)    0 1 2 3 4 5
 *    parent            0 1 2 3 4 5
 *
 * 2. element           0 1 2 3
 *                            ^\
 *                              4
 *
 *    index(element)    0 1 2 3 4 5
 *    parent            0 1 2 3 3 5
 *
 * 3. element           0 1 3
 *                         /^\
 *                        2   4
 *
 *    index(element)   0 1 2 3 4 5
 *    parent           0 1 3 3 3 5
 *
 * 4. element           0 3
 *                       /^\
 *                      2   4
 *                     /^
 *                    1
 *
 *    index(element)   0 1 2 3 4 5
 *    parent           0 2 3 3 3 5
 */
public class QuickUnion {
    /**
     * 存储元素的父亲节点索引
     */
    private final int[] parent;

    public QuickUnion(int size){
        parent = new int[size];

        //初始化，每个元素的父亲都是自己
        for (int i = 0; i < size; i++) {
            parent[i] = i;
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

        while(p != parent[p]){
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
        //p的根节点指向q的根节点
        parent[pRoot] = qRoot;
    }
}

package unionfind;

/**
 * @author: lsj
 * @date: 2021/1/30 11:34
 * Description: 优化QuickUnion。在数据量较大时，QuickUnion的树高很大，性能降低。
 */
public class QuickUnion2 {
    /**
     * 存储元素的父亲节点索引
     */
    private final int[] parent;

    /**
     * size[i]表示以元素i为根的集合的大小。
     * 用于在union操作时根据两个元素的根节点所在集合的size判断合并方向，避免树高过大。
     */
    private final int[] size;

    public QuickUnion2(int size){
        parent = new int[size];
        this.size = new int[size];

        //初始化，每个元素的父亲都是自己
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            this.size[i] = 1;
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

        //让元素个数较少的指向元素个数较多的
        if(size[pRoot] < size[qRoot]){
            //p的根节点指向q的根节点
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        }else {
            parent[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }

    }
}

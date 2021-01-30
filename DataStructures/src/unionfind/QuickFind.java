package unionfind;

/**
 * @author: lsj
 * @date: 2021/1/29 15:54
 * Description: union操作时间复杂度O(n)，find操作时间复杂度O(1)，因此叫QuickFind。
 */
public class QuickFind {
    /**
     * 存储元素的集合编号
     */
    private final int[] id;

    public QuickFind(int size) {
        id = new int[size];

        //初始化，每个数组元素都不同表示每个元素都是一个单独的集合
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }

    public int size(){
        return id.length;
    }

    /**
     * 查找元素p对应的集合编号
     * @param p element
     * @return collection id
     */
    private int find(int p){
        if(p < 0 || p >= id.length){
            throw new IllegalArgumentException("ps is out of bound.");
        }
        return id[p];
    }

    /**
     * 查看两个元素是否同属同一集合
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
        int pId = find(p), qId = find(q);

        //如果已经同属一个集合，直接返回
        if(pId == qId){
            return;
        }

        //遍历集合编号，将元素p的集合编号改为元素q的集合编号
        for (int i = 0; i < id.length; i++) {
            if(id[i] == pId){
                id[i] = qId;
            }
        }
    }
}

package linkedlist;

/**
 * @author: lsj
 * @date: 2021/1/24 10:30
 * Description: 链表。
 */
public class LinkedList<E> {

    /**
     * 节点定义
     * @param <E>
     */
    private static class Node<E>{
        E value;
        Node<E> next;

        public Node(E value ,Node<E> next){
            this.value = value;
            this.next = next;
        }
    }

    private final transient Node<E> dummyHead;
    private transient int size;

    public LinkedList(){
        dummyHead = new Node<>(null, null);
        size = 0;
    }

   public int getSize(){
        return size;
   }

   public boolean isEmpty(){
        return size == 0;
   }

    /**
     * 在指定索引处添加元素
     * @param value value
     * @param index 索引
     */
   public void add(E value, int index){
       if(index < 0 || index > size){
           throw new IllegalArgumentException("Add failed. Illegal index.");
       }

       Node<E> prev = dummyHead;
       for (int i = 0; i < index; i++) {
           prev = prev.next;
       }

       prev.next = new Node<>(value, prev.next);
       ++size;
   }

    /**
     * 表头添加元素
     * @param value value
     */
    public void addFirst(E value){
        add(value,0);
    }

    /**
     * 表尾添加元素
     * @param value value
     */
    public LinkedList<E> add(E value){
        add(value, size);
        return this;
    }

    /**
     * 获取指定索引节点的值
     * @param index index
     * @return value
     */
    public E get(int index){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Get failed. Illegal index.");
        }
        Node<E> cur = dummyHead;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        return cur.value;
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size);
    }

    /**
     * 修改指定索引的值
     * @param value value
     * @param index index
     */
    public void set(E value, int index){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Set failed. Illegal index.");
        }

        Node<E> cur = dummyHead;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.value = value;
    }

    /**
     * 删除指定索引的节点
     * @param index index
     * @return 删除节点的value
     */
    public E remove(int index){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Delete failed. Illegal index.");
        }

        Node<E> prev = dummyHead;
        for (int i = 0; i < index-1; i++) {
            prev = prev.next;
        }
        Node<E> delNode = prev.next;
        prev.next = delNode.next;
        //help GC
        delNode.next = null;
        --size;

        return delNode.value;
    }

    public E removeFirst(){
        return remove(0);
    }

    public E removeLast(){
        return remove(size);
    }

    public boolean contains(E value){
        Node<E> cur = dummyHead.next;
        while (cur != null){
            if(cur.value.equals(value)){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node<E> cur = dummyHead.next;
        while (cur != null){
            stringBuilder.append(cur.value.toString()).append("->");
            cur = cur.next;
        }
        stringBuilder.append("NULL");
        return stringBuilder.toString();
    }
}

package tree.heapandpriorityqueue;

/**
 * @author: lsj
 * @date: 2021/1/26 14:23
 * Description: 使用大根堆实现优先队列。
 */
public class PriorityQueue<E extends Comparable<E>> {
    private MaxHeap<E> maxHeap;

    public PriorityQueue(){
        maxHeap = new MaxHeap<>();
    }

    public int size(){
        return maxHeap.size();
    }

    public boolean isEmpty(){
        return maxHeap.isEmpty();
    }

    public E getFirst(){
        return maxHeap.getMax();
    }

    public void enqueue(E e){
        maxHeap.add(e);
    }

    public E dequeue(){
        return maxHeap.extractMax();
    }
}

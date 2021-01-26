package tree.heapandpriorityqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: lsj
 * @date: 2021/1/26 10:37
 * Description: 大根堆。主要实现了add, extractMax, replace和heapify四个操作。
 */
public class MaxHeap<E extends Comparable<E>> {
    /**
     * 使用动态数组ArrayList作为大根堆的底层数据结构
     */
    private List<E> data;

    public MaxHeap(int capacity){
        data = new ArrayList<>(capacity);
    }

    public MaxHeap(){
        data = new ArrayList<>();
    }

    public MaxHeap(E[] arr){
        data = Arrays.asList(arr);
        //heapify
        for(int i = parentIdx(arr.length - 1); i >= 0; i--){
            shiftDown(i);
        }
    }

    public int size(){
        return data.size();
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }

    /**
     * 返回完全二叉树的数组表示中，某节点的父亲节点的索引
     * @param index child index
     * @return parent index
     */
    private int parentIdx(int index){
        if(index == 0){
            throw new IllegalArgumentException("index zero doesn't have a parent.");
        }
        return (index - 1) / 2;
    }

    /**
     * 返回完全二叉树的数组表示中，某节点的左孩子节点的索引
     * @param index parent index
     * @return index of left child
     */
    private int leftChildIdx(int index){
        return index * 2 + 1;
    }

    private int rightChildIdx(int index){
        return index * 2 + 2;
    }

    /**
     * 向堆中添加元素：首先将元素添加至堆的末尾，然后执行shift up操作。
     * @param e 待添加元素
     */
    public void add(E e){
        //越界则自动扩容
        data.add(e);
        shiftUp(data.size() - 1);
    }

    /**
     * 若新添加元素大于父亲节点的值，则新元素上移（交换），循环该操作直至新元素不再大于父亲节点。
     * @param index 新添加元素的索引
     */
    private void shiftUp(int index){
        //比较添加元素和其父亲的值
        while(index > 0 && data.get(parentIdx(index)).compareTo(data.get(index)) < 0){
            swap(parentIdx(index), index);
            index = parentIdx(index);
        }
    }

    private void swap(int i, int j){
        if(i < 0 || i >= size() || j < 0 || j >= size()){
            throw new IllegalArgumentException("Index is illegal.");
        }

        E temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    public E getMax(){
        if(size() == 0){
            throw new RuntimeException("Heap is null");
        }
        return data.get(0);
    }

    /**
     * 获取大根堆的最大值，即根节点的值，并移除根节点。
     * @return
     */
    public E extractMax(){
        E result = getMax();

        //移除根节点：使用完全二叉树最后一个节点覆盖根节点，并删除最后一个节点
        data.set(0, data.get(size()-1));
        data.remove(size()-1);
        //执行下沉操作
        shiftDown(0);

        return result;
    }

    private void shiftDown(int index) {
        //当节点的左孩子索引越界时，下沉操作终止。
        // 为什么不是右孩子越界终止？因为可能右孩子越界，但是左孩子没有越界，且左孩子比其父亲大，还需要下沉一次。
        while (leftChildIdx(index) < size()){
            int temp = leftChildIdx(index);
            //判断右孩子索引是否越界
            if(temp + 1 < size() && data.get(temp + 1).compareTo(data.get(temp)) > 0) {
                //若右孩子大于左孩子，则temp改为右孩子索引
                temp++;
            }
            //待下沉节点若大于其左右孩子，循环终止；否则与其左右孩子中较大的一个交换。
            if(data.get(index).compareTo(data.get(temp)) >= 0){
                break;
            }
            swap(index, temp);
            index = temp;
        }
    }

    /**
     * 取出堆中的最大元素，并且新加入一个元素
     * @param e 添加的元素
     * @return 取出的最大元素
     */
    public E replace(E e){
        E result = getMax();
        //or data.set(0, e);后再shiftDown(0);
        add(e);
        return result;
    }

}

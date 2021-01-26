package tree.heapandpriorityqueue;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author: lsj
 * @date: 2021/1/26 13:19
 */
public class HeapMainTest {
    public static void main(String[] args) {
        //Test add and extractMax.
        int LEN = 10000;
        MaxHeap<Integer> maxHeap = new MaxHeap<>(LEN);
        Random random = new Random();
        for (int i = 0; i < LEN; i++) {
            maxHeap.add(random.nextInt(LEN));
        }

        int count = 0;
        while (!maxHeap.isEmpty()){
            if(count % 35 == 0){
                System.out.println();
            }
            count++;
            System.out.print(maxHeap.extractMax()+" ");
        }

    }
}

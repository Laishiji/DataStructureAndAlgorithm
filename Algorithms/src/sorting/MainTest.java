package sorting;

import java.util.Arrays;

/**
 * @author: lsj
 * @date: 2021/1/28 11:04
 * Description: 测试排序算法
 */
public class MainTest {
    public static void main(String[] args) {
        Integer[] array = Utils.generateRandomArray(10000, 0, 10000);
        Integer[] array2 = Arrays.copyOf(array, array.length);
        Integer[] array3 = Arrays.copyOf(array, array.length);
        Integer[] array4 = Arrays.copyOf(array, array.length);

        Utils.testSort("selectionSort", array);
        Utils.testSort("bubbleSort", array2);
        Utils.testSort("insertionSort", array3);
        Utils.testSort("mergeSort", array4);
    }
}

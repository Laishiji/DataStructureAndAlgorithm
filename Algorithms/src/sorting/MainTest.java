package sorting;

import java.util.Arrays;

/**
 * @author: lsj
 * @date: 2021/1/28 11:04
 * Description: 测试排序算法
 */
public class MainTest {
    public static void main(String[] args) {
        Integer[] array = Utils.generateRandomArray(50000, 0, 10000);
        Integer[] array2 = Arrays.copyOf(array, array.length);
        Integer[] array3 = Arrays.copyOf(array, array.length);
        Integer[] array4 = Arrays.copyOf(array, array.length);
        Integer[] array5 = Arrays.copyOf(array, array.length);
        Integer[] array6 = Arrays.copyOf(array, array.length);

        Utils.testSort("selectionSort", array);
        Utils.testSort("bubbleSort", array2);
        Utils.testSort("insertionSort", array3);
        Utils.testSort("mergeSort", array4);
        Utils.testSort("mergeSortBottomUp", array5);
        Utils.testSort("quickSort", array6);

        System.out.println("===============================================");
        System.out.println("测试近乎有序数组的排序：");
        Integer[] array7 = Utils.generateNearlyOrderedArray(100000, 100);
        Integer[] array8 = Arrays.copyOf(array7, array7.length);
        Integer[] array9 = Arrays.copyOf(array7, array7.length);
        Utils.testSort("mergeSort", array7);
        //需要调整虚拟机栈-Xss3m，否则会栈溢出
        Utils.testSort("quickSort", array8);
        //使用随机化快排
        Utils.testSort("randomizedQuickSort", array9);

    }
}

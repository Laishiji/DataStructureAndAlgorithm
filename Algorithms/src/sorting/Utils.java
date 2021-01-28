package sorting;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: lsj
 * @date: 2021/1/28 11:09
 * Description: 实现选择，冒泡，插入，堆，归并，快速排序
 */
public class Utils {
    /**
     * 选择排序：外层循环遍历整个数组，内层循环从外层循环当前索引+1处开始遍历剩余数组，找到剩余数组的最小值，将该最小值与外层循环当前值交换。
     * 每一趟内层循环排序好一个最小值。
     * @param arr array to be sorted
     * @param <T> Type of array
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arr){
        for (int i = 0; i < arr.length; i++) {
            //记录每一趟的最小值索引
            int minIndex = i;

            //找到每一趟的最小值的索引
            for (int j = i + 1; j < arr.length; j++) {
                minIndex = arr[minIndex].compareTo(arr[j]) > 0 ? j : minIndex;
            }

            swap(arr, minIndex, i);
        }

    }

    /**
     * 冒泡排序：内层循环两两比较，将大的数放在后面.
     * 每一趟内层循环找到一个最大值。
     * @param arr array to be sorted
     * @param <T> Type of array
     */
    public static <T extends Comparable<T>> void bubbleSort(T[] arr){
        for(int i = arr.length - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(arr[j].compareTo(arr[j+1]) > 0){
                    swap(arr, j , j+1);
                }
            }
        }
    }

    /**
     * 插入排序：外层循环遍历数组（选择某张牌），内层循环将外层循环选择的牌插入到前面已经排好序的牌中。
     * 插入排序在样本较小且基本有序时效率较高。
     * @param arr array to be sorted
     * @param <T> Type of array
     */
    public static <T extends Comparable<T>> void insertionSort(T[] arr){
        for (int i = 1; i < arr.length; i++) {
            //寻找arr[i]在其之前的插入位置
            T e = arr[i];
            //j保存e应该插入的位置
            int j;
            for (j = i; j > 0 && arr[j-1].compareTo(e) > 0; j--) {
                //向后移动一个元素
                arr[j] = arr[j-1];
            }
            arr[j] = e;
//            for (int j = i; j > 0 ; j--) {
//                //向前两两比较并交换
//                if(arr[j].compareTo(arr[j-1]) < 0){
//                    swap(arr, j, j-1);
//                }else{
//                    break;
//                }
//            }
        }
    }

    /**
     * 归并排序。归并排序是稳定的，Arrays中对对象排序就是使用的归并排序。JDK中的归并排序是经过大量优化的，叫TomSort,
     * TimSort属于归并与插入的混合排序算法。
     * @param arr array to be sorted
     * @param <T> Type of array
     */
    public static <T extends Comparable<T>> void mergeSort(T[] arr){
        mergeSort(arr, 0, arr.length-1);
    }

    /**
     * 递归方法，归并排序
     * @param arr array to be sorted
     * @param leftIndex left index
     * @param rightIndex right index
     * @param <T> Type of array
     */
    private static <T extends Comparable<T>> void mergeSort(T[] arr, int leftIndex, int rightIndex) {
        if(leftIndex >= rightIndex){
            return;
        }
        //计算左边界与右边界的中点，注意不能使用(leftIndex+rightIndex)/2，以防止整型溢出
        int midIndex = leftIndex + (rightIndex - leftIndex)/2;

        //对数组左半部分排序
        mergeSort(arr, leftIndex, midIndex);
        //对数组右半部分排序
        mergeSort(arr, midIndex+1, rightIndex);

        //合并已经有序的左右两半部分数组
        if(arr[midIndex].compareTo(arr[midIndex+1]) > 0) {
            merge(arr, leftIndex, midIndex + 1, rightIndex);
        }
    }

    /**
     * 额外开辟一个数组，借助该数组合并两半部分有序的数组，返回新开辟的数组
     * @param arr array to be merged
     * @param leftIndex left index
     * @param midIndex middle index
     * @param rightIndex right index
     * @param <T> new array
     */
    private static <T extends Comparable<T>> void merge(T[] arr, int leftIndex, int midIndex, int rightIndex) {
        //只复制[leftIndex, rightIndex]的值，注意copyOfRange的to参数是开区间，因此此处需要+1
        T[] temp = Arrays.copyOfRange(arr, leftIndex, rightIndex+1);

        int i = leftIndex, j = midIndex, k = leftIndex;
        while(i < midIndex && j <= rightIndex){
            if(temp[i-leftIndex].compareTo(temp[j-leftIndex]) <= 0){
                arr[k++] = temp[i-leftIndex];
                i++;
            }else{
                arr[k++] = temp[j-leftIndex];
                j++;
            }
        }

        //以下两个循环只可能执行其中某一个
        while(i < midIndex){
            arr[k++] = temp[i-leftIndex];
            i++;
        }
        while(j <= rightIndex){
            arr[k++] = temp[j-leftIndex];
            j++;
        }

    }

    public static <T extends Comparable<T>> void quickSort(T[] arr){

    }

    private static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 生成有n个元素的随机数组,每个元素的随机范围为[rangeL, rangeR]
     * @param n number of elements
     * @param rangeL left range
     * @param rangeR right range
     * @return array
     */
    public static Integer[] generateRandomArray(int n, int rangeL, int rangeR) {
        assert rangeL <= rangeR : "range error!";

        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++){
            arr[i] = (int) (Math.random() * (rangeR - rangeL + 1) + rangeL);
        }

        return arr;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr){
        for( int i = 0 ; i < arr.length - 1 ; i ++ ){
            if( arr[i].compareTo(arr[i+1]) > 0 ){
                return false;
            }
        }
        return true;
    }

    /**
     * 通过反射打印每个排序函数运行的时间，以比较不同排序算法的性能。
     * @param methodName 方法名
     * @param arr array
     * @param <T> type of elements
     */
    public static <T extends Comparable<T>> void testSort(String methodName, T[] arr){
        try{
            Method sortMethod = Utils.class.getMethod(methodName, Comparable[].class);

            long startTime = System.currentTimeMillis();
            sortMethod.invoke(null, (Object) arr);
            long endTime = System.currentTimeMillis();

            assert isSorted(arr) : "this array is not sorted.";

            System.out.println((methodName.charAt(0)+"").toUpperCase() +
                    methodName.substring(1) +
                    " took 【" + (endTime-startTime) + "ms】 to finish sorting." );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}

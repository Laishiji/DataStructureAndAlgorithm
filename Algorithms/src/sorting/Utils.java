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
     * 自顶向下的归并排序。归并排序是稳定的，Arrays中对对象排序就是使用的归并排序。JDK中的归并排序是经过大量优化的，叫TomSort,
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
     * 额外开辟一个数组，借助该数组合并两半部分有序的数组
     * @param arr array to be merged
     * @param leftIndex left index
     * @param midIndex middle index
     * @param rightIndex right index
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

    /**
     * 自底向上的归并排序，迭代实现。
     * @param arr arr to be sorted
     * @param <T> type of elements
     */
    public static <T extends Comparable<T>> void mergeSortBottomUp(T[] arr){
        for (int size = 1; size < arr.length ; size *= 2) {
            for (int i = 0; i + size < arr.length; i += 2*size) {
                if(arr[i+size-1].compareTo(arr[i+size]) > 0){
                    merge(arr, i, i + size, Math.min(i + 2 * size - 1, arr.length - 1));
                }
            }
        }
    }

    /**
     * 单轴快排。数组近乎有序时，退化为时间复杂度为n平方的算法
     * @param arr arr to be sorted
     * @param <T> type of elements
     */
    public static <T extends Comparable<T>> void quickSort(T[] arr){
        quickSort(arr, 0, arr.length - 1);
    }

    public static <T extends Comparable<T>> void randomizedQuickSort(T[] arr){
        //使用Knuth算法打乱数组，优化快速排序
        knuthShuffle(arr);
        quickSort(arr, 0, arr.length - 1);
    }

    private static <T extends Comparable<T>> void quickSort(T[] arr, int leftIndex, int rightIndex) {
        if(leftIndex >= rightIndex){
            return;
        }
        //轴左边的元素小于它，右边的元素大于它
        int pivotIndex = partition(arr, leftIndex, rightIndex);
        quickSort(arr, leftIndex, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, rightIndex);
    }

    /**
     * let arr[leftIndex...pivotIndex-1] < arr[pivotIndex], arr[pivotIndex+1...rightIndex] > arr[pivotIndex]
     * @param arr array to be sorted
     * @param leftIndex left boundary index
     * @param rightIndex right boundary index
     * @param <T> type of elements
     * @return pivot index
     */
    private static <T extends Comparable<T>> int partition(T[] arr, int leftIndex, int rightIndex) {
        //选定第一个元素作为轴
        T pivot = arr[leftIndex];

        //由于pivot选定为左边界，因此这里的l需要+1
        int l = leftIndex + 1, r = rightIndex;
        //将比pivot小的元素和比pivot大的元素划分为两部分
        while(l <= r){
            //从左往右找到第一个比pivot大的元素的索引
            while(l <= r && arr[l].compareTo(pivot) <= 0){
                l++;
            }
            //从右往左找到第一个比pivot小的元素的索引
            while(l <= r && arr[r].compareTo(pivot) >= 0){
                r--;
            }

            if(l <= r){
                swap(arr, l, r);
            }
        }
        //pivot换到中间，完成一次partition
        swap(arr, leftIndex, r);

        return r;
    }

    /**
     * Knuth洗牌算法，用于随机化数组元素，优化快速排序。
     * 该算法保证了每一个元素出现在每一个位置的概率相等。
     * @param arr array to be randomized
     * @param <T> type of elements
     */
    private static <T extends Comparable<T>> void knuthShuffle(T[] arr){
        for(int i = arr.length-1; i >= 0; i--){
            //arr[i]与0-i之间的随机一个数交换
            swap(arr, i, (int)(Math.random()*(i+1)));
        }
    }

    /**
     * 堆排序。
     * @param arr array to be sorted
     * @param <T> type of elements
     */
    public static <T extends Comparable<T>> void heapSort(T[] arr){
        heapify(arr);
        //逆序遍历数组，每次与堆顶元素交换，然后堆顶元素执行下沉(shiftDown)操作
        for (int i = arr.length-1;  i >= 0; i--) {
            swap(arr, 0, i);
            //每次下沉的边界为i，否则又会打乱顺序
            shiftDown(arr, 0, i);
        }
    }

    /**
     * 由于任意数组都可以看作一棵完全二叉树，因此heapify操作可将任意数组堆化。所谓堆化，就是将任意数组转化为二叉堆。
     * heapify的实现：从最后一个非叶子节点开始，依次对每一个非叶子节点执行shift down操作即可。
     * 最后一个非叶子节点的索引定位：若数组下标从0开始，则为(arr.length-1-1)/2。
     * @param arr array to be heapified
     * @param <T> type of elements
     */
    private static <T extends Comparable<T>> void heapify(T[] arr) {
        for (int i = (arr.length - 2)/2; i >= 0 ; i--) {
            shiftDown(arr, i, arr.length-1);
        }
    }

    /**
     * 对索引为i的节点执行下沉操作。这里构建的是大根堆。
     * @param arr array
     * @param i 待下沉节点索引
     * @param rightBound 每次下沉的右边界
     * @param <T> type of elements
     */
    private static <T extends Comparable<T>> void shiftDown(T[] arr, int i, int rightBound) {
        while (2*i+1 < rightBound){
            int temp = 2*i+1;
            //判断右孩子索引是否越界
            if(temp + 1 < rightBound && arr[temp+1].compareTo(arr[temp]) > 0) {
                //若右孩子大于左孩子，则temp改为右孩子索引
                temp++;
            }

            //待下沉节点若大于其左右孩子，循环终止；否则与其左右孩子中较大的一个交换。
            if(arr[i].compareTo(arr[temp]) >= 0){
                break;
            }
            swap(arr, i, temp);

            //继续下沉
            i = temp;
        }
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

    /**
     *   生成一个近乎有序的数组
     *   首先生成一个含有[0...n-1]的完全有序数组, 之后随机交换swapTimes对数据
     *   swapTimes定义了数组的无序程度:
     *   swapTimes == 0 时, 数组完全有序
     *   swapTimes 越大, 数组越趋向于无序
     * @param n number of integers
     */
    public static Integer[] generateNearlyOrderedArray(int n, int swapTimes){

        Integer[] arr = new Integer[n];
        for( int i = 0 ; i < n ; i ++ ){
            arr[i] = i;
        }
        for( int i = 0 ; i < swapTimes ; i ++ ){
            int a = (int)(Math.random() * n);
            int b = (int)(Math.random() * n);
            int t = arr[a];
            arr[a] = arr[b];
            arr[b] = t;
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

            //默认关闭断言，需要开启JVM参数-ea或者-enableassertions
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

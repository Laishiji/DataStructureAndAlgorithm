package linkedlist;

/**
 * @author: lsj
 * @date: 2021/1/24 11:29
 * Description: Test Case for LinkedList.
 */
public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1).add(2).add(3).add(4).add(5);
        list.set(100,2);
        System.out.println(list.toString());
        list.remove(2);
        list.removeFirst();
        list.removeLast();
        System.out.println(list.toString());
    }
}

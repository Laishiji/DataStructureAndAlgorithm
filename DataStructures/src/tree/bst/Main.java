package tree.bst;

/**
 * @author: lsj
 * @date: 2021/1/24 19:09
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        int[] nums = {5,3,6,2,4,8};
        //      5     //
        //     / \    //
        //    3   6   //
        //   / \   \  //
        //  2   4   8 //
        for (int number : nums) {
            binarySearchTree.add(number);
        }

//        binarySearchTree.preOrder();
//        System.out.println();
//        binarySearchTree.preOrderNonRecursive();
//        System.out.println();
//        binarySearchTree.levelOrder();
//        System.out.println(binarySearchTree.removeMinimum());
//        System.out.println(binarySearchTree.getMinimum());
//        System.out.println(binarySearchTree.removeMaximum());
        binarySearchTree.remove(3);
        binarySearchTree.preOrder();
    }
}

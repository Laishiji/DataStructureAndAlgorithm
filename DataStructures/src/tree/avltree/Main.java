package tree.avltree;

import java.util.Random;

/**
 * @author: lsj
 * @date: 2021/1/26 18:28
 */
public class Main {
    public static void main(String[] args) {
        int len = 10000;
        AVLTree<Integer> avlTree = new AVLTree<>();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            avlTree.add(random.nextInt(len));
        }
        System.out.println("is current tree balanced? "+ avlTree.isBalanced());
        System.out.println("is current tree binary search tree? " + avlTree.isBSTree());

        for (int i = 0; i < len/10; i++) {
            avlTree.remove(avlTree.getMaximum());
            avlTree.remove(avlTree.getMinimum());
        }
        System.out.println("is current tree balanced? "+ avlTree.isBalanced());
        System.out.println("is current tree binary search tree? " + avlTree.isBSTree());

    }
}

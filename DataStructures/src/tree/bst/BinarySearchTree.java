package tree.bst;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * @author: lsj
 * @date: 2021/1/24 16:52
 * Description: 二分搜素树。泛型E必须是可比较的，因此E须扩展Comparable接口。
 */
public class BinarySearchTree<E extends Comparable<E>> {

    private static class Node<E> {
        E item;
        Node<E> left, right;

        Node(E item){
            this.item = item;
            left = null;
            right = null;
        }
    }

    private transient Node<E> root;
    private transient int size;

    public BinarySearchTree(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 向BST中添加新元素
     * @param item 新元素
     */
    public void add(E item){
        root = add(root, item);
    }

    /**
     * 递归方法，向以node为根的BST中插入新元素。
     * @param node root node
     * @param item 新元素
     * @return 以node为根的BST
     */
    private Node<E> add(Node<E> node, E item) {
        //递归出口
        if(node == null){
            ++size;
            return new Node<>(item);
        }

        //划分子问题
        if(item.compareTo(node.item) < 0){
            node.left = add(node.left, item);
        }else if(item.compareTo(node.item) > 0) {
            node.right = add(node.right, item);
        }
        return node;
    }

    /**
     * 查看BST中是否包含item
     * @param item item
     * @return true or false
     */
    public boolean contains(E item){
        return contains(root, item);
    }

    /**
     * 查看以node为根的BST中是否包含元素item，递归方法
     * @param node node
     * @param item item
     * @return true or false
     */
    private boolean contains(Node<E> node, E item) {
        if(node == null){
            return false;
        }

        if(item.compareTo(node.item) == 0){
            return true;
        }else if(item.compareTo(node.item) > 0){
            return contains(node.right, item);
        }else{
            return contains(node.left, item);
        }
    }

    /**
     * 前序遍历BST。树的DFS。
     */
    public void preOrder(){
        preOrder(root);
    }

    /**
     * 前序遍历以node为根的BST，递归方法
     * @param node root node
     */
    private void preOrder(Node<E> node) {
        if(node == null){
            return;
        }

        System.out.print(node.item + " ");
        preOrder(node.left);
        //System.out.println(node.item);中序遍历，结果是升序的
        preOrder(node.right);
        //System.out.println(node.item);后序遍历
    }

    /**
     * 前序遍历的非递归写法，显式使用栈。
     */
    public void preOrderNonRecursive(){
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);

        //栈空了表示遍历完毕
        while(!stack.isEmpty()){
            Node<E> cur = stack.pop();
            System.out.print(cur.item + " ");

            //由于栈的LIFO特性，因此先序遍历需要先压右孩子进栈
            if(cur.right != null){
                stack.push(cur.right);
            }
            if(cur.left != null){
                stack.push(cur.left);
            }
        }
    }

    /**
     * 层序遍历，借助队列实现。
     * 二叉树的BFS。
     */
    public void levelOrder(){
        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.add(root);

        //由于队列的FIFO特性，因此层序遍历需要先使左孩子入队
        while(!queue.isEmpty()){
            Node<E> cur = queue.poll();
            System.out.print(cur.item + " ");

            if(cur.left != null){
                queue.add(cur.left);
            }
            if(cur.right != null){
                queue.add(cur.right);
            }
        }
    }

    public E getMinimum(){
        if(root == null){
            throw new RuntimeException("Tree is null.");
        }
        Node<E> cur = root;
        while (cur.left != null){
            cur = cur.left;
        }
        return cur.item;
    }

    /**
     * 删除BST的最小值所在节点，并返回最小值。
     * @return min
     */
    public E removeMinimum(){
        E result = getMinimum();
        root = removeMinimumNode(root);
        return result;
    }

    /**
     * 删除以node为根的BST中的最小节点。
     * @param node rootNode
     * @return 新的BST的根节点。
     */
    private Node<E> removeMinimumNode(Node<E> node){
        if(node.left == null){
            //重点！考虑最小值节点左孩子为空，右孩子不为空的情况
            Node<E> rightNode = node.right;
            //使node成为一个孤立节点，等待GC
            node.right = null;
            --size;
            return rightNode;
        }
        //将删除最小节点后的左子树挂接到root上
        node.left = removeMinimumNode(node.left);
        return node;
    }

    public E getMaximum(){
        if(root == null){
            throw new RuntimeException("Tree is null.");
        }
        Node<E> cur = root;
        while (cur.right != null){
            cur = cur.right;
        }
        return cur.item;
    }

    /**
     * 删除BST的最大值所在节点，并返回最大值。
     * @return max
     */
    public E removeMaximum(){
        E result = getMaximum();
        root = removeMaximumNode(root);
        return result;
    }

    /**
     * 删除以node为根的BST中的最大节点。
     * @param node rootNode
     * @return 新的BST的根节点。
     */
    private Node<E> removeMaximumNode(Node<E> node){
        if(node.right == null){
            //重点！考虑最大值节点右孩子为空，左孩子不为空的情况
            Node<E> leftNode = node.left;
            //使node成为一个孤立节点，等待GC
            node.left = null;
            --size;
            return leftNode;
        }
        node.right = removeMaximumNode(node.right);
        return node;
    }

    /**
     * 删除BST中值为e的节点
     * @param e value
     */
    public void remove(E e){
        root = remove(root, e);
    }

    /**
     * 删除以node为根的BST中值为e的节点，递归方法
     * @param node root
     * @param e value
     * @return 删除节点后新的BST的根
     */
    private Node<E> remove(Node<E> node, E e) {
        if(node == null){
            return null;
        }
        if(e.compareTo(node.item) < 0){
            node.left = remove(node.left, e);
            return node;
        }else if (e.compareTo(node.item) > 0){
            node.right = remove(node.right, e);
            return node;
        }else{
            // 删除节点
            //待删除节点左子树为空的情况
            if(node.left == null){
                Node<E> rightNode = node.right;
                //使node成为一个孤立节点，等待GC
                node.right = null;
                --size;
                return rightNode;
            }
            //待删除节点右子树为空的情况
            if(node.right == null){
                Node<E> leftNode = node.left;
                //使node成为一个孤立节点，等待GC
                node.left = null;
                --size;
                return leftNode;
            }
            //待删除节点左右子树均不为空的情况，找到比待删除节点大的最小节点，
            //即待删除节点右子树的最小节点，用该节点替代当前位置。
            //1. 定位右子树最小节点
            Node<E> succssorNode = node.right;
            while (succssorNode .left != null){
                succssorNode  = succssorNode.left;
            }
            //2. 删除右子树最小节点与右子树的连接，返回新的右子树并挂接到successor上
            succssorNode.right = removeMinimumNode(node.right);
            //3. successor的左子树就是待删除节点的左子树
            succssorNode.left = node.left;
            //删除node
            node.left = node.right = null;
            return succssorNode;
        }
    }

}

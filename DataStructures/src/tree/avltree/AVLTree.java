package tree.avltree;

import java.util.ArrayList;

/**
 * @author: lsj
 * @date: 2021/1/26 17:14
 * Description: AVLTree.
 */
public class AVLTree<E extends Comparable<E>>{

    private static class Node<E> {
        E item;
        Node<E> left, right;
        //记录节点的高度
        int height;

        Node(E item){
            this.item = item;
            left = null;
            right = null;
            //新添加节点均为叶子节点，默认高度为1
            height = 1;
        }
    }

    private transient Node<E> root;
    private transient int size;

    public AVLTree(){
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
     * 判断当前二叉树是否是一棵二分搜索树
     * @return true or false
     */
    public boolean isBSTree(){
        ArrayList<E> arrayList = new ArrayList<>();
        inOrder(root, arrayList);
        for(int i = 1; i < arrayList.size(); i++){
            if(arrayList.get(i-1).compareTo(arrayList.get(i)) > 0){
                return false;
            }
        }
        return true;
    }

    private void inOrder(Node<E> node, ArrayList<E> arrayList){
        if(node == null){
            return;
        }
        inOrder(node.left, arrayList);
        arrayList.add(node.item);
        inOrder(node.right,arrayList);
    }

    /**
     * 判断当前二叉树是否为平衡二叉树
     * @return true or false
     */
    public boolean isBalanced(){
        return isBalanced(root);
    }

    private boolean isBalanced(Node<E> node) {
        if(node == null){
            return true;
        }

        if(getBalanceFactor(node) > 1){
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

    /**
     * 获取节点的高度，避免每次获取时都要判空
     * @param node node
     * @return height
     */
    private int getHeight(Node<E> node){
        if(node == null){
            return 0;
        }
        return node.height;
    }

    /**
     * 获取节点平衡因子
     * @param node node
     * @return 平衡因子
     */
    private int getBalanceFactor(Node<E> node){
        if(node == null){
            return 0;
        }
        return getHeight(node.left)-getHeight(node.right);
    }

    /**
     * 向AVLTree中添加新元素
     * @param item 新元素
     */
    public void add(E item){
        root = add(root, item);
    }

    /**
     * 递归方法，向以node为根的AVLTree中插入新元素。
     * @param node root node
     * @param item 新元素
     * @return 以node为根的AVLTree
     */
    private Node<E> add(Node<E> node, E item) {
        if(node == null){
            ++size;
            return new Node<>(item);
        }

        if(item.compareTo(node.item) < 0){
            node.left = add(node.left, item);
        }else if(item.compareTo(node.item) > 0) {
            node.right = add(node.right, item);
        }

        //加入节点后，只有递归方法经过路径上的节点的平衡因子会改变，因此由于递归的特性，只需要向上沿着节点路径维护平衡性。
        //添加一个元素，递归方法经过的每一个节点的高度都需要给更新。注意：此处并不是简单的加1，因为有些节点的高度可能不会变化。
        node.height = Math.max(getHeight(node.left),getHeight(node.right)) + 1;

        //若当前节点的平衡因子绝对值大于1，则需要旋转。
        int balanceFactor = getBalanceFactor(node);

        //若当前节点平衡因子大于1且左孩子的平衡因子大于等于0，说明插入的元素在当前节点左侧的左侧(LL)，则需要对当前节点右旋。
        if(balanceFactor > 1 && getBalanceFactor(node.left) >= 0){
            return rightRotate(node);
        }
        //若当前节点平衡因子小于-1且右孩子的平衡因子小于等于0，说明插入的元素在当前节点右侧的右侧(RR)，需要左旋。
        else if(balanceFactor < -1 && getBalanceFactor(node.right) <= 0){
            return leftRotate(node);
        }
        //若当前节点平衡因子大于1且左孩子的平衡因子小于0，说明插入的元素在当前节点左侧的右侧(LR)，则需要先对左孩子左旋，再对当前节点右旋。
        else if(balanceFactor > 1 && getBalanceFactor(node.left) < 0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        //若当前节点平衡因子小于-1且右孩子的平衡因子大于0，说明插入的元素在当前节点右侧的左侧(RL)，需要先对右孩子右旋，再对当前节点左旋。
        else if(balanceFactor < -1 && getBalanceFactor(node.right) > 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * 右旋操作.参见avl-tree.md文档。
     * @param node 平衡因子大于1的节点
     * @return 右旋后子树的根节点，即node的左孩子
     */
    private Node<E> rightRotate(Node<E> node){
        //右旋
        Node<E> leftChild = node.left, rightChildOfLeftChild = leftChild.right;
        leftChild.right = node;
        node.left = rightChildOfLeftChild;
        //更新height
        node.height = Math.max(getHeight(node.left),getHeight(node.right)) + 1;
        leftChild.height = Math.max(getHeight(leftChild.left),getHeight(leftChild.right)) + 1;

        return leftChild;
    }

    /**
     * 左旋操作。
     * @param node 平衡因子小于-1的节点
     * @return 左旋后子树的根节点，即node的右孩子
     */
    private Node<E> leftRotate(Node<E> node) {
        //左旋
        Node<E> rightChild = node.right, leftChildOfRightChild = rightChild.left;
        rightChild.left = node;
        node.right = leftChildOfRightChild;
        //更新height
        node.height = Math.max(getHeight(node.left),getHeight(node.right)) + 1;
        rightChild.height = Math.max(getHeight(rightChild.left), getHeight(rightChild.right)) + 1;

        return rightChild;
    }

    /**
     * 查看AVLTree中是否包含item
     * @param item item
     * @return true or false
     */
    public boolean contains(E item){
        return contains(root, item);
    }

    /**
     * 查看以node为根的AVLTree中是否包含元素item，递归方法
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
     * 删除AVLTree中值为e的节点
     * @param e value
     */
    public void remove(E e){
        root = remove(root, e);
    }

    /**
     * 删除以node为根的AVLTree中值为e的节点，递归方法
     * @param node root
     * @param e value
     * @return 删除节点后新的AVLTree的根
     */
    private Node<E> remove(Node<E> node, E e) {
        if(node == null){
            return null;
        }

        Node<E> result;

        if(e.compareTo(node.item) < 0){
            node.left = remove(node.left, e);
            result = node;
        }else if (e.compareTo(node.item) > 0){
            node.right = remove(node.right, e);
            result = node;
        }else{
            // 删除节点
            //待删除节点左子树为空的情况
            if(node.left == null){
                Node<E> rightNode = node.right;
                //使node成为一个孤立节点，等待GC
                node.right = null;
                --size;
                result = rightNode;
            }
            //待删除节点右子树为空的情况
            else if(node.right == null){
                Node<E> leftNode = node.left;
                //使node成为一个孤立节点，等待GC
                node.left = null;
                --size;
                result = leftNode;
            }

            else{
                //待删除节点左右子树均不为空的情况，找到比待删除节点大的最小节点，
                //即待删除节点右子树的最小节点，用该节点替代当前位置。
                //1. 定位右子树最小节点
                Node<E> succssorNode = node.right;
                while (succssorNode .left != null){
                    succssorNode  = succssorNode.left;
                }
                //2. 删除右子树最小节点与右子树的连接，返回新的右子树并挂接到successor上
                succssorNode.right = remove(node.right, succssorNode.item);
                //3. successor的左子树就是待删除节点的左子树
                succssorNode.left = node.left;
                //删除node
                node.left = node.right = null;
                result =  succssorNode;
            }

        }

        if(result == null){
            return null;
        }

        //维护平衡性
        result.height = Math.max(getHeight(result.left), getHeight(result.right)) + 1;

        //若当前节点的平衡因子绝对值大于1，则需要旋转。
        int balanceFactor = getBalanceFactor(result);

        //若当前节点平衡因子大于1且左孩子的平衡因子大于等于0，说明插入的元素在当前节点左侧的左侧(LL)，则需要对当前节点右旋。
        if(balanceFactor > 1 && getBalanceFactor(result.left) >= 0){
            return rightRotate(result);
        }
        //若当前节点平衡因子小于-1且右孩子的平衡因子小于等于0，说明插入的元素在当前节点右侧的右侧(RR)，需要左旋。
        else if(balanceFactor < -1 && getBalanceFactor(result.right) <= 0){
            return leftRotate(result);
        }
        //若当前节点平衡因子大于1且左孩子的平衡因子小于0，说明插入的元素在当前节点左侧的右侧(LR)，则需要先对左孩子左旋，再对当前节点右旋。
        else if(balanceFactor > 1 && getBalanceFactor(result.left) < 0){
            result.left = leftRotate(result.left);
            return rightRotate(result);
        }
        //若当前节点平衡因子小于-1且右孩子的平衡因子大于0，说明插入的元素在当前节点右侧的左侧(RL)，需要先对右孩子右旋，再对当前节点左旋。
        else if(balanceFactor < -1 && getBalanceFactor(result.right) > 0){
            result.right = rightRotate(result.right);
            return leftRotate(result);
        }

        return result;
    }
}

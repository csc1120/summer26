/*
 * Course: CSC-1120
 * BinarySearchTree
 */
package wk8;

public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {
    // no duplicates, no null elements
    // all lower stuff goes left
    // all higher stuff goes rights
    //
    private int size;
    public BinarySearchTree() {
        super();
        this.size = 0;
    }

    public boolean add(E e) {
        return add(e, this.root);
    }

    private boolean add(E e, Node<E> node) {
        // base case
        if(node == null) {
            this.root = new Node<>(e);
            ++size;
            return true;
        }
        int compare = e.compareTo(node.data);
        if(compare == 0) {
            return false;
        }

        // recursive
        if(compare < 0) { // go left
            if(node.left != null) {
                return add(e, node.left);
            } else {
                node.left = new Node<>(e);
                ++size;
                return true;
            }
        } else { // greater than, go right
            if(node.right != null) {
                return add(e, node.right);
            } else {
                node.right = new Node<>(e);
                ++size;
                return true;
            }
        }
    }

    public boolean contains(E e) {
        return contains(e, this.root);
    }

    private boolean contains(E e, Node<E> node) {
        if(node == null) {
            return false;
        }
        int compare = e.compareTo(node.data);
        if(compare == 0) {
            return true;
        }
        // recursive
        if(compare < 0) {
            return contains(e, node.left);
        }
        return contains(e, node.right);
    }
}

/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk8;

public class BinaryTree<E> {
    private static class Node<E> {
        private E data;
        private Node<E> left;
        private Node<E> right;

        private Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return String.valueOf(this.data);
        }
    }

    private Node<E> root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) {
        this.root = new Node<>(data);
        this.root.left = left == null ? null : left.root;
        this.root.right = right == null ? null : right.root;
    }

    public BinaryTree(Node<E> root) {
        this.root = root;
    }

    public BinaryTree<E> getLeftSubTree() {
        return (this.root == null || this.root.left == null) ? null :
                new BinaryTree<>(this.root.left);
    }

    public BinaryTree<E> getRightSubTree() {
        return (this.root == null || this.root.right == null) ? null :
                new BinaryTree<>(this.root.right);
    }

    public boolean isLeaf() {
        return this.root != null
                && this.root.left == null
                && this.root.right == null;
    }
}

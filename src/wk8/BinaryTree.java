/*
 * Course: CSC-1120
 * Binary Tree
 * Name: Sean Jones
 */
package wk8;

import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * A generic binary tree data structure where each node contains a value and
 * references to left and right child nodes.
 * <p>
 * This implementation supports construction from subtrees, traversal
 * (pre-order, in-order, post-order), and a simple textual representation.
 *
 * @param <E> the type of data stored in the tree
 */
public class BinaryTree<E> {
    /**
     * A node within the binary tree containing a single data element and
     * references to its left and right children.
     *
     * @param <E> the type of data stored in the node
     */
    protected static class Node<E> {
        /**
         * The data stored in the node.
         */
        protected E data;
        /** Reference to the left child node, or null if none exists. */
        protected Node<E> left;
        /** Reference to the right child node, or null if none exists. */
        protected Node<E> right;

        /**
         * Constructs a node with the specified data and no children.
         *
         * @param data the data to store in the node
         */
        protected Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return String.valueOf(this.data);
        }
    }

    /** The root node of the tree, or null if the tree is empty. */
    protected Node<E> root;

    /**
     * Constructs an empty binary tree.
     */
    public BinaryTree() {
        this.root = null;
    }

    /**
     * Constructs a binary tree with the specified root data and subtrees.
     *
     * @param data  the data for the root node
     * @param left  the left subtree, or null if no left child
     * @param right the right subtree, or null if no right child
     */
    public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) {
        this.root = new Node<>(data);
        this.root.left = left == null ? null : left.root;
        this.root.right = right == null ? null : right.root;
    }

    /**
     * Constructs a binary tree from an existing root node.
     *
     * @param root the root node of the tree
     */
    protected BinaryTree(Node<E> root) {
        this.root = root;
    }

    /**
     * Returns the left subtree of this tree.
     *
     * @return the left subtree, or null if no left subtree exists
     */
    public BinaryTree<E> getLeftSubTree() {
        return this.root == null || this.root.left == null ? null
                : new BinaryTree<>(this.root.left);
    }

    /**
     * Returns the right subtree of this tree.
     *
     * @return the right subtree, or null if no right subtree exists
     */
    public BinaryTree<E> getRightSubTree() {
        return this.root == null || this.root.right == null ? null
                : new BinaryTree<>(this.root.right);
    }

    /**
     * Returns the data stored at the root of the tree.
     *
     * @return the root data, or null if the tree is empty
     */

    public E getData() {
        return this.root == null ? null : this.root.data;
    }

    /**
     * Determines whether this tree is a leaf.
     * <p>
     * A tree is considered a leaf if its root has no children.
     *
     * @return true if the tree is non-empty and its root has no children;
     *         false otherwise
     */
    public boolean isLeaf() {
        return this.root != null
                && this.root.left == null
                && this.root.right == null;
    }

    /**
     * Returns a string representation of the tree as a centered ASCII diagram.
     *
     * @return a string representation of the tree structure
     */
    @Override
    public String toString() {
        if(this.root == null) {
            return "null\n";
        }

        int height = this.height(this.root);
        int labelWidth = this.maxLabelWidth(this.root);
        int unit = labelWidth + 2;
        int width = (1 << height) * unit + labelWidth;
        int rows = height * 2 - 1;
        char[][] canvas = new char[rows][width];

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < width; c++) {
                canvas[r][c] = ' ';
            }
        }

        int rootX = width / 2;
        this.drawAscii(this.root, 0, rootX, height, unit, canvas);

        StringBuilder sb = new StringBuilder();
        for(char[] row : canvas) {
            int end = row.length - 1;
            while(end >= 0 && row[end] == ' ') {
                end--;
            }
            if(end >= 0) {
                sb.append(row, 0, end + 1);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Original preorder/null textual renderer kept for compatibility.
     *
     * @return preorder text representation including explicit null children
     */
    public String toStringOld() {
        StringBuilder sb = new StringBuilder();
        this.toStringOld(this.root, 1, sb);
        return sb.toString();
    }

    private void toStringOld(Node<E> node, int depth, StringBuilder sb) {
        // indent based on the depth of the node (1 space to the right per level)
        sb.append(" ".repeat(Math.max(0, depth - 1)));
        if(node == null) {
            sb.append("null\n");
        } else {
            sb.append(node).append("\n");
            this.toStringOld(node.left, depth + 1, sb);
            this.toStringOld(node.right, depth + 1, sb);
        }
    }

    private int height(Node<E> node) {
        if(node == null) {
            return 0;
        }
        return 1 + Math.max(this.height(node.left), this.height(node.right));
    }

    private int maxLabelWidth(Node<E> node) {
        if(node == null) {
            return 0;
        }
        int current = String.valueOf(node.data).length();
        int left = this.maxLabelWidth(node.left);
        int right = this.maxLabelWidth(node.right);
        return Math.max(current, Math.max(left, right));
    }

    private void drawAscii(Node<E> node, int depth, int x,
                           int height, int unit, char[][] canvas) {
        if(node != null) {
            int nodeRow = depth * 2;
            String label = String.valueOf(node.data);
            int start = x - (label.length() / 2);
            for (int i = 0; i < label.length(); i++) {
                int col = start + i;
                if (col >= 0 && col < canvas[nodeRow].length) {
                    canvas[nodeRow][col] = label.charAt(i);
                }
            }

            if (depth < height - 1) {
                int gap = (1 << (height - depth - 2)) * unit;
                int edgeRow = nodeRow + 1;
                if (node.left != null) {
                    int leftX = x - gap;
                    int leftEdge = x - (gap / 2);
                    if (edgeRow < canvas.length
                            && leftEdge >= 0
                            && leftEdge < canvas[edgeRow].length) {
                        canvas[edgeRow][leftEdge] = '/';
                    }
                    this.drawAscii(node.left, depth + 1, leftX, height, unit, canvas);
                }
                if (node.right != null) {
                    int rightX = x + gap;
                    int rightEdge = x + (gap / 2);
                    if (edgeRow < canvas.length
                            && rightEdge >= 0
                            && rightEdge < canvas[edgeRow].length) {
                        canvas[edgeRow][rightEdge] = '\\';
                    }
                    this.drawAscii(node.right, depth + 1, rightX, height, unit, canvas);
                }
            }
        }
    }

    /**
     * Performs a pre-order traversal of the tree.
     * <p>
     * The provided consumer is called for each node with its data and depth.
     *
     * @param consumer a function that processes each node's data and depth
     */
    public void preOrderTraversal(BiConsumer<E, Integer> consumer) {
        preOrderTraversal(consumer, 1, this.root);
    }

    private void preOrderTraversal(BiConsumer<E, Integer> consumer, int depth, Node<E> node) {
        if(node != null) {
            consumer.accept(node.data, depth);
            preOrderTraversal(consumer, depth + 1, node.left);
            preOrderTraversal(consumer, depth + 1, node.right);
        }
    }

    /**
     * Performs an in-order traversal of the tree.
     * <p>
     * The provided consumer is called for each node with its data and depth.
     *
     * @param consumer a function that processes each node's data and depth
     */
    public void inOrderTraversal(BiConsumer<E, Integer> consumer) {
        inOrderTraversal(consumer, 1, this.root);
    }

    private void inOrderTraversal(BiConsumer<E, Integer> consumer, int depth, Node<E> node) {
        if(node != null) {
            inOrderTraversal(consumer, depth + 1, node.left);
            consumer.accept(node.data, depth);
            inOrderTraversal(consumer, depth + 1, node.right);
        }
    }

    /**
     * Performs a post-order traversal of the tree.
     * <p>
     * The provided consumer is called for each node with its data and depth.
     *
     * @param consumer a function that processes each node's data and depth
     */
    public void postOrderTraversal(BiConsumer<E, Integer> consumer) {
        postOrderTraversal(consumer, 1, this.root);
    }

    private void postOrderTraversal(BiConsumer<E, Integer> consumer, int depth, Node<E> node) {
        if(node != null) {
            postOrderTraversal(consumer, depth + 1, node.left);
            postOrderTraversal(consumer, depth + 1, node.right);
            consumer.accept(node.data, depth);
        }
    }

    /**
     * Reads a binary tree from a {@link Scanner} using a pre-order format.
     * <p>
     * Each line represents a node's data. The string {@code "null"} indicates
     * the absence of a node. The method recursively constructs the tree.
     *
     * @param scan the scanner providing the input data
     * @return the constructed binary tree, or null if the input represents an empty tree
     */
    public static BinaryTree<String> readBinaryTree(Scanner scan) {
        String data = scan.nextLine().trim();
        if(data.equals("null")) {
            return null;
        } else {
            BinaryTree<String> leftTree = readBinaryTree(scan);
            BinaryTree<String> rightTree = readBinaryTree(scan);
            return new BinaryTree<>(data, leftTree, rightTree);
        }
    }
}

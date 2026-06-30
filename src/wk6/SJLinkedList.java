/*
 * Course: CSC-1120
 * ASSIGNMENT
 * CLASS
 * Name: Sean Jones
 * Last Updated:
 */
package wk6;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SJLinkedList<E> implements List<E> {
    // 4 different memory locations for a running program
    // stack - local variables
    // heap - objects
    // String pool - Strings
    // static - loaded before program runs
    private static class Node<E> {
        private E data;
        private Node<E> next;

        private Node(E data) {
            this(data, null);
        }

        private Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private int size;
    private Node<E> head;

    public SJLinkedList() {
        this.size = 0;
        this.head = null;
    }

    @Override
    public int size() {
        return this.size; // O(1)
    }

    @Override
    public boolean isEmpty() {
        return this.head == null; // O(1)
        // return this.size == 0;
    }

    @Override
    public boolean contains(Object o) { // O(n)
        // traverse through the list until
        // we find it, or we hit the end
        Node<E> current = this.head;
        for(int i = 0; i < this.size; ++i) {
            // did I find it?
            if(current.data.equals(o)) {
                // found it!
                return true;
            }
            // otherwise
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }
}

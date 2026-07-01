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
import java.util.Objects;

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
    public boolean add(E e) { // O(n)
        // if list is empty, head == null
        // make new head
        Node<E> node = new Node<>(e);
        if(this.isEmpty()) {
            this.head = node;
        } else {
            Node<E> current = this.head;
            // get to the end
            while(current.next != null) {
                current = current.next;
            }
            // then add
            current.next = node;
        }
        ++this.size;
        return true;
    }

    @Override
    public boolean remove(Object o) { // O(n)
        // find node to remove and the previous node
        Node<E> previous = null;
        Node<E> current = this.head;
        while(current != null && !Objects.equals(current.data, o)) {
            previous = current;
            current = current.next;
        }
        // change prev node's next reference
        if(current == null) { // not there
            return false;
        }
        // found it, remove it
        if(previous == null) { // if remove the head
            this.head = current.next;
        } else {
            previous.next = current.next;
        }
        --this.size;
        return true;
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
    public void clear() { // O(1)
        this.head = null;
        this.size = 0;
    }

    @Override
    public E get(int index) { // O(n)
        // valid? index >= 0 && size <
        if(index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
        // go to index
        Node<E> current = this.head;
        for(int i = 0; i < index; ++i) {
            current = current.next;
        }
        return current.data; // return the DATA, not the node
    }

    @Override
    public E set(int index, E element) { // O(n)
        // valid? index >= 0 && size <
        if(index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
        // go to index
        Node<E> current = this.head;
        for(int i = 0; i < index; ++i) {
            current = current.next;
        }
        E old = current.data;
        current.data = element;
        return old;
    }

    @Override
    public void add(int index, E element) { // O(n)
        if(index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
        // go to index
        Node<E> previous = this.head;
        for(int i = 0; i < index - 1; ++i) { // get to previous node
            previous = previous.next;
        }
        // if head, no previous
        if(index == 0) {
            this.head = new Node<>(element, this.head);
        } else {
            previous.next = new Node<>(element, previous.next);
        }
        ++this.size;
    }

    @Override
    public E remove(int index) { // O(n)
        if(index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
        // go to previous index
        Node<E> previous = this.head;
        for(int i = 0; i < index - 1; ++i) {
            previous = previous.next;
        }
        // if head, no previous
        E result = null;
        if(index == 0) {
            result = this.head.data;
            this.head = previous.next;
        } else {
            result = previous.next.data;
            previous.next = previous.next.next;
        }
        --this.size;
        return result;
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

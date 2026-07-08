///*
// * Course: CSC-1120
// * ASSIGNMENT
// * CLASS
// * Name: Sean Jones
// * Last Updated:
// */
//package wk7;
//
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.Objects;
//
///**
// * A simple implementation of a Single Linked List
// * @param <E> the element type stored in the List
// */
//public class SJDoubleLinkedList<E> implements List<E> {
//    /**
//     * Private static class to hold a single element of a single LinkedList.
//     * <p/>
//     * It is static so it will not store a reference to the containing class, as it never
//     * needs to access the contents of the SJLinkedList class.
//     *
//     * @param <E> the element type stored in the Node
//     */
//    private static class Node<E> {
//        private E data;
//        private Node<E> next;
//        private Node<E> previous;
//
//        /**
//         * A "Subordinate" constructor. It calls the "Master" constructor with a default
//         * null value for hte next reference.
//         *
//         * @param data the data to be stored in the Node
//         */
//        private Node(E data) {
//            this(data, null, null);
//        }
//
//        /**
//         * "Master" constructor. This is the one that will always be used to construct a new
//         * instance
//         * @param data the data to be stored in the Node
//         * @param next a reference to the next Node in the List
//         */
//        private Node(E data, Node<E> previous, Node<E> next) {
//            this.data = data;
//            this.next = next;
//            this.previous = previous;
//        }
//    }
//
//    private int size;
//    private Node<E> head;
//    private Node<E> tail;
//
//    /**
//     * No-param constructor that sets the sie to 0 and the head to null
//     */
//    public SJDoubleLinkedList() {
//        this.size = 0;
//        this.head = null;
//        this.tail = null;
//    }
//
//    /**
//     * Returns the number of elements in this list.  If this list contains
//     * more than {@code Integer.MAX_VALUE} elements, returns
//     * {@code Integer.MAX_VALUE}.
//     * <p/>
//     * Time complexity: O(1) - returns an instance variable value
//     *
//     * @return the number of elements in this list
//     */
//    @Override
//    public int size() {
//        return this.size;
//    }
//
//    /**
//     * Returns {@code true} if this list contains no elements.
//     * <p/>
//     * Time complexity - O(1) - Returns a single comparison
//     *
//     * @return {@code true} if this list contains no elements
//     */
//    @Override
//    public boolean isEmpty() {
//        return this.head == null;
//        // return this.size == 0;
//    }
//
//    /**
//     * Returns {@code true} if this list contains the specified element.
//     * More formally, returns {@code true} if and only if this list contains
//     * at least one element {@code e} such that
//     * {@code Objects.equals(o, e)}.
//     *  <p/>
//     *  Time complexity - O(n) - worst case will traverse the entire list and the value is not
//     *  present
//     *
//     * @param o element whose presence in this list is to be tested
//     * @return {@code true} if this list contains the specified element
//     * @throws ClassCastException   if the type of the specified element
//     *                              is incompatible with this list
//     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
//     * @throws NullPointerException if the specified element is null and this
//     *                              list does not permit null elements
//     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
//     */
//    @Override
//    public boolean contains(Object o) {
//        // traverse through the list until
//        // we find it, or we hit the end
//        Node<E> current = this.head;
//        for(int i = 0; i < this.size; ++i) {
//            // did I find it?
//            if(current.data.equals(o)) {
//                // found it!
//                return true;
//            }
//            // otherwise
//            current = current.next;
//        }
//        return false;
//    }
//
//    @Override
//    public Iterator<E> iterator() {
//        return null;
//    }
//
//    @Override
//    public Object[] toArray() {
//        Object[] result = new Object[this.size];
//        Node<E> current = this.head;
//        for(int i = 0; i < this.size; ++i) {
//            result[i] = current.data;
//            current = current.next;
//        }
//        return result;
//    }
//
//    @Override
//    public <T> T[] toArray(T[] a) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Appends the specified element to the end of this list (optional
//     * operation).
//     *
//     * <p>Lists that support this operation may place limitations on what
//     * elements may be added to this list.  In particular, some
//     * lists will refuse to add null elements, and others will impose
//     * restrictions on the type of elements that may be added.  List
//     * classes should clearly specify in their documentation any restrictions
//     * on what elements may be added.
//     *
//     * <p>
//     * Time complexity - O(n) - will always need to traverse the entire list to reach the
//     *                          last element and assign the last element's next to the new
//     *                          {@code Node}
//     *
//     * @param e element to be appended to this list
//     * @return {@code true} (as specified by {@link Collection#add})
//     * @throws UnsupportedOperationException if the {@code add} operation
//     *                                       is not supported by this list
//     * @throws ClassCastException            if the class of the specified element
//     *                                       prevents it from being added to this list
//     * @throws NullPointerException          if the specified element is null and this
//     *                                       list does not permit null elements
//     * @throws IllegalArgumentException      if some property of this element
//     *                                       prevents it from being added to this list
//     */
//    @Override
//    public boolean add(E e) { // O(n)
//        // if list is empty, head == null
//        // make new head
//        Node<E> node = new Node<>(e);
//        if(this.isEmpty()) {
//            this.head = node;
//        } else {
//            Node<E> current = this.head;
//            // get to the end
//            while(current.next != null) {
//                current = current.next;
//            }
//            // then add
//            current.next = node;
//        }
//        ++this.size;
//        return true;
//    }
//
//    /**
//     * Removes the first occurrence of the specified element from this list,
//     * if it is present (optional operation).  If this list does not contain
//     * the element, it is unchanged.  More formally, removes the element with
//     * the lowest index {@code i} such that
//     * {@code Objects.equals(o, get(i))}
//     * (if such an element exists).  Returns {@code true} if this list
//     * contained the specified element (or equivalently, if this list changed
//     * as a result of the call).
//     * <p/>
//     * Time complexity - O(n) - Like {@code contains(Object o)} it must find the element to remove
//     *                          which in the worst case is traversing the entire list and not
//     *                          finding the element
//     *
//     * @param o element to be removed from this list, if present
//     * @return {@code true} if this list contained the specified element
//     * @throws ClassCastException    if the type of the specified element
//     *                               is incompatible with this list
//     *                               (<a href="Collection.html#optional-restrictions">optional</a>)
//     * @throws NullPointerException  if the specified element is null and this
//     *                               list does not permit null elements
//     *                               (<a href="Collection.html#optional-restrictions">optional</a>)
//     * @throws UnsupportedOperationException if the {@code remove} operation
//     *                                       is not supported by this list
//     */
//    @Override
//    public boolean remove(Object o) {
//        // find node to remove and the previous node
//        Node<E> previous = null;
//        Node<E> current = this.head;
//        while(current != null && !Objects.equals(current.data, o)) {
//            previous = current;
//            current = current.next;
//        }
//        // change prev node's next reference
//        if(current == null) { // not there
//            return false;
//        }
//        // found it, remove it
//        if(previous == null) { // if remove the head
//            this.head = current.next;
//        } else {
//            previous.next = current.next;
//        }
//        --this.size;
//        return true;
//    }
//
//    @Override
//    public boolean containsAll(Collection<?> c) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean addAll(Collection<? extends E> c) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean addAll(int index, Collection<? extends E> c) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean removeAll(Collection<?> c) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean retainAll(Collection<?> c) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Removes all the elements from this list (optional operation).
//     * The list will be empty after this call returns.
//     * <p/>
//     * Time complexity - O(1) - Set two variables to default starting values
//     *
//     * @throws UnsupportedOperationException if the {@code clear} operation
//     *                                       is not supported by this list
//     */
//    @Override
//    public void clear() {
//        this.head = null;
//        this.size = 0;
//    }
//
//    /**
//     * Returns the element at the specified position in this list.
//     * <p/>
//     * Time complexity - O(n) - worst case must traverse to the end of the list
//     *
//     * @param index index of the element to return
//     * @return the element at the specified position in this list
//     * @throws IndexOutOfBoundsException if the index is out of range
//     *                                   ({@code index < 0 || index >= size()})
//     */
//    @Override
//    public E get(int index) {
//        // valid? index >= 0 && size <
//        validateIndex(index, index >= this.size);
//        // go to index
//        Node<E> current = this.head;
//        for(int i = 0; i < index; ++i) {
//            current = current.next;
//        }
//        return current.data; // return the DATA, not the node
//    }
//
//    private void validateIndex(int lowerIndex, boolean upperIndex) {
//        if (lowerIndex < 0 || upperIndex) {
//            throw new IndexOutOfBoundsException("Index: " + lowerIndex + ", Size: " + this.size);
//        }
//    }
//
//    /**
//     * Replaces the element at the specified position in this list with the
//     * specified element (optional operation).
//     * <p/>
//     * Time complexity - O(n) - worst case must traverse to the end of the list
//     *
//     * @param index   index of the element to replace
//     * @param element element to be stored at the specified position
//     * @return the element previously at the specified position
//     * @throws UnsupportedOperationException if the {@code set} operation
//     *                                       is not supported by this list
//     * @throws ClassCastException            if the class of the specified element
//     *                                       prevents it from being added to this list
//     * @throws NullPointerException          if the specified element is null and
//     *                                       this list does not permit null elements
//     * @throws IllegalArgumentException      if some property of the specified
//     *                                       element prevents it from being added to this list
//     * @throws IndexOutOfBoundsException     if the index is out of range
//     *                                       ({@code index < 0 || index >= size()})
//     */
//    @Override
//    public E set(int index, E element) {
//        // valid? index >= 0 && size <
//        validateIndex(index, index >= this.size);
//        // go to index
//        Node<E> current = this.head;
//        for(int i = 0; i < index; ++i) {
//            current = current.next;
//        }
//        E old = current.data;
//        current.data = element;
//        return old;
//    }
//
//    /**
//     * Inserts the specified element at the specified position in this list
//     * (optional operation).  Shifts the element currently at that position
//     * (if any) and any subsequent elements to the right (adds one to their
//     * indices).
//     * <p/>
//     * Time complexity - O(n) - worst case must traverse to the end of the list
//     *
//     * @param index   index at which the specified element is to be inserted
//     * @param element element to be inserted
//     * @throws UnsupportedOperationException if the {@code add} operation
//     *                                       is not supported by this list
//     * @throws ClassCastException            if the class of the specified element
//     *                                       prevents it from being added to this list
//     * @throws NullPointerException          if the specified element is null and
//     *                                       this list does not permit null elements
//     * @throws IllegalArgumentException      if some property of the specified
//     *                                       element prevents it from being added to this list
//     * @throws IndexOutOfBoundsException     if the index is out of range
//     *                                       ({@code index < 0 || index > size()})
//     */
//    @Override
//    public void add(int index, E element) {
//        validateIndex(index, index > this.size);
//        // go to index
//        Node<E> previous = this.head;
//        for(int i = 0; i < index - 1; ++i) { // get to previous node
//            previous = previous.next;
//        }
//        // if head, no previous
//        if(index == 0) {
//            this.head = new Node<>(element, this.head);
//        } else {
//            previous.next = new Node<>(element, previous.next);
//        }
//        ++this.size;
//    }
//
//    /**
//     * Removes the element at the specified position in this list (optional
//     * operation).  Shifts any subsequent elements to the left (subtracts one
//     * from their indices).  Returns the element that was removed from the
//     * list.
//     * <p/>
//     * Time complexity - O(n) - worst case must traverse to the end of the list
//     *
//     * @param index the index of the element to be removed
//     * @return the element previously at the specified position
//     * @throws UnsupportedOperationException if the {@code remove} operation
//     *                                       is not supported by this list
//     * @throws IndexOutOfBoundsException     if the index is out of range
//     *                                       ({@code index < 0 || index >= size()})
//     */
//    @Override
//    public E remove(int index) {
//        validateIndex(index, index >= this.size);
//        // go to previous index
//        Node<E> previous = this.head;
//        for(int i = 0; i < index - 1; ++i) {
//            previous = previous.next;
//        }
//        // if head, no previous
//        E result;
//        if(index == 0) {
//            result = this.head.data;
//            this.head = previous.next;
//        } else {
//            result = previous.next.data;
//            previous.next = previous.next.next;
//        }
//        --this.size;
//        return result;
//    }
//
//    /**
//     * Returns the index of the first occurrence of the specified element
//     * in this list, or -1 if this list does not contain the element.
//     * More formally, returns the lowest index {@code i} such that
//     * {@code Objects.equals(o, get(i))},
//     * or -1 if there is no such index.
//     * <p/>
//     * Time complexity - O(n) - worst case must traverse to the end of the list
//     *
//     * @param o element to search for
//     * @return the index of the first occurrence of the specified element in
//     * this list, or -1 if this list does not contain the element
//     * @throws ClassCastException   if the type of the specified element
//     *                              is incompatible with this list
//     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
//     * @throws NullPointerException if the specified element is null and this
//     *                              list does not permit null elements
//     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
//     */
//    @Override
//    public int indexOf(Object o) {
//        Node<E> current = this.head;
//        for(int i = 0; i < this.size; ++i) {
//            if(Objects.equals(current.data, o)) {
//                return i;
//            }
//            current = current.next;
//        }
//        return -1;
//    }
//
//    /**
//     * Returns the index of the last occurrence of the specified element
//     * in this list, or -1 if this list does not contain the element.
//     * More formally, returns the highest index {@code i} such that
//     * {@code Objects.equals(o, get(i))},
//     * or -1 if there is no such index.
//     * <p/>
//     * Time complexity - O(n) - will always traverse to the end of the list
//     *
//     * @param o element to search for
//     * @return the index of the last occurrence of the specified element in
//     * this list, or -1 if this list does not contain the element
//     * @throws ClassCastException   if the type of the specified element
//     *                              is incompatible with this list
//     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
//     * @throws NullPointerException if the specified element is null and this
//     *                              list does not permit null elements
//     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
//     */
//    @Override
//    public int lastIndexOf(Object o) {
//        Node<E> current = this.head;
//        int result = -1;
//        for(int i = 0; i < this.size; ++i) {
//            if(Objects.equals(current.data, o)) {
//                result = i;
//            }
//            current = current.next;
//        }
//        return result;
//    }
//
//    @Override
//    public ListIterator<E> listIterator() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ListIterator<E> listIterator(int index) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public List<E> subList(int fromIndex, int toIndex) {
//        throw new UnsupportedOperationException();
//    }
//}

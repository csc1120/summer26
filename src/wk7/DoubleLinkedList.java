/*
 * Course: CSC-1120
 * Double Linked List and Iterator
 * DoubleLinkedList
 * Name: Sean Jones
 */
package wk7;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 * A full implementation of a LinkedList, mimicking the java.util.LinkedList, but without some
 * of the additional complications, such as co-modification checks. When possible, the default
 * implementations of the interfaces are used (and thus not implemented)
 *
 * @param <E> the element type stored in the List.
 */
public class DoubleLinkedList<E> implements List<E>, Serializable, Cloneable, Deque<E>, Queue<E> {
    private static final class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        private Node(E data) {
            this(data, null, null);
        }

        private Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private final class ListIter implements ListIterator<E> {
        private Node<E> next;
        private Node<E> lastReturned;
        private int nextIndex;

        private ListIter(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException();
            }

            if (index == size) {
                this.next = null;
            } else {
                this.next = getNode(index);
            }

            this.nextIndex = index;
            this.lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return this.nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.next;
            this.next = this.next.next;
            ++this.nextIndex;

            return this.lastReturned.data;
        }

        @Override
        public boolean hasPrevious() {
            return this.nextIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (this.next == null) {
                this.next = tail;
            } else {
                this.next = this.next.prev;
            }
            this.lastReturned = this.next;
            --this.nextIndex;
            return this.lastReturned.data;
        }

        @Override
        public int nextIndex() {
            return this.nextIndex;
        }

        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }

        @Override
        public void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }

            final Node<E> nodeToRemove = this.lastReturned;

            if (nodeToRemove == this.next) {
                this.next = this.next.next;
            } else {
                --this.nextIndex;
            }

            removeNode(nodeToRemove);
            this.lastReturned = null;
        }

        @Override
        public void set(E e) {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            this.lastReturned.data = e;
        }

        @Override
        public void add(E e) {
            DoubleLinkedList.this.add(this.nextIndex, e);
            ++this.nextIndex;
            this.lastReturned = null;
        }
    }

    private final class SubList extends AbstractList<E> {
        private final DoubleLinkedList<E> parent;
        private final int offset;
        private int subSize;

        private SubList(int fromIndex, int toIndex) {
            this.parent = DoubleLinkedList.this;
            this.offset = fromIndex;
            this.subSize = toIndex - fromIndex;
        }

        @Override
        public E get(int index) {
            rangeCheck(index);
            return parent.get(index + this.offset);
        }
        @Override
        public int size() {
            return this.subSize;
        }

        @Override
        public E set(int index, E element) {
            rangeCheck(index);
            return this.parent.set(index + this.offset, element);
        }

        @Override
        public void add(int index, E element) {
            rangeCheckForAdd(index);
            this.parent.add(index + this.offset, element);
            ++this.subSize;
        }

        @Override
        public E remove(int index) {
            rangeCheck(index);
            E result = parent.remove(index + offset);
            subSize--;
            return result;
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= subSize) {
                throw new IndexOutOfBoundsException();
            }
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > subSize) {
                throw new IndexOutOfBoundsException();
            }
        }
    }

    private final class DescendingIterator implements Iterator<E> {
        private Node<E> nextNode;
        private Node<E> lastReturned;
        private boolean canRemove;

        private DescendingIterator() {
            this.nextNode = tail;
            this.lastReturned = null;
            this.canRemove = false;
        }

        @Override
        public boolean hasNext() {
            return this.nextNode != null;
        }

        @Override
        public E next() {
            if (this.nextNode == null) {
                throw new NoSuchElementException();
            }

            this.lastReturned = this.nextNode;
            this.nextNode = this.nextNode.prev;
            this.canRemove = true;

            return this.lastReturned.data;
        }

        @Override
        public void remove() {
            if (!this.canRemove) {
                throw new IllegalStateException();
            }

            DoubleLinkedList.this.removeNode(this.lastReturned);
            this.lastReturned = null;
            this.canRemove = false;
        }
    }

    private final class ReversedView extends AbstractList<E> implements Deque<E> {
        private int mapElementIndex(int reversedIndex) {
            int n = DoubleLinkedList.this.size();
            int last = n - 1;
            return last - reversedIndex;
        }

        private int mapPositionIndex(int reversedPosition) {
            int n = DoubleLinkedList.this.size();
            return n - reversedPosition;
        }

        private void validateElementIndex(int index) {
            int n = DoubleLinkedList.this.size();
            if (index < 0 || index >= n) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
            }
        }

        private void validatePositionIndex(int index) {
            int n = DoubleLinkedList.this.size();
            if (index < 0 || index > n) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
            }
        }

        // ---- List view ----

        @Override
        public int size() {
            return DoubleLinkedList.this.size();
        }

        @Override
        public E get(int index) {
            validateElementIndex(index);
            return DoubleLinkedList.this.get(mapElementIndex(index));
        }

        @Override
        public E set(int index, E element) {
            validateElementIndex(index);
            return DoubleLinkedList.this.set(mapElementIndex(index), element);
        }

        @Override
        public void add(int index, E element) {
            validatePositionIndex(index);
            DoubleLinkedList.this.add(mapPositionIndex(index), element);
        }

        @Override
        public E remove(int index) {
            validateElementIndex(index);
            return DoubleLinkedList.this.remove(mapElementIndex(index));
        }

        @Override
        public @NotNull Iterator<E> iterator() {
            // Forward iteration of reversed view = backward iteration of backing list
            return DoubleLinkedList.this.descendingIterator();
        }

        @Override
        public @NotNull ListIterator<E> listIterator(int index) {
            validatePositionIndex(index);
            return new ReversedListIterator(index);
        }

        @Override
        public DoubleLinkedList<E> reversed() {
            return DoubleLinkedList.this;
        }

        private final class ReversedListIterator implements ListIterator<E> {
            private final ListIterator<E> backing;
            private int cursor;
            private boolean canModify;

            private ReversedListIterator(int startIndex) {
                final int n = DoubleLinkedList.this.size();
                final int backingStart = n - startIndex;
                this.backing = DoubleLinkedList.this.listIterator(backingStart);
                this.cursor = startIndex;
                this.canModify = false;
            }

            @Override
            public boolean hasNext() {
                return this.backing.hasPrevious();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                ++this.cursor;
                this.canModify = true;
                return this.backing.previous();
            }

            @Override
            public boolean hasPrevious() {
                return this.backing.hasNext();
            }

            @Override
            public E previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                --this.cursor;
                this.canModify = true;
                return this.backing.next();
            }

            @Override
            public int nextIndex() {
                return cursor;
            }

            @Override
            public int previousIndex() {
                final int one = 1;
                return this.cursor - one;
            }

            @Override
            public void remove() {
                if (!this.canModify) {
                    throw new IllegalStateException();
                }
                this.backing.remove();
                this.canModify = false;
            }

            @Override
            public void set(E e) {
                if (!this.canModify) {
                    throw new IllegalStateException();
                }
                this.backing.set(e);
            }

            @Override
            public void add(E e) {
                this.backing.add(e);
                ++this.cursor;
                this.canModify = false;
            }
        }

        // ---- Deque view (swap front/back) ----

        @Override
        public void addFirst(E e) {
            DoubleLinkedList.this.addLast(e);
        }

        @Override
        public void addLast(E e) {
            DoubleLinkedList.this.addFirst(e);
        }

        @Override
        public boolean offerFirst(E e) {
            return DoubleLinkedList.this.offerLast(e);
        }

        @Override
        public boolean offerLast(E e) {
            return DoubleLinkedList.this.offerFirst(e);
        }

        @Override
        public E removeFirst() {
            return DoubleLinkedList.this.removeLast();
        }

        @Override
        public E removeLast() {
            return DoubleLinkedList.this.removeFirst();
        }

        @Override
        public E pollFirst() {
            return DoubleLinkedList.this.pollLast();
        }

        @Override
        public E pollLast() {
            return DoubleLinkedList.this.pollFirst();
        }

        @Override
        public E getFirst() {
            return DoubleLinkedList.this.getLast();
        }

        @Override
        public E getLast() {
            return DoubleLinkedList.this.getFirst();
        }

        @Override
        public E peekFirst() {
            return DoubleLinkedList.this.peekLast();
        }

        @Override
        public E peekLast() {
            return DoubleLinkedList.this.peekFirst();
        }

        @Override
        public boolean removeFirstOccurrence(Object o) {
            return DoubleLinkedList.this.removeLastOccurrence(o);
        }

        @Override
        public boolean removeLastOccurrence(Object o) {
            return DoubleLinkedList.this.removeFirstOccurrence(o);
        }

        @Override
        public boolean offer(E e) {
            // offer adds to tail; reversed tail == backing head
            return offerLast(e);
        }

        @Override
        public E remove() {
            return removeFirst();
        }

        @Override
        public E poll() {
            return pollFirst();
        }

        @Override
        public E element() {
            return getFirst();
        }

        @Override
        public E peek() {
            return peekFirst();
        }

        @Override
        public void push(E e) {
            addFirst(e);
        }

        @Override
        public E pop() {
            return removeFirst();
        }

        @Override
        public @NotNull Iterator<E> descendingIterator() {
            // Descending of reversed view = forward of backing list
            return DoubleLinkedList.this.iterator();
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * A no-param constructor that sets the size to 0 and the head and tail to null
     */
    public DoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> current = this.head;
        while (current != null) {
            if (Objects.equals(current.data, o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public @NotNull Iterator<E> iterator() {
        return new ListIter(0);
    }

    @Override
    public Object @NotNull [] toArray() {
        Node<E> current = this.head;
        final Object[] result = new Object[this.size];
        for(int i = 0; i < size; i++) {
            result[i] = current.data;
            current = current.next;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T @NotNull [] toArray(T @NotNull [] a) {
        Class<?> componentType = a.getClass().getComponentType();
        T[] result = a;
        if (a.length < this.size) {
            result = (T[]) Array.newInstance(componentType, this.size);
        }

        Node<E> current = this.head;
        for(int i = 0; i < this.size; i++) {
            result[i] = (T) current.data;
            current = current.next;
        }
        if(result.length > this.size) {
            result[size] = null;
        }

        return result;
    }

    @Override
    public boolean add(E e) {
        if(this.head == null) {
            this.head = new Node<>(e);
            this.tail = this.head;
        } else {
            this.tail.next = new Node<>(e, this.tail, null);
            this.tail = this.tail.next;
        }
        ++this.size;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstMatching(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for(Object o : c) {
            if(!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        boolean changed = false;
        for(E e : c) {
            this.add(e);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        validateAddIndex(index);
        boolean changed = false;
        for(E e : c) {
            this.add(index++, e);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return bulkRemove(c, true);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return bulkRemove(c, false);
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        Node<E> target = getNode(index);
        return target.data;
    }

    @Override
    public E set(int index, E element) {
        validateIndex(index);
        Node<E> target = getNode(index);
        E oldData = target.data;
        target.data = element;
        return oldData;
    }

    @Override
    public void add(int index, E element) {
        validateAddIndex(index);

        if (index == 0) {
            Node<E> oldHead = this.head;
            Node<E> newNode = new Node<>(element, null, oldHead);
            this.head = newNode;

            if (oldHead == null) {
                this.tail = newNode;
            } else {
                oldHead.prev = newNode;
            }
        } else if (index == this.size) {
            Node<E> oldTail = tail;
            Node<E> newNode = new Node<>(element, oldTail, null);
            tail = newNode;

            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
        } else {
            Node<E> current = getNode(index);
            Node<E> previous = current.prev;

            Node<E> newNode = new Node<>(element, previous, current);
            previous.next = newNode;
            current.prev = newNode;
        }
        ++this.size;
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        Node<E> current = getNode(index);
        E result = current.data;
        removeNode(current);
        return result;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> current = this.head;
        for(int i = 0; i < size; ++i) {
            if (Objects.equals(current.data, o)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<E> current = this.tail;
        for(int i = size - 1; i >= 0; --i) {
            if (Objects.equals(current.data, o)) {
                return i;
            }
            current = current.prev;
        }
        return -1;
    }

    @Override
    public @NotNull ListIterator<E> listIterator() {
        return new ListIter(0);
    }

    @Override
    public @NotNull ListIterator<E> listIterator(int index) {
        return new ListIter(index);
    }

    @Override
    public @NotNull List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        return new SubList(fromIndex, toIndex);
    }

    @Override
    public boolean offerFirst(E e) {
        this.add(0, e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        this.add(e);
        return true;
    }

    @Override
    public E pollFirst() {
        if (this.head == null) {
            return null;
        }
        E value = this.head.data;
        removeNode(this.head);
        return value;
    }

    @Override
    public E pollLast() {
        if (this.tail == null) {
            return null;
        }
        E value = this.tail.data;
        removeNode(this.tail);
        return value;
    }

    @Override
    public E peekFirst() {
        return (this.head == null) ? null : this.head.data;
    }

    @Override
    public E peekLast() {
        return (this.tail == null) ? null : this.tail.data;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return removeFirstMatching(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<E> current = this.tail;

        while (current != null) {
            if (Objects.equals(current.data, o)) {
                removeNode(current);
                return true;
            }
            current = current.prev;
        }

        return false;
    }

    @Override
    public boolean offer(E e) {
        return this.add(e);
    }

    @Override
    public E remove() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return removeFirst();
    }

    @Override
    public E poll() {
        if(isEmpty()) {
            return null;
        }
        return this.removeFirst();
    }

    @Override
    public E element() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return this.head.data;
    }

    @Override
    public E peek() {
        return (this.head == null) ? null : head.data;
    }

    @Override
    public void push(E e) {
        this.add(0, e);
    }

    @Override
    public E pop() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return this.removeFirst();
    }

    @Override
    public @NotNull Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    @Override
    public void addFirst(E e) {
        this.add(0, e);
    }

    @Override
    public void addLast(E e) {
        this.add(e);
    }

    @Override
    public E getFirst() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return this.head.data;
    }

    @Override
    public E getLast() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return this.tail.data;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return this.remove(0);
    }

    @Override
    public E removeLast() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return this.remove(this.size - 1);
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    @Override
    public ReversedView reversed() {
        return new ReversedView();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) { // is it the same memory location?
            return true;
        }
        if(!(o instanceof List<?> other)) { // are they both Lists (and neither are null)?
            return false;
        }

        if(other.size() != this.size()) { // are they the same size?
            return false;
        }
        // compare all the elements
        Iterator<E> it1 = this.iterator();
        Iterator<?> it2 = other.iterator();
        while(it1.hasNext() && it2.hasNext()) {
            if(!Objects.equals(it1.next(), it2.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;
        for (E e : this) {
            hashCode = prime * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DoubleLinkedList<E> clone() {
        try {
            DoubleLinkedList<E> clone = (DoubleLinkedList<E>) super.clone();
            clone.head = null;
            clone.tail = null;
            clone.size = 0;

            Node<E> current = this.head;
            while(current != null) {
                clone.add(current.data);
                current = current.next;
            }

            return clone;
        } catch(CloneNotSupportedException e) {
            throw new AssertionError(); // should not happen, we are defining clone here
        }
    }

    private void validateAddIndex(int index) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void validateIndex(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<E> getNode(int index) {
        final int lastIndex = this.size - 1;
        final int midpoint = this.size / 2;

        Node<E> current;

        if (index <= midpoint) {
            current = this.head;
            for (int i = 0; i < index; ++i) {
                current = current.next;
            }
        } else {
            current = this.tail;
            for (int i = lastIndex; i > index; --i) {
                current = current.prev;
            }
        }

        return current;
    }

    private void removeNode(Node<E> node) {
        final Node<E> prevNode = node.prev;
        final Node<E> nextNode = node.next;

        if (prevNode == null) {
            this.head = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            this.tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }

        node.prev = null;
        node.next = null;
        node.data = null;

        --this.size;
    }

    private boolean removeFirstMatching(Object o) {
        Node<E> current = this.head;

        while (current != null) {
            if (Objects.equals(current.data, o)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    private boolean bulkRemove(Collection<?> c, boolean removeIfContained) {
        if (c == null) {
            throw new NullPointerException("Collection must not be null");
        }
        final Collection<?> lookup = (c instanceof Set<?>)
                ? c
                : new HashSet<>(c);
        boolean changed = false;
        Node<E> current = head;
        while (current != null) {
            final Node<E> next = current.next;
            final boolean contained = lookup.contains(current.data);
            final boolean shouldRemove = removeIfContained == contained;

            if (shouldRemove) {
                removeNode(current);
                changed = true;
            }

            current = next;
        }
        return changed;
    }
}

/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk7;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SJQueue<E> implements Queue<E> {
    private final LinkedList<E> data;

    public SJQueue() {
        this.data = new LinkedList<>();
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public boolean offer(E e) {
        return this.data.offer(e);
    }

    @Override
    public boolean add(E e) {
        return this.data.add(e);
    }

    @Override
    public E poll() {
        if(this.data.isEmpty()) {
            return null;
        }
        return this.data.poll();
    }

    @Override
    public E remove() {
        if(this.data.isEmpty()) {
            throw new NoSuchElementException("Empty queue");
        }
        return this.data.removeFirst();
    }

    @Override
    public E peek() {
        if(this.data.isEmpty()) {
            return null;
        }
        return this.data.peek();
    }

    @Override
    public E element() {
        if(this.data.isEmpty()) {
            throw new NoSuchElementException("Empty queue");
        }
        return this.data.element();
    }
}

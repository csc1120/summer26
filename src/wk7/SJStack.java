/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk7;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class SJStack<E> implements Stack<E> {
    private final LinkedList<E> data;

    public SJStack() {
        this.data = new LinkedList<>();
    }

    @Override
    public boolean isEmpty() { // O(1)
        return this.data.isEmpty();
    }

    @Override
    public E push(E e) { // O(1)
        this.data.addFirst(e);
        return e;
    }

    @Override
    public E pop() throws EmptyStackException { // O(1)
        if(this.data.isEmpty()) {
            throw new EmptyStackException();
        }
        return this.data.removeFirst();
    }

    @Override
    public E peek() throws EmptyStackException { // O(1)
        if(this.data.isEmpty()) {
            throw new EmptyStackException();
        }
        return this.data.getFirst();
    }
}

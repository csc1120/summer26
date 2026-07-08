package wk7;

import java.util.EmptyStackException;

public interface Stack<E> {
    boolean isEmpty();
    E push(E e);
    E pop() throws EmptyStackException;
    E peek() throws EmptyStackException;
}


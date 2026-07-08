package wk7;

public interface Queue<E> {
    boolean isEmpty();
    boolean offer(E e);
    boolean add(E e);
    E poll();
    E remove();
    E peek();
    E element();
}

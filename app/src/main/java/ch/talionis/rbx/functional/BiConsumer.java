package ch.talionis.rbx.functional;

public interface BiConsumer<T, E> {
    void accept(T t, E e);
}
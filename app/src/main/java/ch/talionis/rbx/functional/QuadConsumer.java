package ch.talionis.rbx.functional;

public interface QuadConsumer<T, E, I, K> {
    void accept(T t, E e, I i, K k);
}

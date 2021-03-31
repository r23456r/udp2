package com.bat.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 通用的 Builder 模式构建器
 *
 * 在属性过多的情况下，需要在构造函数中一次性传递所有需要初始化的参数，并且可能会根据需求override多个不同参数组合的构造函数。比较麻烦。
 *
 * 如果是创建一个无参的空对象，通过setter方法，可以在任何时间和地方可以为对象属性重新赋值，
 *
 * 可能导致程序上下文对象的属性出现不一致的情况，这在序列化或者多线程的情境下会因为对象不一致而产生bug隐患。
 * @author: CipherCui
 * @since 2019/8/29
 */
public class Builder<T> {
    private final Supplier<T> instantiator;
    private List<Consumer<T>> modifiers = new ArrayList<>();

    public Builder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> Builder<T> of(Supplier<T> instantiator) {
        return new Builder<>(instantiator);
    }

    public <P1> Builder<T> with(Consumer1<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        modifiers.add(c);
        return this;
    }

    public <P1, P2> Builder<T> with(Consumer2<T, P1, P2> consumer, P1 p1, P2 p2) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2);
        modifiers.add(c);
        return this;
    }

    public <P1, P2, P3> Builder<T> with(Consumer3<T, P1, P2, P3> consumer, P1 p1, P2 p2, P3 p3) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2, p3);
        modifiers.add(c);
        return this;
    }

    public T build() {
        T value = instantiator.get();
        modifiers.forEach(modifier -> modifier.accept(value));
        modifiers.clear();
        return value;
    }

    /**
     * 1 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer1<T, P1> {
        void accept(T t, P1 p1);
    }

    /**
     * 2 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer2<T, P1, P2> {
        void accept(T t, P1 p1, P2 p2);
    }

    /**
     * 3 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer3<T, P1, P2, P3> {
        void accept(T t, P1 p1, P2 p2, P3 p3);
    }
}
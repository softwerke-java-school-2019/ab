package ru.softwerke.rofleksey.app2019.util;

import java.util.function.Supplier;

public abstract class LazySupplier<T> implements Supplier<T> {
    T value;

    @Override
    public T get() {
        if (value == null) {
            value = init();
        }
        return value;
    }

    abstract T init();
}

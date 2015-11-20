package org.yu55.yagga.util.mockito;

public abstract class MockBehavior<T> {

    protected T mock;

    public T get() {
        return mock;
    }
}

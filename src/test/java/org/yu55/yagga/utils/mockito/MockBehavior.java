package org.yu55.yagga.utils.mockito;

public abstract class MockBehavior<T> {

    protected T mock;

    public T get() {
        return mock;
    }
}

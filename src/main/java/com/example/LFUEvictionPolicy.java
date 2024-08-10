package com.example;

import org.jetbrains.annotations.NotNull;

public class LFUEvictionPolicy<K> extends AbstractEvictionPolicy<K> {
    @Override
    void keyAccessed(@NotNull K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    void keyUpdated(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    K keyToEvict() {
        throw new UnsupportedOperationException();
    }
}

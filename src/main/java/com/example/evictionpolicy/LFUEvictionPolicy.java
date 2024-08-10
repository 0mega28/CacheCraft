package com.example.evictionpolicy;

import org.jetbrains.annotations.NotNull;

public class LFUEvictionPolicy<K> extends AbstractEvictionPolicy<K> {
    @Override
    public void keyAccessed(@NotNull K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void keyUpdated(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public K keyToEvict() {
        throw new UnsupportedOperationException();
    }
}

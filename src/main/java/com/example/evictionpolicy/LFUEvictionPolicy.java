package com.example.evictionpolicy;

import org.jetbrains.annotations.NotNull;

public class LFUEvictionPolicy<K> extends OrderBasedEvictionPolicy<K> {
    @Override
    public void keyAccessed(@NotNull K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void keyUpdated(@NotNull K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public K keyToEvict() {
        throw new UnsupportedOperationException();
    }
}

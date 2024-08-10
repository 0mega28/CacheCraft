package com.example.evictionpolicy;

import org.jetbrains.annotations.NotNull;

public class LFUEvictionPolicy<K> implements EvictionPolicy<K> {
    @Override
    public void keyAdded(@NotNull K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void keyRemoved(@NotNull K keyToEvict) {
        throw new UnsupportedOperationException();
    }

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

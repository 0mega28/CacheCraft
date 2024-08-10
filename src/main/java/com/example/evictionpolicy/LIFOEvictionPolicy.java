package com.example.evictionpolicy;

import org.jetbrains.annotations.NotNull;

public class LIFOEvictionPolicy<K> extends AbstractEvictionPolicy<K> {
    @Override
    public void keyAccessed(@NotNull K key) {

    }

    @Override
    public void keyUpdated(K key) {

    }

    @Override
    public K keyToEvict() {
        throwIfEmpty();

        return keyOrder.getLast().getValue();
    }
}

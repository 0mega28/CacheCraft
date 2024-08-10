package com.example.evictionpolicy;

import org.jetbrains.annotations.NotNull;

public class FIFOEvictionPolicy<K> extends OrderBasedEvictionPolicy<K> {
    @Override
    public void keyAccessed(@NotNull K key) {

    }

    @Override
    public void keyUpdated(@NotNull K key) {

    }

    @Override
    public K keyToEvict() {
        throwIfEmpty();

        return keyOrder.getFirst().getValue();
    }
}

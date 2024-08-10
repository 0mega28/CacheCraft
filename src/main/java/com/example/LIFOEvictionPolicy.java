package com.example;

import org.jetbrains.annotations.NotNull;

public class LIFOEvictionPolicy<K> extends AbstractEvictionPolicy<K> {
    @Override
    void keyAccessed(@NotNull K key) {

    }

    @Override
    void keyUpdated(K key) {

    }

    @Override
    K keyToEvict() {
        throwIfEmpty();

        return keyOrder.getLast().getValue();
    }
}

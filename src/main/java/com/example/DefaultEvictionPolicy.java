package com.example;

import org.jetbrains.annotations.NotNull;

public class DefaultEvictionPolicy<K> extends AbstractEvictionPolicy<K> {
    @Override
    public void keyAccessed(@NotNull K key) {

    }

    @Override
    public void keyUpdated(K key) {

    }

    @Override
    public K keyToEvict() {
        throwIfEmpty();

        return keyNodeMap.keySet().iterator().next();
    }
}

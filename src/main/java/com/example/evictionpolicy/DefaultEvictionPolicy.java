package com.example.evictionpolicy;

import org.jetbrains.annotations.NotNull;

public class DefaultEvictionPolicy<K> extends OrderBasedEvictionPolicy<K> {
    @Override
    public void keyAccessed(@NotNull K key) {

    }

    @Override
    public void keyUpdated(@NotNull K key) {

    }

    @NotNull
    @Override
    public K keyToEvict() {
        throwIfEmpty();

        return keyNodeMap.keySet().iterator().next();
    }
}

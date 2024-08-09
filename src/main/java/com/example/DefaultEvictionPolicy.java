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
        if (keyOrder.isEmpty()) {
            // TODO Create proper exception
            throw new IllegalStateException("Key Eviction List is empty");
        }

        return keyNodeMap.keySet().iterator().next();
    }
}

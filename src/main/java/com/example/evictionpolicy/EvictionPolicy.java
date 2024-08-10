package com.example.evictionpolicy;

import org.jetbrains.annotations.NotNull;

public interface EvictionPolicy<K> {

    void keyAdded(@NotNull K key);

    void keyRemoved(@NotNull K keyToEvict);

    void keyAccessed(@NotNull K key);

    void keyUpdated(@NotNull K key);

    @NotNull K keyToEvict();

    boolean isEmpty();
    int size();
}

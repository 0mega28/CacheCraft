package com.example.storage;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Storage<K, V> {
    @NotNull Optional<V> get(@NotNull K key);

    boolean containsKey(@NotNull K key);

    void update(@NotNull K key, @NotNull V value);

    int size();

    void add(K key, V value);

    void remove(K keyToEvict);
}

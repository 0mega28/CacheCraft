package com.example;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Cache<K, V> {
    @NotNull Optional<V> get(@NotNull final K key);

    void put(@NotNull K key, @NotNull V value);

    int size();
}

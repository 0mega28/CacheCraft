package com.example;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

interface IStorage<K, V> {
    Optional<V> get(@NotNull K key);

    boolean containsKey(@NotNull K key);

    void update(@NotNull K key, @NotNull V value);

    int size();

    void add(K key, V value);

    void remove(K keyToEvict);
}

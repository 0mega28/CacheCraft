package com.example;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

class HashMapStorage<K, V> implements IStorage<K, V> {
    private final HashMap<K, V> storage;

    HashMapStorage() {
        storage = new HashMap<>();
    }

    @Override
    public Optional<V> get(@NotNull K key) {
        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public boolean containsKey(@NotNull K key) {
        return storage.containsKey(key);
    }

    @Override
    public void update(@NotNull K key, @NotNull V value) {
        storage.put(key, value);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void add(K key, V value) {
        storage.put(key, value);
    }

    @Override
    public void remove(K keyToEvict) {
        storage.remove(keyToEvict);
    }
}

package com.example;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

class CacheImpl<K, V> implements Cache<K, V> {
    private final int capacity;
    private final IStorage<K, V> storage;
    private final AbstractEvictionPolicy<K> evictionPolicy;

    CacheImpl(int capacity,
              @NotNull final AbstractEvictionPolicy<K> evictionPolicy) {
        this.capacity = capacity;
        storage = new HashMapStorage<>();
        this.evictionPolicy = evictionPolicy;
    }

    @Override
    public Optional<V> get(@NotNull K key) {
        Optional<V> value = storage.get(key);
        evictionPolicy.keyAccessed(key);
        return value;
    }

    @Override
    public void put(@NotNull K key, @NotNull V value) {
        if (storage.containsKey(key)) {
            storage.update(key, value);
            evictionPolicy.keyUpdated(key);
        } else if (storage.size() < capacity) {
            storage.add(key, value);
            evictionPolicy.keyAdded(key);
        } else {
            K keyToEvict = evictionPolicy.keyToEvict();
            storage.remove(keyToEvict);
            evictionPolicy.keyRemoved(keyToEvict);
            storage.add(key, value);
            evictionPolicy.keyAdded(key);
        }

    }

    @Override
    public int size() {
        return storage.size();
    }
}

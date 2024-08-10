package com.example;

import com.example.evictionpolicy.EvictionPolicy;
import com.example.storage.HashMapStorage;
import com.example.storage.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

class CacheImpl<K, V> implements Cache<K, V> {
    private final int capacity;
    private final Storage<K, V> storage;
    private final EvictionPolicy<K> evictionPolicy;

    CacheImpl(int capacity,
              @NotNull final EvictionPolicy<K> evictionPolicy) {
        this.capacity = capacity;
        storage = new HashMapStorage<>();
        this.evictionPolicy = evictionPolicy;
    }

    @NotNull
    @Override
    public Optional<V> get(@NotNull K key) {
        Optional<V> value = storage.get(key);
        value.ifPresent(_i -> evictionPolicy.keyAccessed(key));
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

package com.example.CacheImpl;

import com.example.Cache;

import java.util.HashMap;
import java.util.Map;

public class DefaultCache<K, V> implements Cache<K, V> {
    Map<K, V> cache;

    public DefaultCache() {
        cache = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public boolean put(K key, V value) {
        cache.put(key, value);
        return true;
    }
}

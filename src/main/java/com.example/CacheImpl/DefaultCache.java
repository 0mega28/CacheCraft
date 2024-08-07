package com.example.CacheImpl;

import com.example.Cache;

import java.util.HashMap;
import java.util.Map;

public class DefaultCache<K, V> implements Cache<K, V> {
    private final long cacheSize;
    Map<K, V> cache;

    public DefaultCache(long cacheSize) {
        this.cacheSize = cacheSize;
        cache = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public boolean put(K key, V value) {
        if (!cache.containsKey(key)
            && cache.size() > cacheSize) {
            cache
                    .keySet()
                    .stream()
                    .findAny()
                    .ifPresent(cache::remove);
        }
        cache.put(key, value);
        return true;
    }
}

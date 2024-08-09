package com.example.CacheImpl;

import com.example.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultCache<K, V> implements Cache<K, V> {
    private final long cacheSize;
    Map<K, V> cache;

    public DefaultCache(final long cacheSize) {
        this.cacheSize = cacheSize;
        cache = new HashMap<>();
    }

    @Override
    public Optional<V> get(final K key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public boolean put(final K key, final V value) {
        if (cache.containsKey(key)) {
            cache.put(key, value);
            return true;
        }

        if (cache.size() < cacheSize) {
            cache.put(key, value);
            return true;
        }

        K keyToBeRemoved = cache.keySet().stream().findAny().get();
        cache.remove(keyToBeRemoved);
        cache.put(key, value);
        return true;
    }
}

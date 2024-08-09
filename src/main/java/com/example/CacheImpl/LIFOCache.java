package com.example.CacheImpl;

import com.example.Cache;

import java.util.*;

/**
 * A simple LIFO (Last-In-First-Out) cache implementation.
 *
 * <p>The LIFO cache stores a fixed number of entries, evicting the most recently
 * added entry when the cache exceeds its capacity. When an entry is accessed or
 * updated, its position in the cache does not change; the eviction order remains
 * based on the order of insertion, with the newest entry being the first to be evicted.</p>
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of values maintained by this cache
 */

public class LIFOCache<K, V> implements Cache<K, V> {
    private final long cacheSize;
    private final Stack<K> keyEntryOrder;
    private final Map<K, V> cache;

    public LIFOCache(final long cacheSize) {
        this.cacheSize = cacheSize;
        keyEntryOrder = new Stack<>();
        cache = new HashMap<>();
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public boolean put(K key, V value) {
        if (cache.containsKey(key)) {
            cache.put(key, value);
            return true;
        }

        if (cache.size() < cacheSize) {
            cache.put(key, value);
            keyEntryOrder.push(key);
            return true;
        }

        K keyToRemove = keyEntryOrder.pop();
        cache.remove(keyToRemove);
        cache.put(key, value);
        keyEntryOrder.push(key);
        return true;
    }
}

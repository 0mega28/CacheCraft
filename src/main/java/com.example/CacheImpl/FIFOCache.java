package com.example.CacheImpl;

import com.example.Cache;

import java.util.*;

/**
 * A simple FIFO (First-In-First-Out) cache implementation.
 *
 * <p>The FIFO cache stores a fixed number of entries, evicting the oldest entry
 * when the cache exceeds its capacity. When an entry is accessed or updated, its
 * position in the cache does not change; the eviction order remains based on the
 * order of insertion.</p>
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of values maintained by this cache
 */
public class FIFOCache<K, V> implements Cache<K, V> {
    private final long cacheSize;
    private final Queue<K> keyEntryOrder;
    private final Map<K, V> cache;

    public FIFOCache(final long cacheSize) {
        this.cacheSize = cacheSize;
        keyEntryOrder = new ArrayDeque<>(Math.toIntExact(this.cacheSize));
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
            keyEntryOrder.add(key);
            return true;
        }

        K keyToBeRemoved = keyEntryOrder.remove();
        cache.remove(keyToBeRemoved);
        cache.put(key, value);
        keyEntryOrder.add(key);
        return true;
    }
}

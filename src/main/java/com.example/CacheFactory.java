package com.example;

import com.example.CacheImpl.DefaultCache;
import com.example.CacheImpl.FIFOCache;
import com.example.CacheImpl.LIFOCache;

public interface CacheFactory {
    static<K, V> Cache<K, V> getCache(final EvictionPolicy EVICTION_POLICY, final long cacheSize) {
        if (cacheSize <= 0) {
            throw new IllegalArgumentException("Cache size must be greater than 0");
        }
        return switch (EVICTION_POLICY) {
            case NONE -> new DefaultCache<>(cacheSize);
            case LIFO -> new LIFOCache<>(cacheSize);
            case FIFO -> new FIFOCache<>(cacheSize);
        };
    }
}

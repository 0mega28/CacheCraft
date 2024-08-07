package com.example;

import com.example.CacheImpl.DefaultCache;
import com.example.CacheImpl.FIFOCache;

public interface CacheFactory {
    static<K, V> Cache<K, V> getCache(EvictionPolicy EVICTION_POLICY, final long cacheSize) {
        if (cacheSize <= 0) {
            throw new IllegalArgumentException("Cache size must be greater than 0");
        }
        return switch (EVICTION_POLICY) {
            case NONE -> new DefaultCache<>(cacheSize);
            case FIFO -> new FIFOCache<>(cacheSize);
        };
    }
}

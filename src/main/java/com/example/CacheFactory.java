package com.example;

import com.example.evictionpolicy.*;
import org.jetbrains.annotations.NotNull;

public interface CacheFactory {
    static <K, V> @NotNull Cache<K, V> getCache(final @NotNull EvictionPolicy EVICTION_POLICY
            , final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Cache size must be greater than 0");
        }
        var evictionPolicy = switch (EVICTION_POLICY) {
            case RANDOM -> new DefaultEvictionPolicy<K>();
            case FIFO -> new FIFOEvictionPolicy<K>();
            case LIFO -> new LIFOEvictionPolicy<K>();
            case LRU -> new LRUEvictionPolicy<K>();
            case LFU -> new LFUEvictionPolicy<K>();
        };

        return new CacheImpl<>(capacity, evictionPolicy);
    }
}

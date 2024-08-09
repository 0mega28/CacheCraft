package com.example;

public interface CacheFactory {
    static<K, V> Cache<K, V> getCache(final EvictionPolicy EVICTION_POLICY, final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Cache size must be greater than 0");
        }
        return switch (EVICTION_POLICY) {
            case RANDOM -> new CacheImpl<>(capacity, new DefaultEvictionPolicy<K>());
            case FIFO -> new CacheImpl<>(capacity, new FIFOEvictionPolicy<K>());
            case LIFO -> new CacheImpl<>(capacity, new LIFOEvictionPolicy<K>());
        };
    }
}

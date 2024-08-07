package com.example;

import com.example.CacheImpl.DefaultCache;

public interface CacheFactory {
    static<K, V> Cache<K, V> getCache(EvictionPolicy EVICTION_POLICY) {
        return switch (EVICTION_POLICY) {
            case NONE -> new DefaultCache<>();
        };
    }
}

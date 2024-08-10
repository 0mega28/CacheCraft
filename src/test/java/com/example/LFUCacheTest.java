package com.example;

import org.junit.jupiter.api.BeforeEach;

public class LFUCacheTest {
    private Cache<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = CacheFactory.getCache(EvictionPolicy.LFU, 3);
    }
}

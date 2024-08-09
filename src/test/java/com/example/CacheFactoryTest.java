package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheFactoryTest {
    @Test
    void invalidCacheSize() {
        assertThrows(IllegalArgumentException.class, () -> CacheFactory.getCache(EvictionPolicy.RANDOM, -1));
        assertThrows(IllegalArgumentException.class, () -> CacheFactory.getCache(EvictionPolicy.RANDOM, 0));
    }
}
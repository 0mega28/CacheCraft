package com.example;

import com.example.CacheImpl.DefaultCache;
import com.example.CacheImpl.FIFOCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheFactoryTest {
    @Test
    void factoryTest() {
        assertEquals(CacheFactory.getCache(EvictionPolicy.NONE, 10).getClass(),
                DefaultCache.class);
        assertEquals(CacheFactory.getCache(EvictionPolicy.FIFO, 10).getClass(),
                FIFOCache.class);
    }

    @Test
    void invalidCacheSize() {
        assertThrows(IllegalArgumentException.class, () -> CacheFactory.getCache(EvictionPolicy.NONE, -1));
        assertThrows(IllegalArgumentException.class, () -> CacheFactory.getCache(EvictionPolicy.NONE, 0));
    }
}
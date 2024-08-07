package com.example;

import com.example.CacheImpl.DefaultCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheFactoryTest {
    @Test
    void factoryTest() {
        assertEquals(CacheFactory.getCache(EvictionPolicy.NONE).getClass(),
                DefaultCache.class);
    }
}
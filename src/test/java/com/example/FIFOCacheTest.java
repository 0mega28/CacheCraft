package com.example;

import com.example.CacheImpl.FIFOCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FIFOCacheTest {
    private Cache<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = CacheFactory.getCache(EvictionPolicy.FIFO, 3);
    }

    @Test
    void testFIFO() {
        cache.put("key1", "value1");
        assertEquals(Optional.of("value1"), cache.get("key1"));

        cache.put("key2", "value2");
        assertEquals(Optional.of("value2"), cache.get("key2"));

        cache.put("key3", "value3");
        assertEquals(Optional.of("value3"), cache.get("key3"));

        cache.put("key4", "value4");
        assertEquals(Optional.of("value4"), cache.get("key4"));
        assertEquals(Optional.empty(), cache.get("key1")); /* Evicted */

        /* This shouldn't affect eviction order and should be evicted nest*/
        cache.put("key2", "value5");
        cache.put("key5", "value5");
        assertEquals(Optional.empty(), cache.get("key2"));
        assertEquals(Optional.of("value5"), cache.get("key5"));

        /* This shouldn't affect eviction order and should be evicted nest*/
        assertEquals(Optional.of("value3"), cache.get("key3"));
        cache.put("key6", "value6");
        assertEquals(Optional.empty(), cache.get("key3"));

        /*
         * Evict 4th entry,
         * this test is added to check if the replacement keys are getting removed or not
         */
        cache.put("key7", "value7");
        assertEquals(Optional.empty(), cache.get("key4"));
    }
}
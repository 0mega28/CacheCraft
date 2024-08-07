package com.example.CacheImpl;

import com.example.CacheFactory;
import com.example.EvictionPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FIFOCacheTest {
    private FIFOCache<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = new FIFOCache<>(3);
    }

    @Test
    void testFIFO() {
        cache.put("key1", "value1");
        assertEquals("value1", cache.get("key1"));

        cache.put("key2", "value2");
        assertEquals("value2", cache.get("key2"));

        cache.put("key3", "value3");
        assertEquals("value3", cache.get("key3"));

        cache.put("key4", "value4");
        assertEquals("value4", cache.get("key4"));
        assertNull(cache.get("key1")); /* Evicted */

        /* This shouldn't affect eviction order and should be evicted nest*/
        cache.put("key2", "value5");
        cache.put("key5", "value5");
        assertNull(cache.get("key2"));
        assertEquals("value5", cache.get("key5"));

        /* This shouldn't affect eviction order and should be evicted nest*/
        assertEquals("value3", cache.get("key3"));
        cache.put("key6", "value6");
        assertNull(cache.get("key3"));
    }
}
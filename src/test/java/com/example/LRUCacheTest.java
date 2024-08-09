package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class LRUCacheTest {
    private Cache<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = CacheFactory.getCache(EvictionPolicy.LRU, 3);
    }

    @Test
    void testEmptyCache() {
        assertEquals(0, cache.size());
    }

    @Test
    void testCacheSize() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        assertEquals(2, cache.size());

        cache.put("key3", "value3");
        assertEquals(3, cache.size());

        cache.put("key4", "value4");
        assertEquals(3, cache.size());

        cache.put("key3", "value5");
        assertEquals(3, cache.size());
    }

    @Test
    void testGetAndSet() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        assertEquals(Optional.of("value1"), cache.get("key1"));
        assertEquals(Optional.of("value2"), cache.get("key2"));
        assertEquals(Optional.of("value3"), cache.get("key3"));

        cache.put("key3", "value4");
        assertEquals(Optional.of("value4"), cache.get("key3"));

        cache.put("key4", "value5");
        assertEquals(Optional.of("value5"), cache.get("key4"));
    }

    @Test
    void testEvictionOrder() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        /* 1 2 3 */

        cache.get("key1"); /* key1 becomes RU */
        /* 2 3 1 */
        cache.put("key4", "value4"); /* key2 should get evicted */
        /* 3 1 4 */
        assertEquals(Optional.empty(), cache.get("key2"));

        cache.put("key2", "value5"); /* key2 becomes RU */
        /* 1 4 2 */
        assertEquals(Optional.empty(), cache.get("key3"));
        cache.put("key5", "value6"); /* key
        /* 4 2 5 */
        assertEquals(Optional.empty(), cache.get("key1"));

        assertEquals(Optional.of("value4"), cache.get("key4"));
        /* 2 5 4 */
        assertEquals(Optional.of("value6"), cache.get("key5"));
        /* 2 4 5 */
        assertEquals(Optional.of("value5"), cache.get("key2"));
        /* 4 5 2 */
        cache.put("key6", "value7");
        /* 5 2 6 */
        assertEquals(Optional.empty(), cache.get("key4"));
    }
}
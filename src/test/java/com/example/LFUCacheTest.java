package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LFUCacheTest {
    private Cache<Integer, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = CacheFactory.getCache(EvictionPolicy.LFU, 3);
    }

    @Test
    void getSingleElementMultipleTimes() {
        cache.put(1, 1);
        for (int i = 0; i < 10; i++) {
            cache.get(1);
        }

        cache.put(2, 2);
        cache.put(3, 3);
        assertEquals(3, cache.size());
        cache.put(4, 4);

        for (int i = 0; i < 10; i++) {
            cache.get(4);
        }

        for (int i = 5; i < 20; i++) {
            cache.put(i, i);
        }

        assertEquals(3, cache.size());
        assertEquals(empty(), cache.get(2));
        assertEquals(empty(), cache.get(3));
        assertEquals(of(1), cache.get(1));
        assertEquals(of(4), cache.get(4));
    }

    @Test
    void testEvictionOrder() {
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);

        cache.get(1);
        cache.get(1);
        cache.get(1);
        cache.get(2);

        cache.put(4, 4);
        assertEquals(empty(), cache.get(3));
        cache.put(5, 5);
        assertEquals(empty(), cache.get(4));

        cache.put(5, 5);
        cache.put(6, 6);
        assertEquals(empty(), cache.get(2));
        assertEquals(3, cache.size());
    }

    @Test
    void testEvictionWhenFrequencyIsSame() {
        // Arrange
        cache.put(1, 10);
        cache.put(2, 20);
        cache.put(3, 30);

        // Access keys to set frequency
        cache.get(1); // freq of 1: 1
        cache.get(2); // freq of 2: 1
        cache.get(3); // freq of 3: 1

        // Act
        cache.put(4, 40); // This should evict key 1, as all have the same frequency and key 1 is the oldest.

        // Assert
        assertEquals(empty(), cache.get(1), "Key 1 should be evicted");
        assertEquals(of(20), cache.get(2), "Key 2 should still be in the cache");
        assertEquals(of(30), cache.get(3), "Key 3 should still be in the cache");
        assertEquals(of(40), cache.get(4), "Key 4 should be in the cache");
    }

    @Test
    void testUpdateFrequencyOnAccess() {
        // Arrange
        cache.put(1, 10);
        cache.put(2, 20);
        cache.put(3, 30);

        // Access keys to increase frequency
        cache.get(1); // freq of 1: 1
        cache.get(2); // freq of 2: 1

        // Act
        cache.get(1); // freq of 1: 2

        // Add a new element, should evict key 3
        cache.put(4, 40);

        // Assert
        assertEquals(of(10), cache.get(1), "Key 1 should still be in the cache");
        assertEquals(of(20), cache.get(2), "Key 2 should still be in the cache");
        assertEquals(empty(), cache.get(3), "Key 3 should be evicted");
        assertEquals(of(40), cache.get(4), "Key 4 should be in the cache");
    }
}

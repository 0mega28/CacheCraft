package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class DefaultCacheTest {

    private Cache<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = CacheFactory.getCache(EvictionPolicy.RANDOM, 10);
    }

    @Test
    void testPutAndGet() {
        // Arrange
        String key = "key1";
        String value = "value1";

        // Act
        cache.put(key, value);
        Optional<String> getResult = cache.get(key);

        assertEquals(Optional.of(value), getResult, "get should return the value that was put");
    }

    @Test
    void testGetNonExistentKey() {
        // Arrange
        String key = "nonExistentKey";

        // Act
        Optional<String> result = cache.get(key);

        // Assert
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testPutOverwriteValue() {
        // Arrange
        String key = "key1";
        String value1 = "value1";
        String value2 = "value2";

        // Act
        cache.put(key, value1);
        cache.put(key, value2);
        Optional<String> result = cache.get(key);

        // Assert
        assertEquals(Optional.of(value2), result, "get should return the most recently put value for the key");
    }
}

package com.example.CacheImpl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultCacheTest {

    private DefaultCache<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = new DefaultCache<>(10);
    }

    @Test
    void testPutAndGet() {
        // Arrange
        String key = "key1";
        String value = "value1";

        // Act
        boolean putResult = cache.put(key, value);
        String getResult = cache.get(key);

        // Assert
        assertTrue(putResult, "put should return true");
        assertEquals(value, getResult, "get should return the value that was put");
    }

    @Test
    void testGetNonExistentKey() {
        // Arrange
        String key = "nonExistentKey";

        // Act
        String result = cache.get(key);

        // Assert
        assertNull(result, "get should return null for a non-existent key");
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
        String result = cache.get(key);

        // Assert
        assertEquals(value2, result, "get should return the most recently put value for the key");
    }
}

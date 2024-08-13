package com.example;

import com.example.evictionpolicy.LIFOEvictionPolicy;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

public class LIFOCacheTest {

    private Cache<String, String> cache;

    @BeforeEach
    public void setUp() {
        cache = CacheFactory.getCache(EvictionPolicy.LIFO, 3);
    }

    @Test
    public void testPutAndGet() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        assertEquals(Optional.of("value1"), cache.get("key1"));
        assertEquals(Optional.of("value2"), cache.get("key2"));
        assertEquals(Optional.of("value3"), cache.get("key3"));
    }

    @Test
    public void testPutWithEviction() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        // This should cause "key3" to be evicted
        cache.put("key4", "value4");

        assertEquals(Optional.of("value1"), cache.get("key1"));
        assertEquals(Optional.of("value2"), cache.get("key2"));
        assertEquals(Optional.of("value4"), cache.get("key4"));
        assertEquals(Optional.empty(), cache.get("key3")); // "key3" should be evicted
    }

    @Test
    public void testPutWithUpdate() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key1", "new_value1");

        assertEquals(Optional.of("new_value1"), cache.get("key1"));
        assertEquals(Optional.of("value2"), cache.get("key2"));
    }

    @Test
    public void testEvictOrder() {
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key4", "value4"); // This should evict "key3"

        assertEquals(Optional.of("value1"), cache.get("key1"));
        assertEquals(Optional.of("value2"), cache.get("key2"));
        assertEquals(Optional.empty(), cache.get("key3")); // "key3" should be evicted
        assertEquals(Optional.of("value4"), cache.get("key4"));

        // Add another entry, which should evict "key4"
        cache.put("key5", "value5");
        assertEquals(Optional.of("value1"), cache.get("key1"));
        assertEquals(Optional.of("value2"), cache.get("key2"));
        assertEquals(Optional.empty(), cache.get("key4")); // "key4" should be evicted
        assertEquals(Optional.of("value5"), cache.get("key5"));
    }

    @Test
    public void testEmptyCache() {
        assertEquals(Optional.empty(), cache.get("key1"));
    }

    @Test
    public void testPutAndUpdate() {
        cache.put("key1", "value1");
        cache.put("key1", "new_value1");

        assertEquals(Optional.of("new_value1"), cache.get("key1"));
    }

    @Test
    void automatedTest() {
        CacheTestingStrategy.doTest(new LIFOEvictionPolicy<>(),
                new LIFOEvictionPolicyDumb<>());
    }

    private static class LIFOEvictionPolicyDumb<K> implements com.example.evictionpolicy.EvictionPolicy<K> {
        private final ArrayList<K> stack = new ArrayList<>();

        @Override
        public void keyAdded(@NotNull K key) {
            stack.add(key);
        }

        @Override
        public void keyRemoved(@NotNull K keyToEvict) {
            if (stack.getLast() == keyToEvict) {
                stack.removeLast();
            } else {
                stack.remove(keyToEvict);
            }
        }

        @Override
        public void keyAccessed(@NotNull K key) {

        }

        @Override
        public void keyUpdated(@NotNull K key) {

        }

        @NotNull
        @Override
        public K keyToEvict() {
            return stack.getLast();
        }

        @Override
        public int size() {
            return stack.size();
        }
    }
}

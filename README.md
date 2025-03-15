# CacheCraft [![Java CI](https://github.com/0mega28/CacheCraft/actions/workflows/ci.yml/badge.svg)](https://github.com/0mega28/CacheCraft/actions/workflows/ci.yml)

A flexible and extensible in-memory cache implementation in Java with support for multiple eviction policies.

## Overview

This library provides a generic cache implementation with pluggable eviction policies. It allows developers to store key-value pairs in memory with automatic eviction of entries when the cache reaches its capacity limit.

## Features

- Generic type support for both keys and values
- Multiple eviction policies:
  - LRU (Least Recently Used)
  - LFU (Least Frequently Used)
  - FIFO (First In First Out)
  - LIFO (Last In First Out)
  - Random
- Configurable capacity
- Extensible design for custom eviction policies

## Getting Started

### Creating a Cache

```java
// Create an LRU cache with capacity of 100
Cache<String, User> userCache = CacheFactory.getCache(EvictionPolicy.LRU, 100);

// Create an LFU cache with capacity of 50
Cache<Integer, String> dataCache = CacheFactory.getCache(EvictionPolicy.LFU, 50);
```

### Using the Cache

```java
// Store a value
userCache.put("user123", new User("John Doe"));

// Retrieve a value
Optional<User> user = userCache.get("user123");

// Check cache size
int cacheSize = userCache.size();
```

## Eviction Policies

The library supports the following eviction policies:

### LRU (Least Recently Used)
Evicts the least recently accessed entries first. Best for access patterns with temporal locality.

### LFU (Least Frequently Used)
Evicts entries that are used least frequently. Good for access patterns where popularity matters more than recency.

### FIFO (First In First Out)
Evicts the oldest entries first, operating like a queue. Simple and predictable.

### LIFO (Last In First Out)
Evicts the newest entries first, operating like a stack.

### Random
Evicts entries at random. Simple but can be useful in certain scenarios.

## Architecture

The project follows a clean, modular architecture:

- `Cache`: Main interface for cache operations
- `CacheFactory`: Factory to create appropriate cache instances
- `CacheImpl`: Core implementation of the Cache interface
- `EvictionPolicy`: Interface for pluggable eviction strategies
- `Storage`: Interface for underlying data storage

### Class Diagram

```
Cache (Interface)
    ↑
CacheImpl ──────► Storage ◄─── HashMapStorage
    │               ↑
    │               └─── ArrayListStorage
    │
    └───► EvictionPolicy ◄─── OrderBasedEvictionPolicy ◄─── LRUEvictionPolicy
                ↑                       ↑                └─── FIFOEvictionPolicy
                │                       └─── LIFOEvictionPolicy
                │                       └─── DefaultEvictionPolicy
                └─── LFUEvictionPolicy
```

## Data Structures

The library uses the following data structures:

- `HashMap`: For O(1) key-value lookups
- `DoublyLinkedList`: For efficient implementation of ordering-based policies
- Custom data structures for frequency tracking in LFU

## Performance

The cache operations have the following time complexities:

- `get`: O(1) for all policies
- `put`: O(1) for all policies
- Eviction: O(1) for all policies

## Examples

### LRU Cache Example

```java
// Create an LRU cache with capacity of 3
Cache<String, String> lruCache = CacheFactory.getCache(EvictionPolicy.LRU, 3);

// Add entries
lruCache.put("key1", "value1");
lruCache.put("key2", "value2");
lruCache.put("key3", "value3");

// Access key1 (making key2 the least recently used)
lruCache.get("key1");

// Add a new entry, which will evict key2
lruCache.put("key4", "value4");

// key2 should be evicted
assert lruCache.get("key2").isEmpty();
```

### LFU Cache Example

```java
// Create an LFU cache with capacity of 3
Cache<Integer, Integer> lfuCache = CacheFactory.getCache(EvictionPolicy.LFU, 3);

// Add entries
lfuCache.put(1, 10);
lfuCache.put(2, 20);
lfuCache.put(3, 30);

// Access key1 multiple times
lfuCache.get(1);
lfuCache.get(1);

// Access key2 once
lfuCache.get(2);

// Add a new entry, which will evict key3 (least frequently used)
lfuCache.put(4, 40);

// key3 should be evicted
assert lfuCache.get(3).isEmpty();
```

## Extension Points

This library is designed to be extensible. Here are some ways to extend it:

### Custom Eviction Policy

Implement the `EvictionPolicy` interface to create your own eviction strategy:

```java
public class MyCustomEvictionPolicy<K> implements EvictionPolicy<K> {
    // Implement the required methods
}
```

### Custom Storage

You can also implement your own storage mechanism:

```java
public class MyCustomStorage<K, V> implements Storage<K, V> {
    // Implement the required methods
}
```

## Testing

The library includes comprehensive unit tests for each eviction policy and core components:

- `DefaultCacheTest`: Basic cache functionality tests
- `LRUCacheTest`: Tests specific to LRU eviction
- `LFUCacheTest`: Tests specific to LFU eviction
- `FIFOCacheTest`: Tests specific to FIFO eviction
- `LIFOCacheTest`: Tests specific to LIFO eviction
- `DoublyLinkedListTest`: Tests for the internal data structure

## Requirements

- Java 17 or higher
- JUnit 5 for tests

## License

This project is licensed under the MIT License - see the LICENSE file for details.

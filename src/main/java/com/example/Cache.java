package com.example;

import java.util.Optional;

public interface Cache<K, V> {
    Optional<V> get(final K key);

    boolean put(final K key, final V value);
}

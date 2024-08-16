package com.example.storage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ArrayListStorage<K, V> implements Storage<K, V> {
    private static class KeyAndValue<K, V> {
        private final K key;
        V value;

        KeyAndValue(K key, V value) {
            this.key = key;
            this.value = value;
        }

        void setValue(V value) {
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private final ArrayList<KeyAndValue<K, V>> storage = new ArrayList<>();

    @NotNull
    @Override
    public Optional<V> get(@NotNull K key) {
        return storage
                .stream()
                .filter(keyAndValue -> keyAndValue.getKey().equals(key))
                .findFirst()
                .map(KeyAndValue::getValue);
    }

    @Override
    public boolean containsKey(@NotNull K key) {
        return storage
                .stream()
                .anyMatch(keyAndValue -> keyAndValue.getKey().equals(key));
    }

    @Override
    public void update(@NotNull K key, @NotNull V value) {
        storage
                .stream()
                .filter(keyAndValue -> keyAndValue.getKey().equals(key))
                .findFirst()
                .ifPresentOrElse(kv -> kv.setValue(value),
                        () -> {
                            throw new NoSuchElementException("No key to update");
                        });
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void add(K key, V value) {
        storage.add(new KeyAndValue<>(key, value));
    }

    @Override
    public void remove(K keyToEvict) {
        storage
                .stream()
                .filter(keyAndValue -> keyAndValue.getKey().equals(keyToEvict))
                .findFirst()
                .ifPresentOrElse(
                        storage::remove,
                        () -> {
                            throw new NoSuchElementException("No key to remove");
                        }
                );
    }
}

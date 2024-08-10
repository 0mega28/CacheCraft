package com.example.evictionpolicy;

import com.example.datastructure.DoublyLinkedList;
import com.example.datastructure.DoublyLinkedList.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class OrderBasedEvictionPolicy<K> implements EvictionPolicy<K> {
    DoublyLinkedList<K> keyOrder;
    Map<K, Node<K>> keyNodeMap;

    OrderBasedEvictionPolicy() {
        keyOrder = new DoublyLinkedList<>();
        keyNodeMap = new HashMap<>();
    }

    @Override
    public void keyAdded(@NotNull K key) {
        Node<K> node = keyOrder.add(key);
        keyNodeMap.put(key, node);
    }

    @Override
    public void keyRemoved(@NotNull K keyToEvict) {
        if(!keyNodeMap.containsKey(keyToEvict)) {
            throw new NoSuchElementException();
        }

        Node<K> node = keyNodeMap.remove(keyToEvict);
        keyOrder.remove(node);
    }

    @Override
    public int size() {
        if (keyOrder.size() != keyNodeMap.size()) {
            throw new IllegalStateException("Something really worng happened in the cache");
        }
        return keyNodeMap.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    void throwIfEmpty() {
        if (keyOrder.isEmpty()) {
            throw new IllegalStateException("Empty");
        }
    }

    public abstract void keyAccessed(@NotNull K key);

    public abstract void keyUpdated(@NotNull K key);

    @NotNull
    public abstract K keyToEvict();
}

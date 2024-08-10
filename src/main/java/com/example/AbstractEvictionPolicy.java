package com.example;

import com.example.datastructure.DoublyLinkedList;
import com.example.datastructure.DoublyLinkedList.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

abstract class AbstractEvictionPolicy<K> {
    DoublyLinkedList<K> keyOrder;
    Map<K, Node<K>> keyNodeMap;

    AbstractEvictionPolicy() {
        keyOrder = new DoublyLinkedList<>();
        keyNodeMap = new HashMap<>();
    }

    void keyAdded(@NotNull K key) {
        Node<K> node = keyOrder.add(key);
        keyNodeMap.put(key, node);
    }

    public void keyRemoved(@NotNull K keyToEvict) {
        if(!keyNodeMap.containsKey(keyToEvict)) {
            throw new NoSuchElementException();
        }

        Node<K> node = keyNodeMap.remove(keyToEvict);
        keyOrder.remove(node);
    }

    void throwIfEmpty() {
        if (keyOrder.isEmpty()) {
            throw new IllegalStateException("Empty");
        }
    }

    abstract void keyAccessed(@NotNull K key);

    abstract void keyUpdated(K key);

    abstract K keyToEvict();
}

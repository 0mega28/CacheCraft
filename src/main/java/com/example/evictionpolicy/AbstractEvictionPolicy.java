package com.example.evictionpolicy;

import com.example.datastructure.DoublyLinkedList;
import com.example.datastructure.DoublyLinkedList.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class AbstractEvictionPolicy<K> {
    DoublyLinkedList<K> keyOrder;
    Map<K, Node<K>> keyNodeMap;

    AbstractEvictionPolicy() {
        keyOrder = new DoublyLinkedList<>();
        keyNodeMap = new HashMap<>();
    }

    public void keyAdded(@NotNull K key) {
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

    public abstract void keyAccessed(@NotNull K key);

    public abstract void keyUpdated(K key);

    public abstract K keyToEvict();
}

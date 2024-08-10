package com.example.evictionpolicy;

import com.example.datastructure.DoublyLinkedList.Node;
import org.jetbrains.annotations.NotNull;

public class LRUEvictionPolicy<K> extends OrderBasedEvictionPolicy<K> {
    @Override
    public void keyAccessed(@NotNull K key) {
        Node<K> node = keyNodeMap.get(key);
        keyOrder.remove(node);
        Node<K> newNode = keyOrder.add(node.getValue());
        keyNodeMap.put(key, newNode);
    }

    @Override
    public void keyUpdated(@NotNull K key) {
        keyAccessed(key);
    }

    @NotNull
    @Override
    public K keyToEvict() {
        throwIfEmpty();

        return keyOrder.getFirst().getValue();
    }
}

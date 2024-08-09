package com.example;

import com.example.datastructure.DoublyLinkedList.Node;
import org.jetbrains.annotations.NotNull;

public class FIFOEvictionPolicy<K> extends AbstractEvictionPolicy<K> {
    @Override
    void keyAccessed(@NotNull K key) {

    }

    @Override
    void keyUpdated(K key) {

    }

    @Override
    K keyToEvict() {
        if (keyOrder.isEmpty()) {
            // TODO Create proper exception
            throw new IllegalStateException("Key Eviction List is empty");
        }

        return keyOrder.getFirst().getValue();
    }
}

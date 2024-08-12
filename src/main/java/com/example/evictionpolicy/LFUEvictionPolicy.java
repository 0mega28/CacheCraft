package com.example.evictionpolicy;

import com.example.datastructure.DoublyLinkedList;
import com.example.datastructure.DoublyLinkedList.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LFUEvictionPolicy<K> implements EvictionPolicy<K> {
    static class KeyAndFreq<K> {
        private final K key;
        private int freq;

        KeyAndFreq(K key) {
            this.key = key;
            freq = 1;
        }

        K getKey() {
            return key;
        }

        int getFreq() {
            return freq;
        }

        void incrementFreq() {
            freq++;
        }
    }


    private final DoublyLinkedList<LRUEvictionPolicy<K>> freqOrder;
    private final Map<Integer, Node<LRUEvictionPolicy<K>>> freqToNodeMap;
    private final Map<K, KeyAndFreq<K>> keyToKeyFreqMap;

    public LFUEvictionPolicy() {
        freqOrder = new DoublyLinkedList<>();
        freqToNodeMap = new HashMap<>();
        keyToKeyFreqMap = new HashMap<>();
    }

    @Override
    public void keyAdded(@NotNull K key) {
        KeyAndFreq<K> keyAndFreq = new KeyAndFreq<>(key);
        int freq = keyAndFreq.getFreq();
        keyToKeyFreqMap.put(key, keyAndFreq);
        if (freqToNodeMap.containsKey(freq)) {
            freqToNodeMap
                    .get(keyAndFreq.getFreq())
                    .getValue()
                    .keyAdded(key);
        } else {
            LRUEvictionPolicy<K> lruEvictionPolicy = new LRUEvictionPolicy<>();
            lruEvictionPolicy.keyAdded(key);
            Node<LRUEvictionPolicy<K>> node = freqOrder.addAtHead(lruEvictionPolicy);
            freqToNodeMap.put(freq, node);
        }
    }

    @Override
    public void keyRemoved(@NotNull K keyToEvict) {
        if (!keyToKeyFreqMap.containsKey(keyToEvict)) {
            throw new NoSuchElementException();
        }

        KeyAndFreq<K> keyAndFreq = keyToKeyFreqMap.remove(keyToEvict);

        int freq = keyAndFreq.getFreq();
        Node<LRUEvictionPolicy<K>> lruEvictionPolicyNode = freqToNodeMap.get(freq);
        LRUEvictionPolicy<K> lruEvictionPolicy = lruEvictionPolicyNode.getValue();

        lruEvictionPolicy.keyRemoved(keyToEvict);
        if (lruEvictionPolicy.isEmpty()) {
            freqOrder.remove(lruEvictionPolicyNode);
            freqToNodeMap.remove(freq);
        }
    }

    @Override
    public void keyAccessed(@NotNull K key) {
        if (!keyToKeyFreqMap.containsKey(key)) {
            throw new NoSuchElementException();
        }

        var keyAndFreq = keyToKeyFreqMap.get(key);
        int currFreq = keyAndFreq.getFreq();
        keyAndFreq.incrementFreq();
        int updatedFreq = keyAndFreq.getFreq();
        var lruEvictionPolicyNode = freqToNodeMap.get(currFreq);
        var currLruEvictionPolicy = lruEvictionPolicyNode.getValue();
        currLruEvictionPolicy.keyRemoved(key);

        LRUEvictionPolicy<K> updateLruEvictionPolicy;
        if (freqToNodeMap.containsKey(updatedFreq)) {
            updateLruEvictionPolicy = freqToNodeMap.get(updatedFreq).getValue();
        } else {
            updateLruEvictionPolicy = new LRUEvictionPolicy<>();
            var newNode = freqOrder.insertNext(lruEvictionPolicyNode, updateLruEvictionPolicy);
            freqToNodeMap.put(updatedFreq, newNode);
        }

        if (currLruEvictionPolicy.isEmpty()) {
            freqOrder.remove(lruEvictionPolicyNode);
            freqToNodeMap.remove(currFreq);
        }

        updateLruEvictionPolicy.keyAdded(key);
    }

    @Override
    public void keyUpdated(@NotNull K key) {
        keyAccessed(key);
    }

    @NotNull
    @Override
    public K keyToEvict() {
        if (freqOrder.isEmpty()) {
            throw new IllegalStateException("Nothing to evict");
        }
        return freqOrder.getFirst().getValue().keyToEvict();
    }

    @Override
    public int size() {
        return keyToKeyFreqMap.size();
    }
}

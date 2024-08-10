package com.example.datastructure;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;
    private final HashSet<Node<T>> nodes = new HashSet<>();

    public Node<T> add(T key) {
        Node<T> newNode = new Node<>(key);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;

        size++;
        nodes.add(newNode);
        return newNode;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node<T> getFirst() {
        if (head == null)
            throw new NoSuchElementException();

        return head;
    }

    public Node<T> getLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }

        return tail;
    }

    public Node<T> get(int index) {
        checkIndexOutOfBound(index);
        return getNode(index);
    }

    private void checkIndexOutOfBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void remove(@NotNull Node<T> node) {
        if (!nodes.contains(node)) {
            throw new IllegalArgumentException("Node is not in the list");
        }
        if (head == tail && head == node) {
            head = null;
            tail = null;
        } else if (head == node) {
            head = head.next;
            head.prev = null;
        } else if (tail == node) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        nodes.remove(node);
    }

    public void remove(int index) {
        checkIndexOutOfBound(index);
        remove(getNode(index));
    }

    private Node<T> getNode(int index) {
        Node<T> node = head;
        while (index-- > 0) {
            node = node.next;
        }
        return node;
    }

    public double size() {
        return size;
    }

    public static class Node<T> {
        Node<T> next = null;
        Node<T> prev = null;

        public T getValue() {
            return value;
        }

        private final T value;

        Node(T value) {
            this.value = value;
        }
    }
}

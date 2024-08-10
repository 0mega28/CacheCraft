package com.example.datastructure;

import com.example.datastructure.DoublyLinkedList.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {
    DoublyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test
    void testEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(NoSuchElementException.class, () -> list.getFirst());
        assertThrows(NoSuchElementException.class, () -> list.getLast());
    }

    @Test
    void testAdd() {
        Node<Integer> node0 = list.add(1);
        Node<Integer> node1 = list.add(2);
        Node<Integer> node2 = list.add(3);
        Node<Integer> node3 = list.add(4);
        // 1 2 3 4

        assertEquals(4, list.size());
        assertEquals(1, list.get(0).getValue());
        assertEquals(2, list.get(1).getValue());
        assertEquals(3, list.get(2).getValue());
        assertEquals(4, list.get(3).getValue());

        assertSame(node0, list.get(0));
        assertSame(node1, list.get(1));

        assertEquals(node0, list.getFirst());
        assertEquals(node3, list.getLast());
    }

    @Test
    void testRemove() {
        Node<Integer> node0 = list.add(1);
        Node<Integer> node1 = list.add(2);
        Node<Integer> node2 = list.add(3);
        Node<Integer> node3 = list.add(4);
        // 1 2 3 4

        list.remove(node1);
        // 1 3 4
        assertEquals(3, list.size());
        list.remove(0);
        // 3 4
        assertEquals(2, list.size());

        assertSame(node2, list.get(0));
        assertSame(node3, list.get(1));

        assertEquals(node2, list.getFirst());
        assertEquals(node3, list.getLast());

        list.remove(0);
        list.remove(0);
        testEmpty();

        list.add(1);
        assertEquals(1, list.size());
        assertSame(1, list.get(0).getValue());
    }

    @Test
    void testAddNext() {
        Node<Integer> head = list.add(1);
        Node<Integer> node1 = list.insertNext(head, 2);

        assertEquals(2, list.size());
        assertSame(node1, list.get(1));

        Node<Integer> tail = list.insertNext(node1, 3);
        assertEquals(3, list.size());
        assertSame(tail, list.getLast());
        assertSame(tail, list.get(2));

        Node<Integer> mid = list.insertNext(node1, 4);
        assertEquals(4, list.size());
        assertSame(mid, list.get(2));

        Node<Integer> node2 = list.insertNext(list.getLast(), 5);
        assertEquals(node2, list.getLast());
    }

    @Test
    void testAddHead() {
        Node<Integer> node1 = list.addAtHead(1);
        assertEquals(1, list.size());
        assertSame(node1, list.getFirst());

        Node<Integer> node2 = list.addAtHead(2);
        assertEquals(2, list.size());
        assertSame(node2, list.getFirst());

        Node<Integer> node3 = list.addAtHead(3);
        assertEquals(3, list.size());
        assertSame(node3, list.getFirst());
    }
}
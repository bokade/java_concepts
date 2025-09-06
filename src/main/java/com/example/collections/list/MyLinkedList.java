package com.example.collections.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class MyLinkedList {
    public static void main(String[] args) {
        // Initialization
        LinkedList<String> list = new LinkedList<>();

        // add elements
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("B"); // duplicate allowed
        System.out.println("Initial List: " + list);

        /* ---------------- Collection Interface Methods ---------------- */
        System.out.println("Size: " + list.size());
        System.out.println("Contains C? " + list.contains("C"));
        System.out.println("Is Empty? " + list.isEmpty());

        list.remove("B"); // remove by object
        System.out.println("After remove(B): " + list);

        list.remove(1); // remove by index
        System.out.println("After remove index 1: " + list);

        list.addAll(Arrays.asList("E", "F"));
        System.out.println("After addAll: " + list);

        list.removeAll(Arrays.asList("E"));
        System.out.println("After removeAll(E): " + list);

        list.retainAll(Arrays.asList("A", "F"));
        System.out.println("After retainAll(A,F): " + list);

        list.clear();
        System.out.println("After clear: " + list);

        /* ---------------- List Interface Methods ---------------- */
        LinkedList<String> list2 = new LinkedList<>(Arrays.asList("X", "Y", "Z", "W"));

        System.out.println("Element at index 2: " + list2.get(2));
        list2.set(1, "Q");
        System.out.println("After set: " + list2);

        list2.add(2, "R");
        System.out.println("After add at index: " + list2);

        System.out.println("Index of W: " + list2.indexOf("W"));

        list2.add("X");
        System.out.println("Last index of X: " + list2.lastIndexOf("X"));

        System.out.println("Sublist(1,3): " + list2.subList(1,3));

        /* ---------------- LinkedList Specific Methods (Deque/Queue) ---------------- */
        LinkedList<String> deque = new LinkedList<>(Arrays.asList("One", "Two", "Three"));

        // addFirst / addLast
        deque.addFirst("Zero");
        deque.addLast("Four");
        System.out.println("After addFirst & addLast: " + deque);

        // getFirst / getLast
        System.out.println("First element: " + deque.getFirst());
        System.out.println("Last element: " + deque.getLast());

        // offer (adds like queue)
        deque.offer("Five");
        deque.offerFirst("MinusOne");
        deque.offerLast("Six");
        System.out.println("After offer methods: " + deque);

        // peek / peekFirst / peekLast
        System.out.println("Peek (head): " + deque.peek());
        System.out.println("PeekFirst: " + deque.peekFirst());
        System.out.println("PeekLast: " + deque.peekLast());

        // poll / pollFirst / pollLast (remove + return element)
        System.out.println("Poll: " + deque.poll());
        System.out.println("PollFirst: " + deque.pollFirst());
        System.out.println("PollLast: " + deque.pollLast());
        System.out.println("After polls: " + deque);

        // removeFirst / removeLast
        deque.removeFirst();
        deque.removeLast();
        System.out.println("After removeFirst & removeLast: " + deque);

        // push & pop (Stack behavior)
        deque.push("Start"); // same as addFirst
        System.out.println("After push: " + deque);
        String popped = deque.pop(); // same as removeFirst
        System.out.println("Popped element: " + popped);

        /* ---------------- Iteration Methods ---------------- */
        LinkedList<String> animals = new LinkedList<>(Arrays.asList("Dog","Cat","Lion","Tiger"));

        // for-each loop
        for(String s : animals) {
            System.out.println("For-each: " + s);
        }

        // Iterator
        Iterator<String> it = animals.iterator();
        while(it.hasNext()) {
            System.out.println("Iterator: " + it.next());
        }

        // ListIterator
        ListIterator<String> lit = animals.listIterator();
        while(lit.hasNext()) {
            System.out.println("Forward: " + lit.next());
        }
        while(lit.hasPrevious()) {
            System.out.println("Backward: " + lit.previous());
        }

        // forEach with lambda
        animals.forEach(e -> System.out.println("Lambda: " + e));
    }
}

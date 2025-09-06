package com.example.collections.queue;

import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayDequeDemo {
/*


2. ArrayDeque

Resizable double-ended queue.

Faster than Stack and LinkedList for queue operations.

null elements not allowed.

Can be used as Queue (FIFO) or Deque (both ends).

3. Deque (Interface)

Deque ek interface hai jo Queue ko extend karta hai.

Isme both-ends ke liye operations defined hote hain.

Implementation: ArrayDeque, LinkedList.

Methods → same jo ArrayDeque me dikhaye.
 */
    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>();

        // ✅ Add elements
        deque.add("Apple");
        deque.add("Banana");
        deque.add("Cherry");

        // ✅ Add First/Last
        deque.addFirst("Mango");
        deque.addLast("Grapes");

        System.out.println("ArrayDeque: " + deque);

        // ✅ Peek methods
        System.out.println("Peek: " + deque.peek());
        System.out.println("Peek First: " + deque.peekFirst());
        System.out.println("Peek Last: " + deque.peekLast());

        // ✅ Remove methods
        System.out.println("Remove: " + deque.remove());
        System.out.println("Poll First: " + deque.pollFirst());
        System.out.println("Poll Last: " + deque.pollLast());
        System.out.println("After Removals: " + deque);

        // ✅ Stack operations
        deque.push("Orange");
        deque.push("Kiwi");
        System.out.println("After Push: " + deque);

        System.out.println("Pop: " + deque.pop());
        System.out.println("After Pop: " + deque);

        // ✅ Contains
        System.out.println("Contains Banana? " + deque.contains("Banana"));

        // ✅ Iterate forward
        System.out.println("Iterating forward:");
        for (String fruit : deque) {
            System.out.println(fruit);
        }

        // ✅ Iterate backward
        System.out.println("Iterating backward:");
        Iterator<String> descItr = deque.descendingIterator();
        while (descItr.hasNext()) {
            System.out.println(descItr.next());
        }

        // ✅ Size and Clear
        System.out.println("Size: " + deque.size());
        deque.clear();
        System.out.println("After Clear: " + deque);
    }
}

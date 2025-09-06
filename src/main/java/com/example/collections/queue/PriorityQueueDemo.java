package com.example.collections.queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityQueueDemo {
    /*
    1. PriorityQueue

    FIFO nahi hota, ye priority order me elements ko arrange karta hai.

            Default ordering (Natural Order) ya phir custom Comparator use kar sakte ho.

            null elements allow nahi hai.

    Duplicates allowed hain.

    Sorting ka structure internally Min Heap jaisa hota hai.


     */

    public static void main(String[] args) {
        // Min-Heap Priority Queue (Natural Ordering)
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // ✅ Add elements
        pq.add(10);
        pq.add(5);
        pq.add(20);
        pq.add(15);

        System.out.println("PriorityQueue (Min-Heap): " + pq);

        // ✅ Peek and Element
        System.out.println("Peek: " + pq.peek());
        System.out.println("Element: " + pq.element());

        // ✅ Poll and Remove
        System.out.println("Poll (removed): " + pq.poll());
        System.out.println("After Poll: " + pq);

        pq.remove(); // removes head
        System.out.println("After Remove: " + pq);

        // ✅ Offer
        pq.offer(7);
        System.out.println("After Offer(7): " + pq);

        // ✅ Contains
        System.out.println("Contains 20? " + pq.contains(20));

        // ✅ Size
        System.out.println("Size: " + pq.size());

        // ✅ Iterator traversal
        System.out.println("Iterating with Iterator:");
        Iterator<Integer> itr = pq.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        // ✅ Clear
        pq.clear();
        System.out.println("After clear: " + pq);

        // ✅ Custom Comparator (Max-Heap)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.addAll(Arrays.asList(10, 5, 20, 15));
        System.out.println("Max-Heap PriorityQueue: " + maxHeap);
    }

}

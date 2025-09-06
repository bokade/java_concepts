package com.example.collections.set;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetDemo {

    /*
    🔹 SortedSet (Interface)

Set ka child interface hai.

Elements sorted order (natural ya custom comparator) me hote hain.

No duplicates.

Common implementation → TreeSet.

📌 Methods in SortedSet

first() → First (lowest) element return karta hai.

last() → Last (highest) element return karta hai.

headSet(E toElement) → Elements before given element (exclusive).

tailSet(E fromElement) → Elements from given element (inclusive).

subSet(E fromElement, E toElement) → Elements between range.

comparator() → Comparator return karega (agar custom comparator use hua ho).

🔹 TreeSet

Implements NavigableSet (extends SortedSet).

Elements sorted hote hain (default natural order ya custom comparator).

Null allowed nahi (Java 7+ me NPE throw karega).

Backed by TreeMap internally.

Duplicate not allowed.
    * */

    public static void main(String[] args) {
        // ✅ Natural ordering (Ascending)
        TreeSet<Integer> set = new TreeSet<>();

        // ✅ Add elements
        set.add(50);
        set.add(20);
        set.add(40);
        set.add(10);
        set.add(30);

        System.out.println("Initial TreeSet (Sorted): " + set);

        // ✅ SortedSet methods
        System.out.println("First: " + set.first());
        System.out.println("Last: " + set.last());
        System.out.println("HeadSet(<30): " + set.headSet(30));
        System.out.println("TailSet(>=30): " + set.tailSet(30));
        System.out.println("SubSet(20, 50): " + set.subSet(20, 50));
        System.out.println("Comparator used: " + set.comparator()); // null = natural order

        // ✅ NavigableSet / TreeSet specific
        System.out.println("Ceiling(25): " + set.ceiling(25));
        System.out.println("Floor(25): " + set.floor(25));
        System.out.println("Higher(30): " + set.higher(30));
        System.out.println("Lower(30): " + set.lower(30));

        // ✅ Poll methods
        System.out.println("PollFirst: " + set.pollFirst());
        System.out.println("After PollFirst: " + set);
        System.out.println("PollLast: " + set.pollLast());
        System.out.println("After PollLast: " + set);

        // ✅ Descending view
        System.out.println("DescendingSet: " + set.descendingSet());

        // ✅ Iterator (Ascending)
        System.out.println("Iterator (Ascending):");
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // ✅ Iterator (Descending)
        System.out.println("Iterator (Descending):");
        Iterator<Integer> dit = set.descendingIterator();
        while (dit.hasNext()) {
            System.out.println(dit.next());
        }

        // ✅ Contains, Size, Clear
        System.out.println("Contains 40? " + set.contains(40));
        System.out.println("Size: " + set.size());
        set.clear();
        System.out.println("After Clear: " + set);

        // ✅ Custom Comparator (Descending order)
        TreeSet<String> strSet = new TreeSet<>(Comparator.reverseOrder());
        strSet.addAll(Arrays.asList("Banana", "Apple", "Mango", "Orange"));
        System.out.println("TreeSet with Custom Comparator (Descending): " + strSet);
    }


}

package com.example.collections.list;

import java.util.*;

public class MyVector {

    /*
    * Legacy class (Java 1.0 se hai).

      Implements List, RandomAccess, Cloneable, Serializable.

      Thread-safe hai (methods synchronized hote hain).

      ArrayList jaisa hi hai, but isme capacity-related methods extra hote hain (capacity(), ensureCapacity(), trimToSize(), setSize()).

      Iteration ke liye extra Enumeration interface bhi milta hai (legacy style).
    * */

    public static void main(String[] args) {
        // Initialization
        Vector<String> vec = new Vector<>();

        // add elements
        vec.add("A");
        vec.add("B");
        vec.add("C");
        vec.add("D");
        vec.add("B"); // duplicate allowed
        System.out.println("Initial Vector: " + vec);

        /* ---------------- Collection Interface Methods ---------------- */
        System.out.println("Size: " + vec.size());
        System.out.println("Contains C? " + vec.contains("C"));
        System.out.println("Is Empty? " + vec.isEmpty());

        vec.remove("B"); // remove by object
        System.out.println("After remove(B): " + vec);

        vec.remove(1); // remove by index
        System.out.println("After remove index 1: " + vec);

        vec.addAll(Arrays.asList("E", "F"));
        System.out.println("After addAll: " + vec);

        vec.removeAll(Arrays.asList("E"));
        System.out.println("After removeAll(E): " + vec);

        vec.retainAll(Arrays.asList("A", "F"));
        System.out.println("After retainAll(A,F): " + vec);

        vec.clear();
        System.out.println("After clear: " + vec);

        /* ---------------- List Interface Methods ---------------- */
        Vector<String> vec2 = new Vector<>(Arrays.asList("X", "Y", "Z", "W"));

        System.out.println("Element at index 2: " + vec2.get(2));
        vec2.set(1, "Q");
        System.out.println("After set: " + vec2);

        vec2.add(2, "R");
        System.out.println("After add at index: " + vec2);

        System.out.println("Index of W: " + vec2.indexOf("W"));

        vec2.add("X");
        System.out.println("Last index of X: " + vec2.lastIndexOf("X"));

        System.out.println("Sublist(1,3): " + vec2.subList(1,3));

        /* ---------------- Vector Specific Methods ---------------- */
        Vector<Integer> numbers = new Vector<>(Arrays.asList(10, 20, 30, 40, 50));

        // capacity()
        System.out.println("Capacity: " + numbers.capacity());

        // ensureCapacity()
        numbers.ensureCapacity(20);
        System.out.println("Capacity after ensureCapacity(20): " + numbers.capacity());

        // trimToSize()
        numbers.trimToSize();
        System.out.println("Capacity after trimToSize(): " + numbers.capacity());

        // setSize()
        numbers.setSize(10); // increases size with nulls if needed
        System.out.println("After setSize(10): " + numbers);

        // elementAt(index)
        System.out.println("Element at index 2: " + numbers.elementAt(2));

        // firstElement & lastElement
        System.out.println("First element: " + numbers.firstElement());
        System.out.println("Last element: " + numbers.lastElement());

        // removeElement(Object)
        numbers.removeElement(20);
        System.out.println("After removeElement(20): " + numbers);

        // removeElementAt(index)
        numbers.removeElementAt(2);
        System.out.println("After removeElementAt(2): " + numbers);

        // insertElementAt(element, index)
        numbers.insertElementAt(99, 1);
        System.out.println("After insertElementAt(99,1): " + numbers);

        // setElementAt(element, index)
        numbers.setElementAt(100, 0);
        System.out.println("After setElementAt(100,0): " + numbers);

        // copyInto(array)
        Integer[] arr = new Integer[numbers.size()];
        numbers.copyInto(arr);
        System.out.println("Array after copyInto: " + Arrays.toString(arr));

        /* ---------------- Iteration Methods ---------------- */
        Vector<String> animals = new Vector<>(Arrays.asList("Dog","Cat","Lion","Tiger"));

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

        // Enumeration (Legacy)
        Enumeration<String> en = animals.elements();
        while(en.hasMoreElements()) {
            System.out.println("Enumeration: " + en.nextElement());
        }
    }



}

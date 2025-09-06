package com.example.collections.list;

import java.util.*;

public class MyArrayList {

    public static void main(String[] args) {
        // ArrayList initialization
        ArrayList<String> list = new ArrayList<>();

        // 1. add elements (Collection interface)
        list.add("Apple");
        list.add("Banana");
        list.add("Mango");
        list.add("Orange");
        list.add("Banana"); // duplicate allowed

        System.out.println("Initial List: " + list);

        /* Methods from Collection Interface */
        // size()
        System.out.println("Size: " + list.size());

        // contains()
        System.out.println("Contains Mango? " + list.contains("Mango"));

        // isEmpty()
        System.out.println("Is Empty? " + list.isEmpty());

        // remove(Object)
        list.remove("Banana");
        System.out.println("After removing Banana: " + list);

        // remove(index)
        list.remove(1); // removes element at index 1
        System.out.println("After removing element at index 1: " + list);

        // removeIf(Predicate)
        list.removeIf(e -> e.startsWith("O"));
        System.out.println("After removeIf (start with O): " + list);

        // addAll(Collection)
        ArrayList<String> newFruits = new ArrayList<>();
        newFruits.add("Grapes");
        newFruits.add("Pineapple");
        list.addAll(newFruits);
        System.out.println("After addAll: " + list);

        // addAll(index, Collection)
        list.addAll(1, Arrays.asList("Kiwi", "Papaya"));
        System.out.println("After addAll at index 1: " + list);

        // retainAll()
        list.retainAll(Arrays.asList("Apple", "Grapes"));
        System.out.println("After retainAll (only Apple & Grapes): " + list);

        // removeAll()
        list.removeAll(Arrays.asList("Grapes"));
        System.out.println("After removeAll (removed Grapes): " + list);

        // clear()
        list.clear();
        System.out.println("After clear: " + list);

        /* Methods from List Interface */
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));

        // get(index)
        System.out.println("Element at index 2: " + list2.get(2));

        // set(index, element)
        list2.set(1, "Z");
        System.out.println("After set: " + list2);

        // add(index, element)
        list2.add(2, "Y");
        System.out.println("After add at index: " + list2);

        // indexOf()
        System.out.println("Index of D: " + list2.indexOf("D"));

        // lastIndexOf()
        list2.add("A");
        System.out.println("Last index of A: " + list2.lastIndexOf("A"));

        // subList()
        System.out.println("Sublist(1,4): " + list2.subList(1,4));

        /* ArrayList Specific Methods */
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(50, 20, 40, 10, 30));

        // ensureCapacity()
        numbers.ensureCapacity(20);

        // trimToSize()
        numbers.trimToSize();

        // clone()
        ArrayList<Integer> cloned = (ArrayList<Integer>) numbers.clone();
        System.out.println("Cloned: " + cloned);

        // toArray()
        Object[] arr = numbers.toArray();
        System.out.println("Array: " + Arrays.toString(arr));

        // replaceAll()
        numbers.replaceAll(n -> n * 2); // doubles each number
        System.out.println("After replaceAll (double each element): " + numbers);

        // sort()
        numbers.sort(Comparator.naturalOrder());
        System.out.println("After sort ascending: " + numbers);

        numbers.sort(Comparator.reverseOrder());
        System.out.println("After sort descending: " + numbers);

        /* Iteration Methods */
        ArrayList<String> animals = new ArrayList<>(Arrays.asList("Dog","Cat","Lion","Tiger"));

        // for-each loop
        for(String s : animals) {
            System.out.println("For-each: " + s);
        }

        // Iterator
        Iterator<String> it = animals.iterator();
        while(it.hasNext()) {
            System.out.println("Iterator: " + it.next());
        }

        // ListIterator (bi-directional)
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

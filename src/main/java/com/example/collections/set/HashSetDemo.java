package com.example.collections.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class HashSetDemo {


    /*
    🔹 HashSet

Duplicate elements allow nahi hote.

Insertion order maintain nahi hota.

Backed by HashMap internally.

Allows null element (only one).

Unordered, Unsorted collection.

📌 Important Methods of HashSet

Ye Collection aur Set dono interfaces ke methods provide karta hai:
*/
    public static void main(String[] args) {
        // ✅ Create HashSet
        HashSet<String> set = new HashSet<>();

        // ✅ Add elements
        set.add("Apple");
        set.add("Banana");
        set.add("Mango");
        set.add("Orange");
        set.add("Banana"); // duplicate, ignored
        set.add(null); // allows only one null

        System.out.println("Initial HashSet: " + set);

        // ✅ Size
        System.out.println("Size: " + set.size());

        // ✅ Contains
        System.out.println("Contains Mango? " + set.contains("Mango"));

        // ✅ Remove element
        set.remove("Orange");
        System.out.println("After removing Orange: " + set);

        // ✅ Remove all
        HashSet<String> temp = new HashSet<>(Arrays.asList("Apple", "Grapes"));
        set.removeAll(temp);
        System.out.println("After removeAll(Apple, Grapes): " + set);

        // ✅ Retain only specific elements
        set.add("Apple");
        set.add("Grapes");
        set.add("Pineapple");
        set.retainAll(Arrays.asList("Apple", "Banana"));
        System.out.println("After retainAll(Apple, Banana): " + set);

        // ✅ Contains all
        System.out.println("ContainsAll [Apple, Banana]? " + set.containsAll(Arrays.asList("Apple", "Banana")));

        // ✅ isEmpty
        System.out.println("IsEmpty? " + set.isEmpty());

        // ✅ Clone (via constructor)
        HashSet<String> cloned = new HashSet<>(set);
        System.out.println("Cloned HashSet: " + cloned);

        // ✅ toArray
        Object[] arr = set.toArray();
        System.out.println("Array: " + Arrays.toString(arr));

        // ✅ Iterator
        System.out.println("Iterating with Iterator:");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // ✅ For-each
        System.out.println("For-each:");
        for (String s : set) {
            System.out.println(s);
        }

        // ✅ Lambda forEach
        set.forEach(e -> System.out.println("Lambda: " + e));

        // ✅ Equals and HashCode
        HashSet<String> set2 = new HashSet<>(Arrays.asList("Apple", "Banana"));
        System.out.println("Equals set2? " + set.equals(set2));
        System.out.println("HashCode: " + set.hashCode());

        // ✅ Clear
        set.clear();
        System.out.println("After clear: " + set);
    }
}

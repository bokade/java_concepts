package com.example.collections.map;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class LinkedHashSetDemo {


    /*

    🔹 LinkedHashSet

Extends HashSet.

Insertion order maintain karta hai (jo order me add kiya hai wahi order preserve rahega).

Duplicate elements allow nahi karta.

Ek null element allow karta hai.

Internally LinkedHashMap use karta hai.

HashSet → Fast, unordered.

LinkedHashSet → Thoda slower (because of linked list), but insertion order preserve karta hai.
     */
    public static void main(String[] args) {
        // ✅ Create LinkedHashSet
        LinkedHashSet<String> set = new LinkedHashSet<>();

        // ✅ Add elements (insertion order preserved)
        set.add("Apple");
        set.add("Banana");
        set.add("Mango");
        set.add("Orange");
        set.add("Banana"); // duplicate ignored
        set.add(null); // allows single null

        System.out.println("Initial LinkedHashSet: " + set);

        // ✅ Size
        System.out.println("Size: " + set.size());

        // ✅ Contains
        System.out.println("Contains Mango? " + set.contains("Mango"));

        // ✅ Remove element
        set.remove("Orange");
        System.out.println("After removing Orange: " + set);

        // ✅ RemoveAll
        LinkedHashSet<String> temp = new LinkedHashSet<>(Arrays.asList("Apple", "Grapes"));
        set.removeAll(temp);
        System.out.println("After removeAll(Apple, Grapes): " + set);

        // ✅ Retain only specific elements
        set.add("Apple");
        set.add("Grapes");
        set.add("Pineapple");
        set.retainAll(Arrays.asList("Apple", "Banana"));
        System.out.println("After retainAll(Apple, Banana): " + set);

        // ✅ ContainsAll
        System.out.println("ContainsAll [Apple, Banana]? " + set.containsAll(Arrays.asList("Apple", "Banana")));

        // ✅ isEmpty
        System.out.println("IsEmpty? " + set.isEmpty());

        // ✅ Clone (via constructor)
        LinkedHashSet<String> cloned = new LinkedHashSet<>(set);
        System.out.println("Cloned LinkedHashSet: " + cloned);

        // ✅ toArray
        Object[] arr = set.toArray();
        System.out.println("Array: " + Arrays.toString(arr));

        // ✅ Iterator (insertion order maintained)
        System.out.println("Iterating with Iterator:");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // ✅ For-each loop
        System.out.println("For-each:");
        for (String s : set) {
            System.out.println(s);
        }

        // ✅ Lambda forEach
        set.forEach(e -> System.out.println("Lambda: " + e));

        // ✅ Equals and HashCode
        LinkedHashSet<String> set2 = new LinkedHashSet<>(Arrays.asList("Apple", "Banana"));
        System.out.println("Equals set2? " + set.equals(set2));
        System.out.println("HashCode: " + set.hashCode());

        // ✅ Clear
        set.clear();
        System.out.println("After clear: " + set);
    }
}

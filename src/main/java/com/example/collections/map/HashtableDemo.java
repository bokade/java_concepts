package com.example.collections.map;

import java.util.Hashtable;

public class HashtableDemo {

    /*
    Thread-safe (synchronized) but slower

Null key ❌ not allowed

Null value ❌ not allowed
    */


    public static void main(String[] args) {
        Hashtable<Integer, String> map = new Hashtable<>();

        // ✅ put
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Mango");
        map.put(4, "Orange");
        // map.put(null, "Test"); // ❌ NullPointerException
        // map.put(5, null); // ❌ NullPointerException

        System.out.println("Initial Map: " + map);

        // ✅ get, getOrDefault
        System.out.println("Get(3): " + map.get(3));
        System.out.println("GetOrDefault(10, Default): " + map.getOrDefault(10, "Default"));

        // ✅ containsKey, containsValue
        System.out.println("ContainsKey(2): " + map.containsKey(2));
        System.out.println("ContainsValue('Apple'): " + map.containsValue("Apple"));

        // ✅ remove
        map.remove(4);
        System.out.println("After remove(4): " + map);

        map.remove(2, "Banana");
        System.out.println("After conditional remove(2, Banana): " + map);

        // ✅ putIfAbsent
        map.putIfAbsent(5, "Papaya");
        System.out.println("After putIfAbsent: " + map);

        // ✅ replace
        map.replace(3, "Kiwi");
        map.replace(1, "Apple", "Pineapple");
        System.out.println("After replace: " + map);

        // ✅ replaceAll
        map.replaceAll((k, v) -> v.toUpperCase());
        System.out.println("After replaceAll: " + map);

        // ✅ keySet, values, entrySet
        System.out.println("Keys: " + map.keySet());
        System.out.println("Values: " + map.values());
        System.out.println("EntrySet: " + map.entrySet());

        // ✅ forEach
        map.forEach((k, v) -> System.out.println("Key=" + k + ", Value=" + v));

        // ✅ equals, hashCode
        Hashtable<Integer, String> copy = new Hashtable<>(map);
        System.out.println("Equals copy? " + map.equals(copy));
        System.out.println("HashCode: " + map.hashCode());

        // ✅ clear
        map.clear();
        System.out.println("After clear: " + map);
    }

}

package com.example.collections.map;

import java.util.LinkedHashMap;

public class LinkedHashMapDemo {

    public static void main(String[] args) {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();

        // ✅ put
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Mango");
        map.put(4, "Orange");
        map.put(2, "Grapes"); // overwrite duplicate key
        map.put(null, "NullKey"); // one null key allowed
        map.put(5, null); // multiple null values allowed

        System.out.println("Initial Map: " + map);

        // ✅ get, getOrDefault
        System.out.println("Get key=3: " + map.get(3));
        System.out.println("GetOrDefault(10, Default): " + map.getOrDefault(10, "Default"));

        // ✅ containsKey, containsValue
        System.out.println("ContainsKey(2): " + map.containsKey(2));
        System.out.println("ContainsValue('Apple'): " + map.containsValue("Apple"));

        // ✅ remove
        map.remove(5);
        System.out.println("After remove(5): " + map);

        map.remove(4, "Orange"); // conditional remove
        System.out.println("After conditional remove(4, Orange): " + map);

        // ✅ putIfAbsent
        map.putIfAbsent(10, "Papaya");
        System.out.println("After putIfAbsent: " + map);

        // ✅ replace
        map.replace(3, "Kiwi");
        map.replace(1, "Apple", "Pineapple");
        System.out.println("After replace: " + map);

        // ✅ replaceAll
        map.replaceAll((k, v) -> v == null ? "DefaultValue" : v.toUpperCase());
        System.out.println("After replaceAll: " + map);

        // ✅ keySet, values, entrySet
        System.out.println("Keys: " + map.keySet());
        System.out.println("Values: " + map.values());
        System.out.println("EntrySet: " + map.entrySet());

        // ✅ forEach
        map.forEach((k, v) -> System.out.println("Key=" + k + ", Value=" + v));

        // ✅ equals, hashCode
        LinkedHashMap<Integer, String> copy = new LinkedHashMap<>(map);
        System.out.println("Equals copy? " + map.equals(copy));
        System.out.println("HashCode: " + map.hashCode());

        // ✅ clear
        map.clear();
        System.out.println("After clear: " + map);
    }
}

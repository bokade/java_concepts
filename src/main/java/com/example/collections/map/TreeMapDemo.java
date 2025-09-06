package com.example.collections.map;

import java.util.TreeMap;

public class TreeMapDemo {

    /*👉 Note: TreeMap sorts keys in natural order by default.

Null key ❌ not allowed (throws NullPointerException)

Null values ✅ allowed*/

    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();

        // ✅ put
        map.put(3, "Mango");
        map.put(1, "Apple");
        map.put(4, "Orange");
        map.put(2, "Banana");
        map.put(5, null); // null values allowed
        map.put(6, "Kiwi");

        System.out.println("Initial Map (Sorted by Key): " + map);

        // ✅ get, getOrDefault
        System.out.println("Get(2): " + map.get(2));
        System.out.println("GetOrDefault(10, Default): " + map.getOrDefault(10, "Default"));

        // ✅ containsKey, containsValue
        System.out.println("ContainsKey(3): " + map.containsKey(3));
        System.out.println("ContainsValue('Apple'): " + map.containsValue("Apple"));

        // ✅ remove
        map.remove(5);
        System.out.println("After remove(5): " + map);

        map.remove(4, "Orange");
        System.out.println("After conditional remove(4, Orange): " + map);

        // ✅ putIfAbsent
        map.putIfAbsent(7, "Papaya");
        System.out.println("After putIfAbsent: " + map);

        // ✅ replace
        map.replace(2, "Grapes");
        map.replace(1, "Apple", "Pineapple");
        System.out.println("After replace: " + map);

        // ✅ replaceAll
        map.replaceAll((k, v) -> v == null ? "DefaultValue" : v.toUpperCase());
        System.out.println("After replaceAll: " + map);

        // ✅ NavigableMap features
        System.out.println("First Key: " + map.firstKey());
        System.out.println("Last Key: " + map.lastKey());
        System.out.println("HeadMap(<3): " + map.headMap(3));
        System.out.println("TailMap(3): " + map.tailMap(3));
        System.out.println("SubMap(2,5): " + map.subMap(2, 5));

        // ✅ keySet, values, entrySet
        System.out.println("Keys: " + map.keySet());
        System.out.println("Values: " + map.values());
        System.out.println("EntrySet: " + map.entrySet());

        // ✅ forEach
        map.forEach((k, v) -> System.out.println("Key=" + k + ", Value=" + v));

        // ✅ clear
        map.clear();
        System.out.println("After clear: " + map);
    }


}

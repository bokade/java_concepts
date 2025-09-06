package com.example.collections.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

    public static void main(String[] args) {


        /*
        🔹 Map (Interface)

Elements store karta hai key-value pairs me.

Key unique hoti hai, value duplicate ho sakti hai.

null key ek hi allow hoti hai (HashMap, LinkedHashMap), TreeMap me null key allow nahi hoti.

Values me multiple null allow hoti hain.

Implementations of Map

HashMap → Unordered, fast, allows one null key + many null values.

LinkedHashMap → Insertion order maintained.

TreeMap → Sorted order (by natural/comparator), no null key.

Hashtable → Legacy, thread-safe, no null key or value.
         */

        // ✅ Create HashMap
        HashMap<Integer, String> map = new HashMap<>();

        // ✅ put
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Mango");
        map.put(4, "Orange");
        map.put(2, "Grapes"); // duplicate key → overwrite
        map.put(null, "NullKey"); // one null key allowed
        map.put(5, null); // multiple null values allowed
        map.put(6, null);

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

        map.remove(6, null); // conditional remove
        System.out.println("After conditional remove(6, null): " + map);

        // ✅ putIfAbsent
        map.putIfAbsent(2, "Papaya"); // won't overwrite since key=2 exists
        map.putIfAbsent(10, "Papaya"); // will insert
        System.out.println("After putIfAbsent: " + map);

        // ✅ replace
        map.replace(3, "Kiwi");
        System.out.println("After replace(3, Kiwi): " + map);

        map.replace(4, "Orange", "Pineapple");
        System.out.println("After conditional replace(4): " + map);

        // ✅ replaceAll
        map.replaceAll((k, v) -> v == null ? "DefaultValue" : v.toUpperCase());
        System.out.println("After replaceAll: " + map);

        // ✅ size, isEmpty
        System.out.println("Size: " + map.size());
        System.out.println("IsEmpty: " + map.isEmpty());

        // ✅ keySet
        System.out.println("KeySet: " + map.keySet());

        // ✅ values
        System.out.println("Values: " + map.values());

        // ✅ entrySet
        System.out.println("EntrySet:");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // ✅ forEach with lambda
        map.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));

        // ✅ equals & hashCode
        HashMap<Integer, String> map2 = new HashMap<>(map);
        System.out.println("Equals map2? " + map.equals(map2));
        System.out.println("HashCode: " + map.hashCode());

        // ✅ clear
        map.clear();
        System.out.println("After clear: " + map);
    }
}

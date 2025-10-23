package com.example.collections.concurrent;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapExample {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(3, "Three");
        map.put(1, "One");
        map.put(2, "Two");

        map.forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}

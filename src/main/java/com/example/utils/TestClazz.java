package com.example.utils;

import  java.util.*;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class TestClazz {

    public static  void main(String[] args) {
        System.out.println("TestClazz is running...");

        String str = "management";

        Map<Character, Integer> countFreq = new LinkedHashMap<>();
        for(char c : str.toCharArray()){
            countFreq.put(c, countFreq.getOrDefault(c,0)+1);
        }


       countFreq.entrySet().stream().forEach(e-> System.out.println(e.getKey() + " : " + e.getValue()));



    }
}

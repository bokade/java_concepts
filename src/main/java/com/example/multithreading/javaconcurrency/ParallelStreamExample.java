package com.example.multithreading.javaconcurrency;

import java.util.*;
import java.util.stream.*;

public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().toList();

        int sum = list.parallelStream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Sum using parallel stream = " + sum);
    }
}


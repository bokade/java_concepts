package com.example.streamapi;

import java.util.*;
import java.util.stream.*;

public class TerminalStreamOperations {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(3, 7, 2, 9, 5, 2, 7, 10, 4);

        System.out.println("Original List: " + numbers);
        System.out.println("--------------------------------------------------");

        // 1️⃣ forEach(Consumer) → performs action for each element
        System.out.println("1️⃣ forEach():");
        numbers.stream()
                .forEach(System.out::println);
        System.out.println("--------------------------------------------------");

        // 2️⃣ forEachOrdered(Consumer) → same as forEach but ordered for parallel streams
        System.out.println("2️⃣ forEachOrdered() (in parallel stream):");
        numbers.parallelStream()
                .forEachOrdered(System.out::println);
        System.out.println("--------------------------------------------------");

        // 3️⃣ toArray() → Convert to Object[]
        System.out.println("3️⃣ toArray():");
        Object[] arr = numbers.stream().toArray();
        System.out.println(Arrays.toString(arr));

        // toArray(IntFunction) → Convert to typed array
        Integer[] intArr = numbers.stream().toArray(Integer[]::new);
        System.out.println("Typed Array: " + Arrays.toString(intArr));
        System.out.println("--------------------------------------------------");

        // 4️⃣ reduce(BinaryOperator) → Combine elements into one result
        System.out.println("4️⃣ reduce() - sum of elements:");
        int sum = numbers.stream()
                .reduce(0, Integer::sum); // identity + accumulator
        System.out.println("Sum = " + sum);

        System.out.println("Reduce without identity (Optional):");
        numbers.stream()
                .reduce(Integer::max)
                .ifPresent(max -> System.out.println("Max = " + max));
        System.out.println("--------------------------------------------------");

        // 5️⃣ collect(Collector) → Convert to other collections
        System.out.println("5️⃣ collect() - convert to list/set/map:");
        List<Integer> evenList = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Even List: " + evenList);

        Set<Integer> uniqueSet = numbers.stream()
                .collect(Collectors.toSet());
        System.out.println("Unique Set: " + uniqueSet);

        Map<Integer, String> numMap = numbers.stream()
                .collect(Collectors.toMap(n -> n, n -> "Num-" + n, (a, b) -> a));
        System.out.println("Number Map: " + numMap);
        System.out.println("--------------------------------------------------");

        // ✅ Java 16+: Direct collectors
        System.out.println("✅ Java 16+ direct collectors:");
        List<Integer> directList = numbers.stream().toList(); // immutable list
        System.out.println("stream.toList(): " + directList);
        System.out.println("--------------------------------------------------");

        // 6️⃣ count() → number of elements
        System.out.println("6️⃣ count():");
        long count = numbers.stream().count();
        System.out.println("Count = " + count);
        System.out.println("--------------------------------------------------");

        // 7️⃣ min(Comparator)
        System.out.println("7️⃣ min():");
        numbers.stream()
                .min(Integer::compareTo)
                .ifPresent(min -> System.out.println("Min = " + min));
        System.out.println("--------------------------------------------------");

        // 8️⃣ max(Comparator)
        System.out.println("8️⃣ max():");
        numbers.stream()
                .max(Integer::compareTo)
                .ifPresent(max -> System.out.println("Max = " + max));
        System.out.println("--------------------------------------------------");

        // 9️⃣ findFirst()
        System.out.println("9️⃣ findFirst():");
        numbers.stream()
                .findFirst()
                .ifPresent(first -> System.out.println("First Element = " + first));
        System.out.println("--------------------------------------------------");

        // 🔟 findAny()
        System.out.println("🔟 findAny() (in parallel stream):");
        numbers.parallelStream()
                .findAny()
                .ifPresent(any -> System.out.println("Any Element = " + any));
        System.out.println("--------------------------------------------------");

        // 1️⃣1️⃣ anyMatch(Predicate)
        System.out.println("1️⃣1️⃣ anyMatch():");
        boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        System.out.println("Has Even Number? " + hasEven);
        System.out.println("--------------------------------------------------");

        // 1️⃣2️⃣ allMatch(Predicate)
        System.out.println("1️⃣2️⃣ allMatch():");
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println("All Positive? " + allPositive);
        System.out.println("--------------------------------------------------");

        // 1️⃣3️⃣ noneMatch(Predicate)
        System.out.println("1️⃣3️⃣ noneMatch():");
        boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
        System.out.println("None Negative? " + noneNegative);
        System.out.println("--------------------------------------------------");
    }
}


package com.example.streamapi;
import java.util.*;
import java.util.stream.*;
public class StreamCreationExamples {
    public static void main(String[] args) {

        // 1️⃣ From Collection (List, Set)
        System.out.println("1️⃣ From Collection:");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        Stream<Integer> streamFromList = numbers.stream();
        streamFromList.forEach(System.out::println);

        System.out.println("\n------------------------------");

        // 2️⃣ From Arrays
        System.out.println("2️⃣ From Array:");
        int[] arr = {10, 20, 30, 40, 50};
        IntStream streamFromArray = Arrays.stream(arr);
        streamFromArray.forEach(System.out::println);

        System.out.println("\n------------------------------");

        // 3️⃣ Using Stream.of()
        System.out.println("3️⃣ Using Stream.of():");
        Stream<String> streamOf = Stream.of("Java", "Spring", "Hibernate", "React");
        streamOf.forEach(System.out::println);

        System.out.println("\n------------------------------");

        // 4️⃣ Using Stream.builder()
        System.out.println("4️⃣ Using Stream.builder():");
        Stream<String> streamBuilder = Stream.<String>builder()
                .add("Swapnil")
                .add("Riya")
                .add("Amit")
                .build();
        streamBuilder.forEach(System.out::println);

        System.out.println("\n------------------------------");

        // 5️⃣ Using Stream.generate() — generates infinite stream (use limit)
        System.out.println("5️⃣ Using Stream.generate():");
        Stream<Double> streamGenerate = Stream.generate(Math::random).limit(5);
        streamGenerate.forEach(System.out::println);

        System.out.println("\n------------------------------");

        // 6️⃣ Using Stream.iterate() — arithmetic progression
        System.out.println("6️⃣ Using Stream.iterate():");
        Stream<Integer> streamIterate = Stream.iterate(1, n -> n + 2).limit(5);
        streamIterate.forEach(System.out::println); // prints 1, 3, 5, 7, 9

        System.out.println("\n------------------------------");

        // 7️⃣ Using Stream.concat() — merge two streams
        System.out.println("7️⃣ Using Stream.concat():");
        Stream<String> s1 = Stream.of("A", "B", "C");
        Stream<String> s2 = Stream.of("D", "E");
        Stream<String> concatenated = Stream.concat(s1, s2);
        concatenated.forEach(System.out::println);

        System.out.println("\n------------------------------");

        // 8️⃣ Using Stream.ofNullable() — (Java 9+)
        System.out.println("8️⃣ Using Stream.ofNullable():");
        Stream<String> streamNullable1 = Stream.ofNullable("Hello");
        Stream<String> streamNullable2 = Stream.ofNullable(null);

        System.out.println("Stream 1:");
        streamNullable1.forEach(System.out::println);

        System.out.println("Stream 2 (null):");
        streamNullable2.forEach(System.out::println);

        System.out.println("\n------------------------------");

        // 9️⃣ Using primitive streams (IntStream, LongStream, DoubleStream)
        System.out.println("9️⃣ Primitive Streams (IntStream.range):");
        IntStream intRange = IntStream.range(1, 6); // 1 to 5
        intRange.forEach(System.out::println);
    }
}

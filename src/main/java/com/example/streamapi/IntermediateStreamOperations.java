package com.example.streamapi;
import java.util.*;
import java.util.stream.*;

public class IntermediateStreamOperations {
    public static void main(String[] args) {

        // Input list
        List<Integer> numbers = List.of(3, 5, 7, 2, 4, 2, 8, 5, 10, 12, 15, 7);

        System.out.println("Original List: " + numbers);
        System.out.println("--------------------------------------------------");

        // 1️⃣ filter(Predicate): Keep elements that match a condition
        System.out.println("1️⃣ filter() - Even numbers:");
        numbers.stream()
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 2️⃣ map(Function): Transform elements
        System.out.println("2️⃣ map() - Square each number:");
        numbers.stream()
                .map(n -> n * n)
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 3️⃣ flatMap(Function): Flatten nested streams
        System.out.println("3️⃣ flatMap() - Flatten list of lists:");
        List<List<String>> listOfLists = List.of(
                List.of("Java", "Spring"),
                List.of("React", "Angular"),
                List.of("Docker", "Kubernetes")
        );

        listOfLists.stream()
                .flatMap(List::stream) // flatten into single stream
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 4️⃣ distinct(): Remove duplicates
        System.out.println("4️⃣ distinct() - Unique numbers:");
        numbers.stream()
                .distinct()
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 5️⃣ sorted(): Natural order sorting
        System.out.println("5️⃣ sorted() - Natural order:");
        numbers.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 6️⃣ sorted(Comparator): Custom sorting (descending)
        System.out.println("6️⃣ sorted(Comparator) - Descending order:");
        numbers.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 7️⃣ peek(Consumer): Debug or inspect stream data
        System.out.println("7️⃣ peek() - With filter + map:");
        List<Integer> processed = numbers.stream()
                .filter(n -> n > 5)
                .peek(n -> System.out.println("After filter: " + n))
                .map(n -> n * 10)
                .peek(n -> System.out.println("After map: " + n))
                .toList();
        System.out.println("Processed List: " + processed);

        System.out.println("--------------------------------------------------");

        // 8️⃣ limit(long): Limit number of elements
        System.out.println("8️⃣ limit() - First 5 numbers:");
        numbers.stream()
                .limit(5)
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 9️⃣ skip(long): Skip first few elements
        System.out.println("9️⃣ skip() - Skip first 5 numbers:");
        numbers.stream()
                .skip(5)
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 10️⃣ takeWhile(Predicate) - (Java 9+)
        System.out.println("🔟 takeWhile() - Take until number >= 10:");
        numbers.stream()
                .sorted() // must be ordered for takeWhile to make sense
                .takeWhile(n -> n < 10)
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        // 11️⃣ dropWhile(Predicate) - (Java 9+)
        System.out.println("1️⃣1️⃣ dropWhile() - Skip until number >= 10:");
        numbers.stream()
                .sorted()
                .dropWhile(n -> n < 10)
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------");
    }
}

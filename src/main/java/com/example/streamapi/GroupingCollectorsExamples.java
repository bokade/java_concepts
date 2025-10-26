package com.example.streamapi;

import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

public class GroupingCollectorsExamples {

    static class Employee {
        private final String name;
        private final String dept;
        private final int salary;
        private final int age;
        private final double rating;

        public Employee(String name, String dept, int salary, int age, double rating) {
            this.name = name;
            this.dept = dept;
            this.salary = salary;
            this.age = age;
            this.rating = rating;
        }

        public String getName() { return name; }
        public String getDept() { return dept; }
        public int getSalary() { return salary; }
        public int getAge() { return age; }
        public double getRating() { return rating; }

        @Override
        public String toString() {
            return String.format("%s(%s, ₹%d, age %d, rating %.1f)", name, dept, salary, age, rating);
        }
    }

    public static void main(String[] args) {

        List<Employee> emps = List.of(
                new Employee("Amit", "Engineering", 90000, 28, 4.6),
                new Employee("Riya", "Engineering", 85000, 26, 4.3),
                new Employee("Swapnil", "Finance", 70000, 30, 4.5),
                new Employee("Pooja", "HR", 50000, 27, 3.9),
                new Employee("Rahul", "Engineering", 120000, 35, 4.8),
                new Employee("Sneha", "Finance", 75000, 29, 4.2),
                new Employee("Asha", "HR", 52000, 32, 4.0)
        );

        System.out.println("Employees:");
        emps.forEach(System.out::println);
        System.out.println("--------------------------------------------------");

        // 1️⃣ toList(), toSet()
        List<String> namesList = emps.stream()
                .map(Employee::getName)
                .collect(toList());
        System.out.println("1️⃣ toList(): " + namesList);

        Set<String> deptSet = emps.stream()
                .map(Employee::getDept)
                .collect(toSet());
        System.out.println("toSet(): " + deptSet);
        System.out.println("--------------------------------------------------");

        // 2️⃣ toMap(keyMapper, valueMapper)
        Map<String, Integer> nameToSalary = emps.stream()
                .collect(toMap(Employee::getName, Employee::getSalary));
        System.out.println("2️⃣ toMap(name, salary): " + nameToSalary);
        System.out.println("--------------------------------------------------");

        // 3️⃣ joining()
        String joinedNames = emps.stream()
                .map(Employee::getName)
                .collect(joining(", ", "[", "]"));
        System.out.println("3️⃣ joining(): " + joinedNames);
        System.out.println("--------------------------------------------------");

        // 4️⃣ counting()
        long totalEmployees = emps.stream()
                .collect(counting());
        System.out.println("4️⃣ counting(): " + totalEmployees);
        System.out.println("--------------------------------------------------");

        // 5️⃣ summingInt()
        int totalSalary = emps.stream()
                .collect(summingInt(Employee::getSalary));
        System.out.println("5️⃣ summingInt(salary): " + totalSalary);
        System.out.println("--------------------------------------------------");

        // 6️⃣ averagingDouble()
        double avgRating = emps.stream()
                .collect(averagingDouble(Employee::getRating));
        System.out.println("6️⃣ averagingDouble(rating): " + avgRating);
        System.out.println("--------------------------------------------------");

        // 7️⃣ summarizingInt()
        IntSummaryStatistics stats = emps.stream()
                .collect(summarizingInt(Employee::getSalary));
        System.out.println("7️⃣ summarizingInt(salary): " + stats);
        System.out.println("Average Salary = " + stats.getAverage());
        System.out.println("--------------------------------------------------");

        // 8️⃣ groupingBy()
        Map<String, List<Employee>> groupByDept = emps.stream()
                .collect(groupingBy(Employee::getDept));
        System.out.println("8️⃣ groupingBy(dept): " + groupByDept);
        System.out.println("--------------------------------------------------");

        // 9️⃣ groupingBy() with downstream collector
        Map<String, Double> avgSalaryByDept = emps.stream()
                .collect(groupingBy(Employee::getDept, averagingDouble(Employee::getSalary)));
        System.out.println("9️⃣ groupingBy(dept, avg salary): " + avgSalaryByDept);
        System.out.println("--------------------------------------------------");

        // 🔟 partitioningBy()
        Map<Boolean, List<Employee>> highEarners = emps.stream()
                .collect(partitioningBy(e -> e.getSalary() >= 80000));
        System.out.println("🔟 partitioningBy(salary >= 80000): " + highEarners);
        System.out.println("--------------------------------------------------");

        // 1️⃣1️⃣ mapping() with groupingBy
        Map<String, List<String>> namesByDept = emps.stream()
                .collect(groupingBy(Employee::getDept,
                        mapping(Employee::getName, toList())));
        System.out.println("1️⃣1️⃣ groupingBy + mapping(name): " + namesByDept);
        System.out.println("--------------------------------------------------");

        // 1️⃣2️⃣ collectingAndThen()
        Map<String, Integer> highestSalaryByDept = emps.stream()
                .collect(groupingBy(Employee::getDept,
                        collectingAndThen(
                                maxBy(Comparator.comparingInt(Employee::getSalary)),
                                opt -> opt.map(Employee::getSalary).orElse(0)
                        )));
        System.out.println("1️⃣2️⃣ collectingAndThen(max salary per dept): " + highestSalaryByDept);
        System.out.println("--------------------------------------------------");

        // 1️⃣3️⃣ reducing()
        int reducedTotalSalary = emps.stream()
                .collect(reducing(0, Employee::getSalary, Integer::sum));
        System.out.println("1️⃣3️⃣ reducing(total salary): " + reducedTotalSalary);
        System.out.println("--------------------------------------------------");

        // 1️⃣4️⃣ toCollection()
        TreeSet<String> sortedNames = emps.stream()
                .map(Employee::getName)
                .collect(toCollection(TreeSet::new));
        System.out.println("1️⃣4️⃣ toCollection(TreeSet): " + sortedNames);
        System.out.println("--------------------------------------------------");

        // 1️⃣5️⃣ groupingByConcurrent()
        Map<String, Long> concurrentCount = emps.parallelStream()
                .collect(groupingByConcurrent(Employee::getDept, counting()));
        System.out.println("1️⃣5️⃣ groupingByConcurrent(count): " + concurrentCount);
        System.out.println("--------------------------------------------------");

        // ✅ Combined collector example
        Map<String, Integer> roundedAvgSalaryByDept = emps.stream()
                .collect(groupingBy(Employee::getDept,
                        collectingAndThen(
                                averagingDouble(Employee::getSalary),
                                avg -> (int) Math.round(avg)
                        )));
        System.out.println("✅ Rounded average salary per dept: " + roundedAvgSalaryByDept);
        System.out.println("--------------------------------------------------");
    }
}


package com.example.streamapi;

import java.util.*;
import java.util.stream.*;
import static java.util.Map.entry;

public class GroupingCollectorsExamples {

    // Simple POJO for examples
    static class Employee {
        private final String name;
        private final String dept;
        private final int salary;
        private final int age;

        public Employee(String name, String dept, int salary, int age) {
            this.name = name;
            this.dept = dept;
            this.salary = salary;
            this.age = age;
        }
        public String getName() { return name; }
        public String getDept() { return dept; }
        public int getSalary() { return salary; }
        public int getAge() { return age; }

        @Override
        public String toString() {
            return String.format("%s(%s,₹%d,age%d)", name, dept, salary, age);
        }

    }

    public static void main(String[] args) {
        List<Employee> emps = List.of(
                new Employee("Amit", "Engineering", 90000, 28),
                new Employee("Riya", "Engineering", 85000, 26),
                new Employee("Swapnil", "Finance", 70000, 30),
                new Employee("Pooja", "HR", 50000, 27),
                new Employee("Rahul", "Engineering", 120000, 35),
                new Employee("Sneha", "Finance", 75000, 29),
                new Employee("Asha", "HR", 52000, 32)
        );

        // 1) Basic groupingBy -> Map<Dept, List<Employee>>
        System.out.println("1) group by dept -> List<Employee>:");
        Map<String, List<Employee>> byDept = emps.stream()
                .collect(Collectors.groupingBy(Employee::getDept));
        byDept.forEach((dept, list) -> System.out.println(dept + " => " + list));
        System.out.println("--------------------------------------------------");

        // 2) groupingBy with counting -> Map<Dept, Long>
        System.out.println("2) group by dept -> count:");
        Map<String, Long> countByDept = emps.stream()
                .collect(Collectors.groupingBy(Employee::getDept, Collectors.counting()));
        System.out.println(countByDept);
        System.out.println("--------------------------------------------------");

        // 3) groupingBy with summingInt -> total salary per dept
        System.out.println("3) total salary per dept (summingInt):");
        Map<String, Integer> totalSalaryByDept = emps.stream()
                .collect(Collectors.groupingBy(Employee::getDept, Collectors.summingInt(Employee::getSalary)));
        System.out.println(totalSalaryByDept);
        System.out.println("--------------------------------------------------");

        // 4) groupingBy with averagingDouble -> avg salary per dept
        System.out.println("4) average salary per dept:");
        Map<String, Double> avgSalaryByDept = emps.stream()
                .collect(Collectors.groupingBy(Employee::getDept, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(avgSalaryByDept);
        System.out.println("--------------------------------------------------");

        // 5) groupingBy with mapping -> names per dept
        System.out.println("5) names per dept (mapping -> toList):");
        Map<String, List<String>> namesByDept = emps.stream()
                .collect(Collectors.groupingBy(Employee::getDept,
                        Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println(namesByDept);
        System.out.println("--------------------------------------------------");

        // 6) multi-level grouping -> dept -> salary band -> list
        System.out.println("6) multi-level grouping (dept -> salaryBand -> employees):");
        Map<String, Map<String, List<Employee>>> multi =
                emps.stream()
                        .collect(Collectors.groupingBy(Employee::getDept,
                                Collectors.groupingBy(e -> {
                                    if (e.getSalary() >= 100_000) return "HIGH";
                                    if (e.getSalary() >= 75_000) return "MEDIUM";
                                    return "LOW";
                                })
                        ));
        System.out.println(multi);
        System.out.println("--------------------------------------------------");

        // 7) collectingAndThen -> get dept -> top paid employee (optional)
        System.out.println("7) top paid employee per dept (collectingAndThen + maxBy):");
        Map<String, Employee> topPaidPerDept = emps.stream()
                .collect(Collectors.groupingBy(Employee::getDept,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Employee::getSalary)),
                                opt -> opt.orElse(null)
                        )));
        System.out.println(topPaidPerDept);
        System.out.println("--------------------------------------------------");

        // 8) partitioningBy -> boolean split (salary >= 80000)
        System.out.println("8) partition by high salary (>=80000):");
        Map<Boolean, List<Employee>> partition = emps.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() >= 80_000));
        System.out.println(partition);
        System.out.println("--------------------------------------------------");

        // 9) groupingByConcurrent -> for parallel streams (thread-safe map)
        System.out.println("9) groupingByConcurrent example (parallel):");
        Map<String, Long> concurrentCount = emps.parallelStream()
                .collect(Collectors.groupingByConcurrent(Employee::getDept, Collectors.counting()));
        System.out.println(concurrentCount);
        System.out.println("--------------------------------------------------");

        // 10) groupingBy with specific map supplier -> LinkedHashMap to preserve insertion order
        System.out.println("10) groupingBy with LinkedHashMap (order preserved):");
        Map<String, List<Employee>> linked = emps.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDept,
                        LinkedHashMap::new,           // map supplier
                        Collectors.toList()
                ));
        System.out.println(linked);
        System.out.println("--------------------------------------------------");
    }
}


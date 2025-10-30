package com.example.multithreading.threadsafetydesign;

// ✅ Thread-safe Immutable class
final class Employee {
    private final String name;
    private final int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public int getId() { return id; }
}


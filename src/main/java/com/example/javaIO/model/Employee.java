package com.example.javaIO.model;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L; // recommended

    private int id;
    private String name;
    private double salary;

    private transient String password; // not serialized

    // getters, setters, constructors
    public Employee() {}

    public Employee(int id, String name, double salary, String password) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.password = password;
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
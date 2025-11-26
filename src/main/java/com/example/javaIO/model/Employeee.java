package com.example.javaIO.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employeee {

    private final int id;
    private final String name;
    private final LocalDate joinDate;
    private final LocalDateTime lastUpdated;

    // Constructor-based deserialization (ParameterNamesModule enables this)
    public Employeee(int id, String name, LocalDate joinDate, LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.lastUpdated = lastUpdated;
    }

    // getters only
    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDate getJoinDate() { return joinDate; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
}

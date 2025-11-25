package com.example.javaIO.model;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;

public class User {
    @JsonProperty("full_name")  // JSON property renamed
    private String name;

    @JsonIgnore  // will not appear in JSON
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL) // only non-null fields serialized
    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd") // date formatting
    private LocalDate dob;

    private int age; // new field added

    public User() {
    }

    // @JsonCreator constructor for deserialization
    @JsonCreator
    public User(@JsonProperty("full_name") String name,
                @JsonProperty("city") String city,
                @JsonProperty("dob") LocalDate dob,
                @JsonProperty("age") int age) {
        this.name = name;
        this.city = city;
        this.dob = dob;
        this.age = age;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    // @JsonValue example
    @JsonValue
    public String toJsonValue() {
        return name + " - " + city + " - " + age;
    }
}
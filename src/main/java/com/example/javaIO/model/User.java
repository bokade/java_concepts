package com.example.javaIO.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true) // ignore unknown during deserialization
public class User {

    private int id;

    @JsonProperty("full_name")
    private String name;

    @JsonIgnore
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private int age;

    @JsonUnwrapped
    private Address address;

    private Map<String, Object> extra = new HashMap<>();

    @JsonAnySetter
    public void setExtra(String key, Object value) {
        extra.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getExtra() {
        return extra;
    }

    @JsonSerialize(using = UserSerializer.class)
    @JsonDeserialize(using = UserDeserializer.class)
    private User nestedUser;

    public User() {}

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

    // extra constructor for deserializer
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public User getNestedUser() { return nestedUser; }
    public void setNestedUser(User nestedUser) { this.nestedUser = nestedUser; }


//    @JsonValue
//    public String toJsonValue() {
//        return name + " - " + city + " - " + age;
//    }
}
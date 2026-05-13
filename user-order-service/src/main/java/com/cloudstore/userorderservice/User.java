package com.cloudstore.userorderservice;

/**
 * User - Represents a user in our system.
 *
 * In a real application, this would be stored in a database.
 * For Day 1, we're returning hardcoded example data.
 */
public class User {

    private Integer id;
    private String name;
    private String email;

    // No-arg constructor (required)
    public User() {}

    // Full constructor
    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}
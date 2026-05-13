package com.cloudstore.userorderservice.model;

import jakarta.persistence.*;

/**
 * User - A JPA Entity that maps to the "users" table in the database.
 *
 * @Entity tells Spring: "This class represents a database table."
 * @Table(name = "users") sets the table name.
 * @Id marks the primary key field.
 * @GeneratedValue tells the database to auto-generate the ID (1, 2, 3, ...)
 * @Column defines the column properties.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)           // This column cannot be empty
    private String name;

    @Column(nullable = false, unique = true)  // Must be unique - no two users with same email
    private String email;

    @Column(nullable = false)
    private String password;            // We store the password here (hashed on Day 3)

    // --- No-arg constructor (REQUIRED by JPA) ---
    public User() {}

    // --- Full constructor ---
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // --- Setters ---
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
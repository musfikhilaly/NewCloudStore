package com.cloudstore.userorderservice.controller;

import com.cloudstore.userorderservice.model.User;
import com.cloudstore.userorderservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * UserController - Handles all HTTP requests related to users.
 *
 * Endpoints:
 *   GET    /users          → Get all users
 *   GET    /users/{id}     → Get one user by ID
 *   POST   /users/register → Register a new user
 *   DELETE /users/{id}     → Delete a user
 *
 * ResponseEntity lets us control the HTTP status code we send back.
 * For example: 201 Created, 200 OK, 404 Not Found, 400 Bad Request.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /users
     * Returns all users as JSON array.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);    // 200 OK
    }

    /**
     * GET /users/{id}
     * Returns one user by ID.
     * Returns 404 Not Found if user doesn't exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
        return ResponseEntity.ok(user);     // 200 OK
    }

    /**
     * POST /users/register
     * Registers a new user.
     *
     * The request body must be JSON like:
     * {
     *   "name": "Alice Johnson",
     *   "email": "alice@example.com",
     *   "password": "password123"
     * }
     *
     * @RequestBody tells Spring to convert the JSON body into a User object.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);  // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());    // 400 Bad Request
        }
    }

    /**
     * DELETE /users/{id}
     * Deletes a user by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    /**
     * GET /users/health
     * Simple health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User service is running ✅");
    }
}
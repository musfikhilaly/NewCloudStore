package com.cloudstore.userorderservice;

import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

/**
 * UserOrderController - Handles requests for users and orders.
 *
 * For Day 1, we return hardcoded (example) data.
 * In future days, this will connect to a real database.
 *
 * Endpoints:
 *   GET /users           → All users
 *   GET /users/{id}      → One user by ID
 *   GET /orders          → All orders
 *   GET /orders/{id}     → One order by ID
 */
@RestController
public class UserOrderController {

    // --- USER ENDPOINTS ---

    /**
     * GET /users
     * Returns a list of all users (hardcoded for Day 1).
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return Arrays.asList(
                new User(1, "Alice Johnson", "alice@cloudstore.com"),
                new User(2, "Bob Smith", "bob@cloudstore.com"),
                new User(3, "Carol Williams", "carol@cloudstore.com")
        );
    }

    /**
     * GET /users/{id}
     * Returns a single user by ID.
     * For Day 1, returns a hardcoded user regardless of ID.
     */
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        return new User(id, "User #" + id, "user" + id + "@cloudstore.com");
    }

    // --- ORDER ENDPOINTS ---

    /**
     * GET /orders
     * Returns a list of all orders (hardcoded for Day 1).
     */
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return Arrays.asList(
                new Order(1, 1, 5, 2, "delivered"),   // User 1 ordered product 5
                new Order(2, 2, 3, 1, "shipped"),      // User 2 ordered product 3
                new Order(3, 1, 12, 3, "pending")      // User 1 ordered product 12
        );
    }

    /**
     * GET /orders/{id}
     * Returns a single order by ID.
     */
    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable Integer id) {
        return new Order(id, 1, 5, 1, "pending");
    }

    /**
     * GET /health
     * A simple health check endpoint.
     * Useful later for Docker/AWS to verify the service is running.
     */
    @GetMapping("/health")
    public String health() {
        return "User/Order Service is running on port 8082 ✅";
    }
}
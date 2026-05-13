package com.cloudstore.userorderservice.controller;

import com.cloudstore.userorderservice.model.Order;
import com.cloudstore.userorderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * OrderController - Handles all HTTP requests related to orders.
 *
 * Endpoints:
 *   GET  /orders              → Get all orders
 *   GET  /orders/{id}         → Get one order by ID
 *   GET  /orders/user/{userId}→ Get all orders for a user
 *   POST /orders              → Create a new order
 *   PUT  /orders/{id}/status  → Update order status
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * GET /orders
     * Returns all orders as JSON array.
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * GET /orders/{id}
     * Returns one order by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(order);
    }

    /**
     * GET /orders/user/{userId}
     * Returns all orders belonging to a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    /**
     * POST /orders
     * Creates a new order.
     *
     * Request body JSON:
     * {
     *   "userId": 1,
     *   "productId": 5,
     *   "quantity": 2
     * }
     *
     * Map<String, Object> lets us receive any JSON object.
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> body) {
        try {
            Long userId = Long.valueOf(body.get("userId").toString());
            Integer productId = Integer.valueOf(body.get("productId").toString());
            Integer quantity = Integer.valueOf(body.get("quantity").toString());

            Order order = orderService.createOrder(userId, productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);   // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PUT /orders/{id}/status
     * Updates the status of an order.
     *
     * Request body JSON:
     * {
     *   "status": "shipped"
     * }
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            String newStatus = body.get("status");
            Order updated = orderService.updateOrderStatus(id, newStatus);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
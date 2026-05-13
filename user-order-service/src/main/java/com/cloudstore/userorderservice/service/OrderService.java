package com.cloudstore.userorderservice.service;

import com.cloudstore.userorderservice.model.Order;
import com.cloudstore.userorderservice.model.User;
import com.cloudstore.userorderservice.repository.OrderRepository;
import com.cloudstore.userorderservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * OrderService - Business logic for order operations.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // Spring injects both repositories automatically
    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all orders from the database.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Get one order by its ID.
     */
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    /**
     * Get all orders belonging to a specific user.
     */
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * Create a new order.
     *
     * Business rules:
     * 1. The user must exist
     * 2. Quantity must be at least 1
     * 3. Status starts as "pending"
     */
    public Order createOrder(Long userId, Integer productId, Integer quantity) {
        // Check the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Validate quantity
        if (quantity < 1) {
            throw new RuntimeException("Quantity must be at least 1");
        }

        // Create and save the order
        Order order = new Order(user, productId, quantity, "pending");
        return orderRepository.save(order);
    }

    /**
     * Update order status (e.g. from "pending" to "shipped").
     */
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}
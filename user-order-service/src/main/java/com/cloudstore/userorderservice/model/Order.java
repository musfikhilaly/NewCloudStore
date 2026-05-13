package com.cloudstore.userorderservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Links this order to a User - "many orders belong to one user"
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer productId;          // The product ID from FakeStore API

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String status;              // "pending", "shipped", "delivered"

    @Column(nullable = false)
    private LocalDateTime createdAt;    // When was this order placed?

    // --- No-arg constructor (REQUIRED by JPA) ---
    public Order() {}

    // --- Full constructor ---
    public Order(User user, Integer productId, Integer quantity, String status) {
        this.user = user;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // --- Getters ---
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Integer getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // --- Setters ---
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Order{id=" + id + ", productId=" + productId + ", status='" + status + "'}";
    }
}
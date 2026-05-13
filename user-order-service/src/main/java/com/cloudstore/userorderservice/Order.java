package com.cloudstore.userorderservice;

/**
 * Order - Represents a customer order.
 *
 * In a real system, an order would reference products by ID
 * and be stored in a database. For Day 1, we use example data.
 */
public class Order {

    private Integer id;
    private Integer userId;       // Which user placed the order
    private Integer productId;    // Which product was ordered
    private Integer quantity;
    private String status;        // "pending", "shipped", "delivered"

    // No-arg constructor
    public Order() {}

    // Full constructor
    public Order(Integer id, Integer userId, Integer productId,
                 Integer quantity, String status) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters
    public Integer getId() { return id; }
    public Integer getUserId() { return userId; }
    public Integer getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }
}
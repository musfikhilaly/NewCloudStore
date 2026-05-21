package com.cloudstore.productservice.dto;

public class OrderDTO {
    private Long id;
    private Long userId;
    private Integer productId;
    private Integer quantity;
    private String status;
    private String createdAt;

    public OrderDTO() {}

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Integer getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
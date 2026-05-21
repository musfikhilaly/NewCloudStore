package com.cloudstore.productservice.dto;

import java.util.List;

public class ProductWithOrdersDTO {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private List<OrderDTO> orders;

    public ProductWithOrdersDTO() {}

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public Double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImage() { return image; }
    public List<OrderDTO> getOrders() { return orders; }

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setPrice(Double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setImage(String image) { this.image = image; }
    public void setOrders(List<OrderDTO> orders) { this.orders = orders; }
}
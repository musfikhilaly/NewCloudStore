package com.cloudstore.productservice.dto;


public class Product {

    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;

    // --- Constructors ---

    // No-argument constructor (required by Spring for JSON conversion)
    public Product() {}

    // Constructor with all fields (useful for creating products manually)
    public Product(Integer id, String title, Double price,
                   String description, String category, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    // --- Getters (Spring needs these to convert to JSON) ---

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public Double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImage() { return image; }

    // --- Setters (Spring needs these to convert FROM JSON) ---

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setPrice(Double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setImage(String image) { this.image = image; }

    // --- toString (useful for debugging/logging) ---

    @Override
    public String toString() {
        return "Product{id=" + id + ", title='" + title + "', price=" + price + "}";
    }
}
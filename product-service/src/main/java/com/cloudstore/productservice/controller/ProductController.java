package com.cloudstore.productservice.controller;

import com.cloudstore.productservice.dto.Product;
import com.cloudstore.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ProductController - Handles all HTTP requests related to products.
 *
 * This controller exposes two endpoints:
 *   GET /products      → Returns all products
 *   GET /products/{id} → Returns one product by ID
 *
 * @RestController = @Controller + @ResponseBody
 *   - @Controller: handles web requests
 *   - @ResponseBody: automatically converts return values to JSON
 *
 * @RequestMapping("/products") sets the base URL for all methods in this class.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    // The ProductService is injected by Spring automatically
    private final ProductService productService;

    /**
     * Constructor injection of ProductService.
     * Spring Boot detects that ProductController needs a ProductService
     * and automatically provides the one it created.
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /products
     * Returns a JSON array of ALL products from FakeStore API.
     *
     * Example request:  GET http://localhost:8081/products
     * Example response: [ { "id": 1, "title": "...", ... }, { ... }, ... ]
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * GET /products/{id}
     * Returns a single product by its ID.
     *
     * {id} is a path variable — Spring pulls it from the URL automatically.
     *
     * Example request:  GET http://localhost:8081/products/1
     * Example response: { "id": 1, "title": "Fjallraven Backpack", ... }
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }
}
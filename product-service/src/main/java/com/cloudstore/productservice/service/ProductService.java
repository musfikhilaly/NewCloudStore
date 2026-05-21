package com.cloudstore.productservice.service;

import com.cloudstore.productservice.dto.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import com.cloudstore.productservice.client.UserOrderClient;
import com.cloudstore.productservice.dto.OrderDTO;
import com.cloudstore.productservice.dto.ProductWithOrdersDTO;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * ProductService - Contains the business logic for products.
 *
 * @Service tells Spring: "This is a service class — create one instance
 * and share it wherever it's needed."
 *
 * This class is responsible for:
 * - Fetching all products from FakeStore API
 * - Fetching a single product by ID from FakeStore API
 */
@Service
public class ProductService {

    private final WebClient fakeStoreWebClient;
    private final UserOrderClient userOrderClient;

    public ProductService(@Qualifier("fakeStoreWebClient") WebClient fakeStoreWebClient,
                          UserOrderClient userOrderClient) {
        this.fakeStoreWebClient = fakeStoreWebClient;
        this.userOrderClient = userOrderClient;
    }

    /**
     * Fetches ALL products from FakeStore API.
     *
     * How this works:
     * 1. We call GET https://fakestoreapi.com/products
     * 2. FakeStore returns a JSON array of products
     * 3. Spring automatically converts the JSON into a List<Product>
     * 4. We return that list to whoever called this method
     *
     * @return List of all products
     */
    public List<Product> getAllProducts() {
        return fakeStoreWebClient
                .get()                          // HTTP GET request
                .uri("/products")               // URL: /products (base URL is already set)
                .retrieve()                     // Execute the request and get the response
                .bodyToFlux(Product.class)      // Convert response body to a stream of Product objects
                .collectList()                  // Collect all products into a List
                .block();                       // Wait for the result (synchronous call)
    }

    /**
     * Fetches a SINGLE product by its ID from FakeStore API.
     *
     * @param id The product ID to fetch
     * @return The product with the given ID
     */
    public Product getProductById(Integer id) {
        return fakeStoreWebClient
                .get()
                .uri("/products/" + id)         // URL: /products/1, /products/2, etc.
                .retrieve()
                .bodyToMono(Product.class)      // One Product (not a list)
                .block();
    }
    public ProductWithOrdersDTO getProductWithOrders(Integer id, String jwtToken) {
        Product product = getProductById(id);

        if (product == null) {
            return null;
        }

        List<OrderDTO> orders = userOrderClient.getOrdersByProductId(id, jwtToken);

        ProductWithOrdersDTO result = new ProductWithOrdersDTO();
        result.setId(product.getId());
        result.setTitle(product.getTitle());
        result.setPrice(product.getPrice());
        result.setDescription(product.getDescription());
        result.setCategory(product.getCategory());
        result.setImage(product.getImage());
        result.setOrders(orders);

        return result;
    }

}
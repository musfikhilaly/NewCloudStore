package com.cloudstore.productservice.service;

import com.cloudstore.productservice.client.UserOrderClient;
import com.cloudstore.productservice.dto.OrderDTO;
import com.cloudstore.productservice.dto.Product;
import com.cloudstore.productservice.dto.ProductWithOrdersDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private WebClient fakeStoreWebClient;

    @Mock
    private UserOrderClient userOrderClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private OrderDTO testOrder;

    @BeforeEach
    void setUp() {
        testProduct = new Product(
                1,
                "Test Product",
                49.99,
                "A great product",
                "electronics",
                "http://image.url"
        );

        testOrder = new OrderDTO();
        testOrder.setId(1L);
        testOrder.setUserId(10L);
        testOrder.setProductId(1);
        testOrder.setQuantity(2);
        testOrder.setStatus("PENDING");
        testOrder.setCreatedAt("2026-05-26T10:00:00");
    }

    // -------------------------------------------------------
    // getAllProducts
    // -------------------------------------------------------

    @Test
    @DisplayName("getAllProducts - returns list of products from FakeStore API")
    void testGetAllProductsSuccess() {
        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/products")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(Product.class)).thenReturn(Flux.just(testProduct));

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getTitle());
        assertEquals(49.99, result.get(0).getPrice());
        assertEquals("electronics", result.get(0).getCategory());
    }

    @Test
    @DisplayName("getAllProducts - returns empty list when API returns nothing")
    void testGetAllProductsEmpty() {
        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/products")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(Product.class)).thenReturn(Flux.empty());

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // -------------------------------------------------------
    // getProductById
    // -------------------------------------------------------

    @Test
    @DisplayName("getProductById - returns correct product")
    void testGetProductByIdSuccess() {
        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/products/1")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.just(testProduct));

        Product result = productService.getProductById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Product", result.getTitle());
        assertEquals(49.99, result.getPrice());
    }

    @Test
    @DisplayName("getProductById - returns null when product not found")
    void testGetProductByIdNotFound() {
        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/products/99")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.empty());

        Product result = productService.getProductById(99);

        assertNull(result);
    }

    // -------------------------------------------------------
    // getProductWithOrders
    // -------------------------------------------------------

    @Test
    @DisplayName("getProductWithOrders - returns product combined with orders")
    void testGetProductWithOrdersSuccess() {
        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/products/1")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.just(testProduct));
        when(userOrderClient.getOrdersByProductId(1, "Bearer token123"))
                .thenReturn(List.of(testOrder));

        ProductWithOrdersDTO result = productService.getProductWithOrders(1, "Bearer token123");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Product", result.getTitle());
        assertEquals(49.99, result.getPrice());
        assertEquals("A great product", result.getDescription());
        assertEquals("electronics", result.getCategory());
        assertEquals("http://image.url", result.getImage());
        assertEquals(1, result.getOrders().size());
        assertEquals(1L, result.getOrders().get(0).getId());
        assertEquals("PENDING", result.getOrders().get(0).getStatus());
        verify(userOrderClient, times(1)).getOrdersByProductId(1, "Bearer token123");
    }

    @Test
    @DisplayName("getProductWithOrders - returns null when product not found")
    void testGetProductWithOrdersProductNotFound() {
        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/products/99")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.empty());

        ProductWithOrdersDTO result = productService.getProductWithOrders(99, "Bearer token123");

        assertNull(result);
        // userOrderClient should NEVER be called if product doesn't exist
        verify(userOrderClient, never()).getOrdersByProductId(any(), any());
    }

    @Test
    @DisplayName("getProductWithOrders - works correctly when product has no orders")
    void testGetProductWithOrdersNoOrders() {
        when(fakeStoreWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/products/1")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.just(testProduct));
        when(userOrderClient.getOrdersByProductId(1, "Bearer token123"))
                .thenReturn(List.of());

        ProductWithOrdersDTO result = productService.getProductWithOrders(1, "Bearer token123");

        assertNotNull(result);
        assertEquals("Test Product", result.getTitle());
        assertTrue(result.getOrders().isEmpty());
        verify(userOrderClient, times(1)).getOrdersByProductId(1, "Bearer token123");
    }
}
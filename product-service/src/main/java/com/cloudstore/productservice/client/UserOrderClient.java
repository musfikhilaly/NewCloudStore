package com.cloudstore.productservice.client;

import com.cloudstore.productservice.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Component
public class UserOrderClient {

    private final WebClient userOrderWebClient;

    public UserOrderClient(@Qualifier("userOrderWebClient") WebClient userOrderWebClient) {
        this.userOrderWebClient = userOrderWebClient;
    }

    public List<OrderDTO> getOrdersByProductId(Integer productId, String jwtToken) {
        try {
            List<OrderDTO> allOrders = userOrderWebClient
                    .get()
                    .uri("/orders")
                    .header("Authorization", "Bearer " + jwtToken)
                    .retrieve()
                    .bodyToFlux(OrderDTO.class)
                    .collectList()
                    .block();

            if (allOrders == null) {
                return Collections.emptyList();
            }

            return allOrders.stream()
                    .filter(order -> order.getProductId().equals(productId))
                    .toList();

        } catch (Exception e) {
            System.err.println("Error calling user-order-service: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
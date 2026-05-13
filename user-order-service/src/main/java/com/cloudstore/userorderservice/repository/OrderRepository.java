package com.cloudstore.userorderservice.repository;

import com.cloudstore.userorderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * OrderRepository - Gives us database operations for Order automatically.
 *
 * JpaRepository<Order, Long> gives us all basic CRUD operations for free.
 * We add one custom method: findByUserId
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders belonging to a specific user.
     * Spring generates: SELECT * FROM orders WHERE user_id = ?
     *
     * This lets us show a user's order history.
     */
    List<Order> findByUserId(Long userId);
}
package com.cloudstore.userorderservice.repository;

import com.cloudstore.userorderservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * UserRepository - Gives us database operations for User automatically.
 *
 * By extending JpaRepository<User, Long> we get these methods for FREE:
 *   save(user)          → INSERT or UPDATE a user in the database
 *   findById(id)        → SELECT * FROM users WHERE id = ?
 *   findAll()           → SELECT * FROM users
 *   deleteById(id)      → DELETE FROM users WHERE id = ?
 *   existsById(id)      → returns true if user exists
 *   count()             → returns total number of users
 *
 * We also add our own custom method: findByEmail
 * Spring automatically generates the SQL for this based on the method name!
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their email address.
     * Spring generates: SELECT * FROM users WHERE email = ?
     *
     * Returns Optional<User> because the user might not exist.
     * Optional is safer than returning null.
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if an email already exists in the database.
     * Spring generates: SELECT COUNT(*) FROM users WHERE email = ?
     * Used during registration to prevent duplicate accounts.
     */
    boolean existsByEmail(String email);
}
package com.cloudstore.userorderservice.service;

import com.cloudstore.userorderservice.model.User;
import com.cloudstore.userorderservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * UserService - Business logic for user operations.
 *
 * This class sits between UserController and UserRepository.
 * It handles the "rules" of the application:
 *   - Can't register with an email that already exists
 *   - Password validation (on Day 3 we add password hashing)
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    // Spring automatically injects UserRepository here
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get all users from the database.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get one user by their ID.
     * Returns null if not found (we improve this with proper error handling on Day 3).
     */
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    /**
     * Register a new user.
     *
     * Business rules:
     * 1. Email must not already exist in the database
     * 2. Save the user to the database
     *
     * Note: On Day 3 we add password hashing with BCrypt.
     * For now we store the password as plain text (NOT safe for production!).
     */
    public User registerUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }

        // TODO Day 3: hash the password before saving
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    /**
     * Find a user by email - used for login on Day 3.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Delete a user by ID.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
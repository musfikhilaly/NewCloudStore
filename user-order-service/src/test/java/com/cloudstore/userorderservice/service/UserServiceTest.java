package com.cloudstore.userorderservice.service;

import com.cloudstore.userorderservice.model.User;
import com.cloudstore.userorderservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should register new user successfully")
    void testRegisterUser_Success() {
        // Arrange
        String name = "John Doe";
        String email = "john@example.com";
        String password = "password123";
        String hashedPassword = "hashed_password";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        // Act
        User result = userService.registerUser(name, email, password);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        verify(userRepository).existsByEmail(email);
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void testRegisterUser_EmailAlreadyExists() {
        // Arrange
        String email = "existing@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser("Name", email, "password");
        });

        assertTrue(exception.getMessage().contains("Email already registered"));
        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should verify login with correct credentials")
    void testVerifyLogin_Success() {
        // Arrange
        String email = "john@example.com";
        String rawPassword = "password123";
        String hashedPassword = "hashed_password";

        User user = new User("John Doe", email, hashedPassword);
        user.setId(1L);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);

        // Act
        User result = userService.verifyLogin(email, rawPassword);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, hashedPassword);
    }

    @Test
    @DisplayName("Should return null when user not found")
    void testVerifyLogin_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        User result = userService.verifyLogin(email, "password");

        // Assert
        assertNull(result);
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Should return null when password is incorrect")
    void testVerifyLogin_WrongPassword() {
        // Arrange
        String email = "john@example.com";
        String wrongPassword = "wrongpassword";
        String hashedPassword = "hashed_password";

        User user = new User("John Doe", email, hashedPassword);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(wrongPassword, hashedPassword)).thenReturn(false);

        // Act
        User result = userService.verifyLogin(email, wrongPassword);

        // Assert
        assertNull(result);
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(wrongPassword, hashedPassword);
    }

    @Test
    @DisplayName("Should find user by email")
    void testFindByEmail() {
        // Arrange
        String email = "john@example.com";
        User user = new User("John Doe", email, "hashed_password");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Should delete user by id")
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository).deleteById(userId);
    }
}
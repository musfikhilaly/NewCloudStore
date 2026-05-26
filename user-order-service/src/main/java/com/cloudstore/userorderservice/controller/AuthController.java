package com.cloudstore.userorderservice.controller;

import com.cloudstore.userorderservice.dto.AuthResponse;
import com.cloudstore.userorderservice.dto.LoginRequest;
import com.cloudstore.userorderservice.dto.RegisterRequest;
import com.cloudstore.userorderservice.model.User;
import com.cloudstore.userorderservice.security.JwtUtil;
import com.cloudstore.userorderservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(
                    request.getName(),
                    request.getEmail(),
                    request.getPassword()
            );
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new AuthResponse(token, user.getEmail(), "Registration successful"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.verifyLogin(request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(
                new AuthResponse(token, user.getEmail(), "Login successful")
        );
    }
}
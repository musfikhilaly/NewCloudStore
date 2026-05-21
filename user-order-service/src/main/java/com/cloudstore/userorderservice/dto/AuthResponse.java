package com.cloudstore.userorderservice.dto;

public class AuthResponse {

    private String token;
    private String email;
    private String message;

    public AuthResponse() {}

    public AuthResponse(String token, String email, String message) {
        this.token = token;
        this.email = email;
        this.message = message;
    }

    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getMessage() { return message; }

    public void setToken(String token) { this.token = token; }
    public void setEmail(String email) { this.email = email; }
    public void setMessage(String message) { this.message = message; }
}
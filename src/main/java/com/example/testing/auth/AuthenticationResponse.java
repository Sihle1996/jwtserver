package com.example.testing.auth;

public class AuthenticationResponse {

    private String token;

    // No-arg constructor
    public AuthenticationResponse() {}

    // All-arg constructor
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    // Builder pattern
    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    public static class AuthenticationResponseBuilder {
        private String token;

        public AuthenticationResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(token);
        }
    }

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

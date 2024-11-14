package com.example.testing.auth;

import com.example.testing.config.JwtService;
import com.example.testing.repository.UserRepository;
import com.example.testing.user.Role;
import com.example.testing.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        repository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists");
                });

        // Manually create a User object without using Lombok's builder
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        repository.save(user);

        // Generate token
        String jwtToken = jwtService.generateTokenWithId(user, user.getId());

        // Manually create an AuthenticationResponse object without using Lombok's builder
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);

        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String jwtToken = jwtService.generateTokenWithId(user, user.getId());

        // Manually create an AuthenticationResponse object
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);

        return response;
    }

    public User findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User updateUser(Integer id, User updatedUser) {
        return repository.findById(id).map(existingUser -> {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            existingUser.setEmail(updatedUser.getEmail());
            return repository.save(existingUser);
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
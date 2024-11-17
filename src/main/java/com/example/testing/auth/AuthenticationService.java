package com.example.testing.auth;

import com.example.testing.config.JwtService;
import com.example.testing.repository.UserRepository;
import com.example.testing.user.Role;
import com.example.testing.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        repository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists");
                });

        // Use Lombok's builder on the User class if available
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);

        String jwtToken = jwtService.generateTokenWithId(user, user.getId());

        // Use Lombok's builder on AuthenticationResponse if available
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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

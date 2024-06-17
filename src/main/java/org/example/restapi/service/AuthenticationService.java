package org.example.restapi.service;

import org.example.restapi.repository.UserRepository;
import org.example.restapi.utils.JwtUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthenticationService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public String authenticate(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return jwtUtils.generateToken(username);
        }

        throw new RuntimeException("Invalid username or password");
    }
}

package com.findmypro.controller;
import com.findmypro.model.User;
import com.findmypro.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        logger.info("Registration attempt for username: {}", newUser.getUsername());
        
        try {
            // Validate input
            if (newUser.getUsername() == null || newUser.getPassword() == null) {
                return ResponseEntity.badRequest().body(errorResponse("Username and password are required"));
            }
            
            // Check if username exists
            // if (userRepository.existsByUsername(newUser.getUsername())) {
            //     logger.warn("Registration failed - username already exists: {}", newUser.getUsername());
            //     return ResponseEntity.badRequest().body(errorResponse("Username already exists"));
            // }
            
            // Hash password and save user
            User user = new User();
            user.setUsername(newUser.getUsername());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setEmail(newUser.getEmail());
            user.setPhone(newUser.getPhone());
            user.setRole(newUser.getRole() != null ? newUser.getRole() : "USER");
            
            User savedUser = userRepository.save(user);
            savedUser.setPassword(null); // Never return password
            
            logger.info("User registered successfully: {}", savedUser.getUsername());
            return ResponseEntity.ok(successResponse("User registered successfully", savedUser));
            
        } catch (Exception e) {
            logger.error("Registration error for username: {}", newUser.getUsername(), e);
            return ResponseEntity.internalServerError().body(errorResponse("Registration failed. Please try again."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        logger.info("Login attempt for username: {}", loginRequest.getUsername());
        
        try {
            User user = userRepository.findByUsername(loginRequest.getUsername());
            
            if (user == null) {
                logger.warn("Login failed - user not found: {}", loginRequest.getUsername());
                return ResponseEntity.status(401).body(errorResponse("Invalid credentials"));
            }
            
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                logger.warn("Login failed - invalid password for user: {}", loginRequest.getUsername());
                return ResponseEntity.status(401).body(errorResponse("Invalid credentials"));
            }
            
            user.setPassword(null); // Never return password
            
            // Create response payload
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", user);
            
            logger.info("Login successful for user: {}", user.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Login error for username: {}", loginRequest.getUsername(), e);
            return ResponseEntity.internalServerError().body(errorResponse("Login failed. Please try again."));
        }
    }

    // Helper methods for standardized responses
    private Map<String, Object> successResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return response;
    }
    
    private Map<String, Object> errorResponse(String error) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", error);
        return response;
    }
}
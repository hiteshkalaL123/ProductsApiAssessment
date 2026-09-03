package com.product.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.product.Entity.User;
import com.product.repository.Userrepository;

@Service

public class AuthService {

	@Autowired
    private  Userrepository userRepository;
	
	@Autowired
    private  PasswordEncoder passwordEncoder;
	
	@Autowired
    private  AuthenticationManager authenticationManager;
    
	@Autowired
    private  JwtService jwtService;

    // =========================
    // REGISTER
    // =========================
	public User register(User user) {

	    if (userRepository.existsByUsername(user.getUsername())) {
	        throw new RuntimeException("Username already exists");
	    }

	    user.setPassword(
	            passwordEncoder.encode(user.getPassword())
	    );

	    if (user.getRole() == null || user.getRole().isBlank()) {
	        user.setRole("USER");
	    }

	    user.setCreatedOn(LocalDateTime.now());

	    return userRepository.save(user);
	}

    // =========================
    // LOGIN
    // =========================
    public String login(String username, String password) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username,
                                password
                        )
                );

        // Generate JWT after successful authentication
        return jwtService.generateToken(username);
    }
}
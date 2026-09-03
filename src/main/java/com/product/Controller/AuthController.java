package com.product.Controller;

import com.product.Entity.User;
import com.product.Service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

	@Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody User user) {

        User registeredUser = authService.register(user);

        return new ResponseEntity<>(
                registeredUser,
                HttpStatus.CREATED
        );
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {

        String token = authService.login(
                username,
                password
        );

        return ResponseEntity.ok(token);
    }
}
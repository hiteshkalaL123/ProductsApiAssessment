package com.product.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.product.Entity.User;
import com.product.Service.AuthService;
import com.product.Service.JwtService;
import com.product.repository.Userrepository;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private Userrepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldCreateUser() {

        User user = new User();

        user.setUsername("hitesh");
        user.setPassword("123456");
        user.setRole("USER");

        when(userRepository.existsByUsername("hitesh"))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        User result =
                authService.register(user);

        assertNotNull(result);

        verify(passwordEncoder)
                .encode("123456");

        verify(userRepository)
                .save(user);
    }

    @Test
    void login_shouldReturnJwt() {

        when(jwtService.generateToken("hitesh"))
                .thenReturn("test-jwt-token");

        String token =
                authService.login(
                        "hitesh",
                        "123456"
                );

        assertEquals(
                "test-jwt-token",
                token
        );

        verify(authenticationManager)
                .authenticate(any());
    }
}

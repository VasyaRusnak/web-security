package com.example.demo.auth; // Змініть пакет на свій

import com.example.demo.jwt.JwtService; // Змініть пакет на свій
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        // Хардкодимо ім'я, оскільки ми не використовуємо БД
        claims.put("fullName", "John Smith");

        var user = (org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal();

        // Викликаємо метод так, як він був написаний у першому робочому варіанті
        String jwtToken = jwtService.generateToken(user, "John Smith");

        // Використовуємо звичайний конструктор замість Lombok builder()
        return new AuthenticationResponse(jwtToken);
    }
}
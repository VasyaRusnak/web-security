package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @GetMapping("/hello/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin() {
        return "Hello Admin!";
    }

    @GetMapping("/hello/user")
    @PreAuthorize("hasRole('USER')")
    public String helloUser() {
        return "Hello User!";
    }

    @GetMapping("/hello/man")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public String helloMan() {
        // Повернуто стандартний рядок, як було у ваших файлах, якщо тести очікують інше - змініть текст
        return "Hello Man!";
    }

    @GetMapping("/unknown")
    public String unknown() {
        // Відповідь має точно збігатися з тим, що перевіряє 10-й тест
        return "Hello Unknown Route (Just Authenticated)!";
    }

    // Цей роут потрібен для 1-го та 9-го тестів
    @GetMapping("/index")
    public String index() {
        return "Hello Security (Permit All)!";
    }
}
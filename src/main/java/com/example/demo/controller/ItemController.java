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
        return "Hello Man (Admin or SuperAdmin)!";
    }

    @GetMapping("/unknown")
    public String unknown() {
        return "Hello Unknown Route (Just Authenticated)!";
    }

    @GetMapping("/index")
    public String index() {
        return "Hello Security (Permit All)!";
    }
}
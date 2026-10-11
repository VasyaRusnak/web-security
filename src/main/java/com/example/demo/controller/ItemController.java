package com.example.demo.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @GetMapping("/hello/admin")
    public String helloAdmin() { return "Hello Admin!"; }

    @GetMapping("/hello/user")
    public String helloUser() { return "Hello User!"; }

    @GetMapping("/hello/man")
    public String helloMan() { return "Hello Man!"; }

    @GetMapping("/unknown")
    public String unknown() { return "Hello Unknown Route!"; }
}
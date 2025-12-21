package com.example.msauth.web;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/secure")
    public String secure(Authentication authentication) {
        return "Hello " + authentication.getName();
    }
}
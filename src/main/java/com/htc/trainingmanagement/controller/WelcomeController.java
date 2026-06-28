package com.htc.trainingmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Training Management System";
    }

    @GetMapping("/home")
    public String home() {
        return "Login Successful";
    }
}
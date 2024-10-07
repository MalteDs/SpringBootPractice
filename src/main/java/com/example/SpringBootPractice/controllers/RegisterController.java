package com.example.SpringBootPractice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.SpringBootPractice.services.UserService;
import com.example.SpringBootPractice.model.User;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String registerUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return "User registered successfully";
        } catch (Exception e) {
            return "Error registering user";
        }
    }
    
}

package com.example.spring_playground.controller;



import com.example.spring_playground.dto.UserRequestDTO;
import com.example.spring_playground.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import com.example.spring_playground.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/foo")
    public Optional<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("/create")
    public Optional<UserResponseDTO> createUser(@RequestBody UserRequestDTO dto) {
        UserResponseDTO res = userService.createUser(dto);
        return Optional.ofNullable(res);
    }
}


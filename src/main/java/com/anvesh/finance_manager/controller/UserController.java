package com.anvesh.finance_manager.controller;

import com.anvesh.finance_manager.dto.LoginRequestDTO;
import com.anvesh.finance_manager.dto.LoginResponseDTO;
import com.anvesh.finance_manager.dto.UserResponseDTO;
import com.anvesh.finance_manager.entity.User;
import com.anvesh.finance_manager.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(
            @RequestBody User user
    ) {

        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO request
    ) {

        return userService.login(request);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(
            @PathVariable Long id
    ) {

        return userService.getUserById(id);
    }
}
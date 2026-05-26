package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.dto.LoginRequestDTO;
import com.anvesh.finance_manager.dto.LoginResponseDTO;
import com.anvesh.finance_manager.dto.UserResponseDTO;

import com.anvesh.finance_manager.entity.User;

import com.anvesh.finance_manager.repository.UserRepository;

import com.anvesh.finance_manager.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // REGISTER USER
    public User registerUser(
            User user
    ) {

        // VALIDATE NAME
        if (
                user.getName() == null
                        || user.getName().trim().isEmpty()
        ) {

            throw new RuntimeException(
                    "Name is required"
            );
        }

        // VALIDATE EMAIL
        if (
                user.getEmail() == null
                        || user.getEmail().trim().isEmpty()
        ) {

            throw new RuntimeException(
                    "Email is required"
            );
        }

        // NORMALIZE PASSWORD
        String password =
                user.getPassword() == null
                        ? ""
                        : user.getPassword().trim();

        // VALIDATE PASSWORD
        if (
                password.length() < 6
        ) {

            throw new RuntimeException(
                    "Password must be at least 6 characters"
            );
        }

        // NORMALIZE EMAIL
        String email =
                user.getEmail()
                        .trim()
                        .toLowerCase();

        // CHECK DUPLICATE EMAIL
        if (
                userRepository.existsByEmail(
                        email
                )
        ) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        user.setEmail(email);

        // ENCODE PASSWORD
        user.setPassword(
                passwordEncoder.encode(
                        password
                )
        );

        return userRepository.save(user);
    }

    // LOGIN USER
    public LoginResponseDTO login(
            LoginRequestDTO request
    ) {

        String email =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Invalid email or password"
                                )
                        );

        // PASSWORD CHECK
        if (
                !passwordEncoder.matches(
                        request.getPassword().trim(),
                        user.getPassword()
                )
        ) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        // GENERATE JWT
        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new LoginResponseDTO(
                "Login successful",
                token,
                "Bearer"
        );
    }

    // GET ALL USERS
    public List<UserResponseDTO> getAllUsers() {

        return userRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET USER BY ID
    public UserResponseDTO getUserById(
            Long id
    ) {

        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "User not found"
                                )
                        );

        return convertToDTO(user);
    }

    // CONVERT ENTITY TO DTO
    private UserResponseDTO convertToDTO(
            User user
    ) {

        UserResponseDTO dto =
                new UserResponseDTO();

        dto.setId(
                user.getId()
        );

        dto.setName(
                user.getName()
        );

        dto.setEmail(
                user.getEmail()
        );

        return dto;
    }
}
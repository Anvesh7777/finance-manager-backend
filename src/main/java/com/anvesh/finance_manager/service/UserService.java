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
    public User registerUser(User user) {

        if (
                userRepository
                        .findByEmail(user.getEmail())
                        .isPresent()
        ) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return userRepository.save(user);
    }

    // LOGIN USER
    public LoginResponseDTO login(
            LoginRequestDTO request
    ) {

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        if (
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )
        ) {

            throw new RuntimeException(
                    "Invalid password"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new LoginResponseDTO(
                "Login successful",
                token
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
                        .orElse(null);

        if (user == null) {

            return null;
        }

        return convertToDTO(user);
    }

    // CONVERT ENTITY TO DTO
    private UserResponseDTO convertToDTO(
            User user
    ) {

        UserResponseDTO dto =
                new UserResponseDTO();

        dto.setId(user.getId());

        dto.setName(user.getName());

        dto.setEmail(user.getEmail());

        return dto;
    }
}
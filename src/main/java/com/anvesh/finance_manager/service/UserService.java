package com.anvesh.finance_manager.service;

import com.anvesh.finance_manager.dto.UserResponseDTO;
import com.anvesh.finance_manager.entity.User;
import com.anvesh.finance_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anvesh.finance_manager.dto.LoginRequestDTO;
import com.anvesh.finance_manager.security.JwtService;
import com.anvesh.finance_manager.dto.LoginResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        return convertToDTO(user);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {

            return new LoginResponseDTO(
                    "User not found",
                    null
            );
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            return new LoginResponseDTO(
                    "Invalid password",
                    null
            );
        }

        String token =
                jwtService.generateToken(user.getEmail());

        return new LoginResponseDTO(
                "Login successful",
                token
        );
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
}
package com.anvesh.finance_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String message;

    private String token;

    // OPTIONAL
    private String tokenType = "Bearer";
}
package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Email not will be null")
    @Email
    private String email;
    @NotBlank
    private String password;
    private String fristName;
    private  String lastName;
}

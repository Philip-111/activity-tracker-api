package com.example.activitytracker.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "please provide your name")
    private String name;
    @Email(message = "Invalid email")
    @NotBlank(message = "email field is required")
    private String email;
    @NotBlank(message = "invalid password")
    private String password;
}

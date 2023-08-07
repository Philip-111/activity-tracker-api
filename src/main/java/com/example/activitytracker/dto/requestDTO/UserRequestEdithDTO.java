package com.example.activitytracker.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequestEdithDTO {
    @NotBlank(message = "please provide your name")
    private String name;
    @Email(message = "Invalid email")
    @NotBlank(message = "email field is a required field")
    private String email;
}

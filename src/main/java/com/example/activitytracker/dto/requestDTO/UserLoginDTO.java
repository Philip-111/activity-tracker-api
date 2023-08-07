package com.example.activitytracker.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @Email(message = "Invalid email")
    @NotEmpty(message = "email field is required")
    private String email;
    @NotEmpty(message = "Invalid password")
    private String password;
}

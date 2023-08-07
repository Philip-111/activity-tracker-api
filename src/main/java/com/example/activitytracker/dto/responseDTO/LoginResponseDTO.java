package com.example.activitytracker.dto.responseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private Long id;
    private String name;
    private String email;
}

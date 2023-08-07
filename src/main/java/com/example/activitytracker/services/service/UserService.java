package com.example.activitytracker.services.service;

import com.example.activitytracker.dto.requestDTO.UserLoginDTO;
import com.example.activitytracker.dto.requestDTO.UserRequest;
import com.example.activitytracker.dto.requestDTO.UserRequestEdithDTO;
import com.example.activitytracker.dto.responseDTO.LoginResponseDTO;
import com.example.activitytracker.dto.responseDTO.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserRequest userRequestDTO);
    LoginResponseDTO login(UserLoginDTO request);

    UserResponseDTO updateUser(UserRequestEdithDTO request, Long userId);

    String deleteUserById(Long userId);
}

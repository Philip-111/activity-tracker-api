package com.example.activitytracker.services;

import com.example.activitytracker.dto.requestDTO.UserLoginDTO;
import com.example.activitytracker.dto.requestDTO.UserRequest;
import com.example.activitytracker.dto.requestDTO.UserRequestEdithDTO;
import com.example.activitytracker.dto.responseDTO.LoginResponseDTO;
import com.example.activitytracker.dto.responseDTO.UserResponseDTO;
import com.example.activitytracker.entities.AppUser;
import com.example.activitytracker.exception.PasswordAndEmailNotFoundException;
import com.example.activitytracker.exception.myOwnException;
import com.example.activitytracker.repository.UserRepository;
import com.example.activitytracker.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public AppUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO registerUser(UserRequest userRequestDTO) {
        Optional<AppUser> user = userRepository.findByEmail(userRequestDTO.getEmail());
        if (user.isPresent()) {
            throw new myOwnException("Person don dey database Bro!", HttpStatus.CONFLICT);
        }

        AppUser newUser = AppUser.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();

        AppUser savedUser = userRepository.save(newUser);

        return UserResponseDTO.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public LoginResponseDTO login(UserLoginDTO request) {
        AppUser appUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new PasswordAndEmailNotFoundException("Person wey get the email nor dey our database bros",HttpStatus.NOT_FOUND));

        if (!request.getPassword().equals(appUser.getPassword())) {
            throw new PasswordAndEmailNotFoundException("Omo check your email or your password", HttpStatus.NOT_FOUND);
        }

        LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .id(appUser.getId())
                .name(appUser.getName())
                .email(appUser.getEmail())
                .build();

        return responseDTO;
    }

    @Override
    public UserResponseDTO updateUser(UserRequestEdithDTO request, Long userId) {

        Optional<AppUser> userOptional = userRepository.findById(userId);


        if (userOptional.isEmpty()) {
            throw new myOwnException("User not found", HttpStatus.NOT_FOUND);
        }

        AppUser user = userOptional.get();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        AppUser updatedUser = userRepository.save(user);

        UserResponseDTO responseDTO = UserResponseDTO.builder()
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .build();

        return responseDTO;
    }

    @Override
    public String deleteUserById(Long userId) {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new myOwnException("User not found", HttpStatus.NOT_FOUND);
        }
        AppUser user = userOptional.get();
        userRepository.delete(user);

        return user.getName() + " Is deleted";
    }





}

package com.example.spring_playground.service;

import com.example.spring_playground.dto.UserRequestDTO;
import com.example.spring_playground.dto.UserResponseDTO;
import com.example.spring_playground.entity.User;
import com.example.spring_playground.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.spring_playground.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserResponseDTO> getAllUsers() {
        System.out.println("getAllUsers------>");
        Optional<User> userOptional = this.userRepository.findById(Long.valueOf(1));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDTO userResponseDTO = UserMapper.INSTANCE.userToUserResponseDto(user);
            return Optional.ofNullable(userResponseDTO);
        } else {
            return Optional.empty();
        }
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = UserMapper.INSTANCE.userRequestDtoToUser(dto);
        User createdUser  = this.userRepository.save(user);
        return UserMapper.INSTANCE.userToUserResponseDto(createdUser);
    }
}

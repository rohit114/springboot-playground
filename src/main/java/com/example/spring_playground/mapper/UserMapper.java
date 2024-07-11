package com.example.spring_playground.mapper;

import com.example.spring_playground.dto.UserRequestDTO;
import com.example.spring_playground.dto.UserResponseDTO;
import com.example.spring_playground.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDTO userToUserResponseDto(User user);
    User userRequestDtoToUser(UserRequestDTO dto);
}

package com.htc.trainingmanagement.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.UserRequestDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.entity.User;
import com.htc.trainingmanagement.enums.RoleName;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto requestDto) {

        User user = new User();
        user.setUserName(requestDto.getUserName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());

        return user;
    }

    public UserResponseDto toResponse(User user) {

        Set<RoleName> roleNames = user.getRoles()
                .stream()
                .map(role -> role.getRoleName())
                .collect(Collectors.toSet());

        return new UserResponseDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                roleNames,
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    public void updateEntity(User user, UserRequestDto requestDto) {

        user.setUserName(requestDto.getUserName());
        user.setEmail(requestDto.getEmail());
    }
}
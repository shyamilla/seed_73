package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.UserRequestDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.entity.User;

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
        return new UserResponseDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    public void updateEntity(User user, UserRequestDto requestDto) {

        user.setUserName(requestDto.getUserName());
        user.setEmail(requestDto.getEmail());

    }
}
// Enum-to-Entity conversion requires repository access, so role mapping belongs
// in the Service layer, not the Mapper.

package com.htc.trainingmanagement.serviceimpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.SetupAdminRequestDto;
import com.htc.trainingmanagement.dto.request.UserRequestDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.entity.Role;
import com.htc.trainingmanagement.enums.RoleName;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.repository.RoleRepository;
import com.htc.trainingmanagement.repository.UserRepository;
import com.htc.trainingmanagement.service.AuthService;
import com.htc.trainingmanagement.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Value("${app.setup.key}")
    private String setupKey;

    @Override
    public UserResponseDto setupFirstAdmin(SetupAdminRequestDto requestDto)
            throws DuplicateResourceException, ResourceNotFoundException {

        if (userRepository.count() > 0) {
            throw new RuntimeException("Admin setup is already completed");
        }

        if (!setupKey.equals(requestDto.getSetupKey())) {
            throw new RuntimeException("Invalid setup key");
        }

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserName(requestDto.getUserName());
        userRequestDto.setEmail(requestDto.getEmail());
        userRequestDto.setPassword(requestDto.getPassword());
        userRequestDto.setRoles(Set.of(RoleName.ADMIN));

        return userService.createUser(userRequestDto);
    }

    @Override
    public void setupRoles() {
        createRoleIfNotExists(RoleName.ADMIN);
        createRoleIfNotExists(RoleName.TRAINER);
        createRoleIfNotExists(RoleName.TRAINEE);
    }

    private void createRoleIfNotExists(RoleName roleName) {
        if (roleRepository.findByRoleName(roleName).isEmpty()) {
            Role role = new Role();
            role.setRoleName(roleName);
            roleRepository.save(role);
        }
    }
}
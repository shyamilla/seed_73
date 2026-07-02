package com.htc.trainingmanagement.serviceimpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.config.JwtService;
import com.htc.trainingmanagement.dto.request.LoginRequestDto;
import com.htc.trainingmanagement.dto.request.RefreshTokenRequestDto;
import com.htc.trainingmanagement.dto.request.SetupAdminRequestDto;
import com.htc.trainingmanagement.dto.request.UserRequestDto;
import com.htc.trainingmanagement.dto.response.LoginResponseDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.entity.RefreshToken;
import com.htc.trainingmanagement.entity.Role;
import com.htc.trainingmanagement.entity.User;
import com.htc.trainingmanagement.enums.RoleName;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.exception.UserException;
import com.htc.trainingmanagement.repository.RoleRepository;
import com.htc.trainingmanagement.repository.UserRepository;
import com.htc.trainingmanagement.service.AuthService;
import com.htc.trainingmanagement.service.RefreshTokenService;
import com.htc.trainingmanagement.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
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

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto)
            throws ResourceNotFoundException, UserException {

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with email: " + requestDto.getEmail()));

        if (!user.getIsActive()) {
            throw new UserException("Inactive user cannot log in.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getEmail());

        String accessToken = jwtService.generateToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponseDto(accessToken, refreshToken.getToken());
    }

    @Override
    public void logout(RefreshTokenRequestDto requestDto) {
        refreshTokenService.deleteRefreshToken(requestDto.getRefreshToken());
    }

    @Override
    public LoginResponseDto refreshToken(RefreshTokenRequestDto requestDto)
            throws ResourceNotFoundException {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(requestDto.getRefreshToken());

        User user = refreshToken.getUser();

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        String newAccessToken = jwtService.generateToken(userDetails);

        return new LoginResponseDto(newAccessToken, refreshToken.getToken());
    }
}
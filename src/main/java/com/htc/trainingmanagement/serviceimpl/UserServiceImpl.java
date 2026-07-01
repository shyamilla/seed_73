package com.htc.trainingmanagement.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.UserRequestDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.entity.Role;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.entity.User;
import com.htc.trainingmanagement.enums.RoleName;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.UserMapper;
import com.htc.trainingmanagement.repository.RoleRepository;
import com.htc.trainingmanagement.repository.TraineeRepository;
import com.htc.trainingmanagement.repository.TrainerRepository;
import com.htc.trainingmanagement.repository.UserRepository;
import com.htc.trainingmanagement.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final UserMapper userMapper;
        private final PasswordEncoder passwordEncoder;
        private final TrainerRepository trainerRepository;
        private final TraineeRepository traineeRepository;

        @Override
        public UserResponseDto createUser(UserRequestDto requestDto)
                        throws DuplicateResourceException, ResourceNotFoundException {

                if (userRepository.existsByEmail(requestDto.getEmail())) {
                        throw new DuplicateResourceException(
                                        "User already exists with email: "
                                                        + requestDto.getEmail());
                }

                User user = userMapper.toEntity(requestDto);

                user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

                user.setRoles(getRoles(requestDto.getRoles()));

                User savedUser = userRepository.save(user);

                createProfiles(savedUser);

                return userMapper.toResponse(savedUser);
        }

        @Override
        public UserResponseDto updateUser(Long userId, UserRequestDto requestDto)
                        throws ResourceNotFoundException, DuplicateResourceException {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with ID: " + userId + " to update"));

                // Checks if another user already has the requested email.
                if (!user.getEmail().equalsIgnoreCase(requestDto.getEmail())
                                && userRepository.existsByEmail(requestDto.getEmail())) {

                        throw new DuplicateResourceException(
                                        "User already exists with email: " + requestDto.getEmail());
                }

                userMapper.updateEntity(user, requestDto);

                // Encodes the password before saving it.
                user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
                user.setRoles(getRoles(requestDto.getRoles()));

                User updatedUser = userRepository.save(user);

                // Creates trainer or trainee profile if the updated role requires it.
                createProfiles(updatedUser);

                return userMapper.toResponse(updatedUser);
        }

        @Override
        public boolean deleteUser(Long userId) throws ResourceNotFoundException {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with ID: " + userId + " to delete"));

                userRepository.delete(user);

                return true;
        }

        @Override
        public UserResponseDto getUserById(Long userId) throws ResourceNotFoundException {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with ID: " + userId + " to fetch"));

                return userMapper.toResponse(user);
        }

        @Override
        public List<UserResponseDto> getAllUsers() {

                return userRepository.findAll()
                                .stream()
                                .map(userMapper::toResponse)
                                .toList();
        }

        // used in create and update
        private Set<Role> getRoles(Set<RoleName> roleNames) throws ResourceNotFoundException {

                if (roleNames == null || roleNames.isEmpty()) {
                        return new HashSet<>();
                }

                Set<Role> roles = new HashSet<>();

                for (RoleName roleName : roleNames) {
                        Role role = roleRepository.findByRoleName(roleName)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Role not found: " + roleName));
                        roles.add(role);
                }
                return roles;
        }

        // business logics (other than crud)

        @Override
        public List<UserResponseDto> getUsersByRole(RoleName roleName)
                        throws ResourceNotFoundException {

                Role role = roleRepository.findByRoleName(roleName)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Role not found: " + roleName));

                return userRepository.findByRolesContaining(role)
                                .stream()
                                .map(userMapper::toResponse)
                                .toList();
        }

        @Override
        public UserResponseDto assignRole(Long userId, RoleName roleName)
                        throws ResourceNotFoundException, DuplicateResourceException {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with ID: " + userId));

                Role role = roleRepository.findByRoleName(roleName)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Role not found: " + roleName));

                if (user.getRoles().contains(role)) {
                        throw new DuplicateResourceException(
                                        "User already has role: " + roleName);
                }

                user.getRoles().add(role);

                User updatedUser = userRepository.save(user);

                createProfiles(updatedUser);

                return userMapper.toResponse(updatedUser);
        }

        @Override
        public UserResponseDto removeRole(Long userId, RoleName roleName)
                        throws ResourceNotFoundException {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with ID: " + userId));

                Role role = roleRepository.findByRoleName(roleName)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Role not found: " + roleName));

                if (!user.getRoles().contains(role)) {
                        throw new ResourceNotFoundException(
                                        "User does not have role: " + roleName);
                }

                user.getRoles().remove(role);

                User updatedUser = userRepository.save(user);

                return userMapper.toResponse(updatedUser);
        }

        @Override
        public UserResponseDto changePassword(Long userId, String newPassword)
                        throws ResourceNotFoundException {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with ID: " + userId));

                user.setPassword(passwordEncoder.encode(newPassword));

                User updatedUser = userRepository.save(user);

                return userMapper.toResponse(updatedUser);
        }

        // helper

        private void createProfiles(User user) {

                for (Role role : user.getRoles()) {

                        if (role.getRoleName() == RoleName.TRAINER
                                        && !trainerRepository.existsByUser(user)) {

                                Trainer trainer = new Trainer();
                                trainer.setUser(user);

                                trainerRepository.save(trainer);
                        }

                        if (role.getRoleName() == RoleName.TRAINEE
                                        && !traineeRepository.existsByUser(user)) {

                                Trainee trainee = new Trainee();
                                trainee.setUser(user);

                                traineeRepository.save(trainee);
                        }
                }
        }

        @Override
        public List<UserResponseDto> getInactiveUsers() {

                return userRepository.findByIsActiveFalse()
                                .stream()
                                .map(userMapper::toResponse)
                                .toList();
        }

        @Override
        public List<UserResponseDto> getActiveUsers() {

                return userRepository.findByIsActiveTrue()
                                .stream()
                                .map(userMapper::toResponse)
                                .toList();
        }

}

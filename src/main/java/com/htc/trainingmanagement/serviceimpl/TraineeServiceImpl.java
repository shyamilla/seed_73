package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.entity.User;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.TraineeMapper;
import com.htc.trainingmanagement.repository.TraineeRepository;
import com.htc.trainingmanagement.repository.UserRepository;
import com.htc.trainingmanagement.service.TraineeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final UserRepository userRepository;
    private final TraineeMapper traineeMapper;

    @Override
    public TraineeResponseDto createTrainee(TraineeRequestDto requestDto) throws ResourceNotFoundException {

        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("user not found with id: "));

        Trainee trainee = traineeMapper.toEntity(requestDto, user);
        Trainee saveTrainee = traineeRepository.save(trainee);
        return traineeMapper.toResponseDto(saveTrainee);
    }

    @Override
    public TraineeResponseDto getTraineeById(Long traineeId) throws ResourceNotFoundException {

        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id: " + traineeId));

        return traineeMapper.toResponseDto(trainee);
    }

    @Override
    public List<TraineeResponseDto> getAllTrainees() {

        return traineeRepository.findAll()
                .stream()
                .map(traineeMapper::toResponseDto)
                .toList();
    }

    @Override
    public TraineeResponseDto updateTrainee(
            Long traineeId,
            TraineeRequestDto requestDto) throws ResourceNotFoundException {

        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id: " + traineeId));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + requestDto.getUserId()));

        trainee.setUser(user);
        traineeMapper.updateEntity(trainee, requestDto);

        Trainee updateTrainee = traineeRepository.save(trainee);

        return traineeMapper.toResponseDto(updateTrainee);
    }

    @Override
    public boolean deleteTrainee(Long traineeId) throws ResourceNotFoundException {

        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id: " + traineeId));

        traineeRepository.delete(trainee);

        return true;
    }

}
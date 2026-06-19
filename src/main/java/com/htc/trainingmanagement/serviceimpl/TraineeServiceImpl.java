package com.htc.trainingmanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.repository.TraineeRepository;
import com.htc.trainingmanagement.service.TraineeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;

    @Override
    public TraineeResponseDto createTrainee(TraineeRequestDto requestDto) {

        Trainee trainee = new Trainee();

        trainee.setFirstName(requestDto.getFirstName());
        trainee.setLastName(requestDto.getLastName());
        trainee.setEmail(requestDto.getEmail());
        trainee.setPhoneNumber(requestDto.getPhoneNumber());
        trainee.setDepartment(requestDto.getDepartment());
        trainee.setDesignation(requestDto.getDesignation());
        trainee.setJoiningDate(requestDto.getJoiningDate());
        trainee.setStatus(requestDto.getStatus());

        Trainee savedTrainee = traineeRepository.save(trainee);

        return convertToResponseDto(savedTrainee);
    }

    @Override
    public TraineeResponseDto getTraineeById(Long traineeId) throws ResourceNotFoundException {

        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id: " + traineeId));

        return convertToResponseDto(trainee);
    }

    @Override
    public List<TraineeResponseDto> getAllTrainees() {

        List<Trainee> trainees = traineeRepository.findAll();

        List<TraineeResponseDto> responseDtos = new ArrayList<>();

        for (Trainee trainee : trainees) {
            responseDtos.add(convertToResponseDto(trainee));
        }

        return responseDtos;
    }

    @Override
    public TraineeResponseDto updateTrainee(
            Long traineeId,
            TraineeRequestDto requestDto) throws ResourceNotFoundException {

        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id: " + traineeId));

        trainee.setFirstName(requestDto.getFirstName());
        trainee.setLastName(requestDto.getLastName());
        trainee.setEmail(requestDto.getEmail());
        trainee.setPhoneNumber(requestDto.getPhoneNumber());
        trainee.setDepartment(requestDto.getDepartment());
        trainee.setDesignation(requestDto.getDesignation());
        trainee.setJoiningDate(requestDto.getJoiningDate());
        trainee.setStatus(requestDto.getStatus());

        Trainee updatedTrainee = traineeRepository.save(trainee);

        return convertToResponseDto(updatedTrainee);
    }

    @Override
    public boolean deleteTrainee(Long traineeId) throws ResourceNotFoundException {

        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id: " + traineeId));

        traineeRepository.delete(trainee);

        return true;
    }

    private TraineeResponseDto convertToResponseDto(
            Trainee trainee) {

        return new TraineeResponseDto(
                trainee.getTraineeId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getEmail(),
                trainee.getPhoneNumber(),
                trainee.getDepartment(),
                trainee.getDesignation(),
                trainee.getJoiningDate(),
                trainee.getStatus(),
                trainee.getCreatedAt(),
                trainee.getUpdatedAt());
    }
}
package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.enums.TraineeStatus;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.TraineeMapper;
import com.htc.trainingmanagement.repository.TraineeRepository;
import com.htc.trainingmanagement.service.TraineeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

        private final TraineeRepository traineeRepository;
        private final TraineeMapper traineeMapper;

        @Override
        public TraineeResponseDto getTraineeById(Long traineeId)
                        throws ResourceNotFoundException {

                Trainee trainee = getTraineeEntityById(traineeId);

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
                        TraineeRequestDto requestDto)
                        throws ResourceNotFoundException {

                Trainee trainee = getTraineeEntityById(traineeId);

                traineeMapper.updateEntity(trainee, requestDto);

                Trainee updatedTrainee = traineeRepository.save(trainee);

                return traineeMapper.toResponseDto(updatedTrainee);
        }

        @Override
        public boolean deleteTrainee(Long traineeId)
                        throws ResourceNotFoundException {

                Trainee trainee = getTraineeEntityById(traineeId);

                traineeRepository.delete(trainee);

                return true;
        }

        @Override
        public List<TraineeResponseDto> getTraineesByDepartment(String department) {

                return traineeRepository.findByDepartmentIgnoreCase(department)
                                .stream()
                                .map(traineeMapper::toResponseDto)
                                .toList();
        }

        @Override
        public List<TraineeResponseDto> getTraineesByStatus(TraineeStatus status) {

                return traineeRepository.findByStatus(status)
                                .stream()
                                .map(traineeMapper::toResponseDto)
                                .toList();
        }

        @Override
        public TraineeResponseDto updateTraineeStatus(
                        Long traineeId,
                        TraineeStatus status)
                        throws ResourceNotFoundException {

                Trainee trainee = getTraineeEntityById(traineeId);

                // Updates only the trainee status.
                trainee.setStatus(status);

                Trainee updatedTrainee = traineeRepository.save(trainee);

                return traineeMapper.toResponseDto(updatedTrainee);
        }

        private Trainee getTraineeEntityById(Long traineeId)
                        throws ResourceNotFoundException {

                return traineeRepository.findById(traineeId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + traineeId));
        }
}
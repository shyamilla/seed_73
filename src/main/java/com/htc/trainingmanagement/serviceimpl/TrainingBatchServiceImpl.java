package com.htc.trainingmanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.TrainingBatchRequestDto;
import com.htc.trainingmanagement.dto.response.TrainingBatchResponseDto;
import com.htc.trainingmanagement.entity.Course;
import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.entity.TrainingBatch;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.TrainingBatchMapper;
import com.htc.trainingmanagement.repository.CourseRepository;
import com.htc.trainingmanagement.repository.TrainerRepository;
import com.htc.trainingmanagement.repository.TrainingBatchRepository;
import com.htc.trainingmanagement.service.TrainingBatchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainingBatchServiceImpl implements TrainingBatchService {

        private final TrainingBatchRepository trainingBatchRepository;
        private final CourseRepository courseRepository;
        private final TrainerRepository trainerRepository;
        private final TrainingBatchMapper trainingBatchMapper;

        @Override
        public TrainingBatchResponseDto createTrainingBatch(
                        TrainingBatchRequestDto requestDto) throws ResourceNotFoundException {

                TrainingBatch trainingBatch = trainingBatchMapper.toEntity(requestDto);
                TrainingBatch saveBatch = trainingBatchRepository.save(trainingBatch);
                return trainingBatchMapper.toResponseDto(saveBatch);
        }

        @Override
        public TrainingBatchResponseDto getTrainingBatchById(
                        Long trainingBatchId) throws ResourceNotFoundException {

                TrainingBatch batch = trainingBatchRepository.findById(
                                trainingBatchId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: "
                                                                + trainingBatchId));

                return trainingBatchMapper.toResponseDto(batch);
        }

        @Override
        public List<TrainingBatchResponseDto> getAllTrainingBatch() {

                return trainingBatchRepository.findAll().stream()
                                .map(trainingBatchMapper::toResponseDto)
                                .toList();
        }

        @Override
        public TrainingBatchResponseDto updateTrainingBatch(
                        Long trainingBatchId,
                        TrainingBatchRequestDto requestDto) throws ResourceNotFoundException {

                TrainingBatch trainingBatch = trainingBatchRepository.findById(trainingBatchId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: "
                                                                + trainingBatchId));

                trainingBatchMapper.updateEntity(trainingBatch, requestDto);
                TrainingBatch updatedBatch = trainingBatchRepository.save(trainingBatch);

                return trainingBatchMapper.toResponseDto(updatedBatch);
        }

        @Override
        public boolean deleteTrainingBatch(Long trainingBatchId) throws ResourceNotFoundException {

                TrainingBatch batch = trainingBatchRepository.findById(
                                trainingBatchId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: "
                                                                + trainingBatchId));

                trainingBatchRepository.delete(batch);

                return true;
        }

}
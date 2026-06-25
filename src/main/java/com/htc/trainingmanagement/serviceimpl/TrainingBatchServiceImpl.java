package com.htc.trainingmanagement.serviceimpl;

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
    private final TrainingBatchMapper trainingBatchMapper;
    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public TrainingBatchResponseDto createTrainingBatch(
            TrainingBatchRequestDto requestDto) throws ResourceNotFoundException {

        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + requestDto.getCourseId()));

        Trainer trainer = trainerRepository.findById(requestDto.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainer not found with id: " + requestDto.getTrainerId()));

        TrainingBatch trainingBatch = trainingBatchMapper.toEntity(
                requestDto, course, trainer);

        TrainingBatch savedBatch = trainingBatchRepository.save(trainingBatch);

        return trainingBatchMapper.toResponseDto(savedBatch);
    }

    @Override
    public TrainingBatchResponseDto getTrainingBatchById(Long trainingBatchId) throws ResourceNotFoundException {

        TrainingBatch batch = trainingBatchRepository.findById(trainingBatchId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training Batch not found with id: " + trainingBatchId));

        return trainingBatchMapper.toResponseDto(batch);
    }

    @Override
    public List<TrainingBatchResponseDto> getAllTrainingBatch() {

        return trainingBatchRepository.findAll()
                .stream()
                .map(trainingBatchMapper::toResponseDto)
                .toList();
    }

    @Override
    public TrainingBatchResponseDto updateTrainingBatch(
            Long trainingBatchId,
            TrainingBatchRequestDto requestDto) throws ResourceNotFoundException {

        TrainingBatch trainingBatch = trainingBatchRepository.findById(trainingBatchId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training Batch not found with id: " + trainingBatchId));

        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + requestDto.getCourseId()));

        Trainer trainer = trainerRepository.findById(requestDto.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainer not found with id: " + requestDto.getTrainerId()));

        trainingBatchMapper.updateEntity(trainingBatch, requestDto);

        trainingBatch.setCourse(course);
        trainingBatch.setTrainer(trainer);

        TrainingBatch updatedBatch = trainingBatchRepository.save(trainingBatch);

        return trainingBatchMapper.toResponseDto(updatedBatch);
    }

    @Override
    public boolean deleteTrainingBatch(Long trainingBatchId) throws ResourceNotFoundException {

        TrainingBatch batch = trainingBatchRepository.findById(trainingBatchId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training Batch not found with id: " + trainingBatchId));

        trainingBatchRepository.delete(batch);

        return true;
    }
}
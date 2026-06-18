package com.htc.trainingmanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.TrainingBatchRequestDto;
import com.htc.trainingmanagement.dto.response.TrainingBatchResponseDto;
import com.htc.trainingmanagement.entity.Course;
import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.entity.TrainingBatch;
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

    @Override
    public TrainingBatchResponseDto createTrainingBatch(TrainingBatchRequestDto requestDto) {

        Course course = courseRepository.findById(requestDto.getCourseId()).orElseThrow();

        Trainer trainer = trainerRepository.findById(requestDto.getTrainerId()).orElseThrow();

        TrainingBatch trainingBatch = new TrainingBatch();

        trainingBatch.setBatchCode(requestDto.getBatchCode());
        trainingBatch.setBatchName(requestDto.getBatchName());
        trainingBatch.setStartDate(requestDto.getStartDate());
        trainingBatch.setEndDate(requestDto.getEndDate());
        trainingBatch.setCapacity(requestDto.getCapacity());
        trainingBatch.setEnrolledCount(requestDto.getEnrolledCount());
        trainingBatch.setStatus(requestDto.getStatus());
        trainingBatch.setCourse(course);
        trainingBatch.setTrainer(trainer);

        TrainingBatch savedBatch = trainingBatchRepository.save(trainingBatch);

        return convertToResponseDto(savedBatch);
    }

    @Override
    public TrainingBatchResponseDto getTrainingBatchById(Long trainingBatchId) {

        TrainingBatch batch = trainingBatchRepository.findById(trainingBatchId).orElseThrow();

        return convertToResponseDto(batch);
    }

    @Override
    public List<TrainingBatchResponseDto> getAllTrainingBatch() {

        List<TrainingBatch> batches = trainingBatchRepository.findAll();

        List<TrainingBatchResponseDto> responseDtos = new ArrayList<>();

        for (TrainingBatch batch : batches) {
            responseDtos.add(convertToResponseDto(batch));
        }

        return responseDtos;
    }

    @Override
    public TrainingBatchResponseDto updateTrainingBatch(Long trainingBatchId, TrainingBatchRequestDto requestDto) {

        TrainingBatch trainingBatch = trainingBatchRepository.findById(trainingBatchId).orElseThrow();

        Course course = courseRepository.findById(requestDto.getCourseId()).orElseThrow();

        Trainer trainer = trainerRepository.findById(requestDto.getTrainerId()).orElseThrow();

        trainingBatch.setBatchCode(requestDto.getBatchCode());
        trainingBatch.setBatchName(requestDto.getBatchName());
        trainingBatch.setStartDate(requestDto.getStartDate());
        trainingBatch.setEndDate(requestDto.getEndDate());
        trainingBatch.setCapacity(requestDto.getCapacity());
        trainingBatch.setEnrolledCount(requestDto.getEnrolledCount());
        trainingBatch.setStatus(requestDto.getStatus());
        trainingBatch.setCourse(course);
        trainingBatch.setTrainer(trainer);

        TrainingBatch updatedBatch = trainingBatchRepository.save(trainingBatch);

        return convertToResponseDto(updatedBatch);
    }

    @Override
    public boolean deleteTrainingBatch(Long trainingBatchId) {

        TrainingBatch batch = trainingBatchRepository.findById(trainingBatchId).orElseThrow();

        trainingBatchRepository.delete(batch);

        return true;
    }

    private TrainingBatchResponseDto convertToResponseDto(
            TrainingBatch batch) {

        return new TrainingBatchResponseDto(
                batch.getTrainingbatchId(),
                batch.getBatchCode(),
                batch.getBatchName(),
                batch.getStartDate(),
                batch.getEndDate(),
                batch.getCapacity(),
                batch.getEnrolledCount(),
                batch.getStatus(),
                batch.getCourse().getCourseId(),
                batch.getCourse().getCourseName(),
                batch.getTrainer().getTrainerId(),
                batch.getTrainer().getName(),
                batch.getCreatedAt(),
                batch.getUpdatedAt());
    }
}
package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.TrainingBatchRequestDto;
import com.htc.trainingmanagement.dto.response.TrainingBatchResponseDto;
import com.htc.trainingmanagement.entity.Course;
import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.entity.TrainingBatch;
import com.htc.trainingmanagement.enums.BatchStatus;
import com.htc.trainingmanagement.exception.BatchException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.exception.TrainerException;
import com.htc.trainingmanagement.mapper.TrainingBatchMapper;
import com.htc.trainingmanagement.repository.CourseRepository;
import com.htc.trainingmanagement.repository.EnrollmentRepository;
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
        private final EnrollmentRepository enrollmentRepository;

        @Override
        public TrainingBatchResponseDto createTrainingBatch(
                        TrainingBatchRequestDto requestDto)
                        throws ResourceNotFoundException, TrainerException, BatchException {

                // Validates that the batch end date is after the start date.
                validateBatchDates(requestDto);

                // Fetches the associated course.
                Course course = getCourseEntityById(requestDto.getCourseId());

                // Fetches the assigned trainer.
                Trainer trainer = getTrainerEntityById(requestDto.getTrainerId());

                validateTrainerIsActive(trainer);

                validateTrainerAvailability(trainer, requestDto);

                // Converts the request DTO into a TrainingBatch entity.
                TrainingBatch trainingBatch = trainingBatchMapper.toEntity(
                                requestDto, course, trainer);

                // Saves the training batch.
                TrainingBatch savedBatch = trainingBatchRepository.save(trainingBatch);

                // Converts the saved entity into a response DTO.
                return trainingBatchMapper.toResponseDto(savedBatch);
        }

        @Override
        public TrainingBatchResponseDto getTrainingBatchById(Long trainingBatchId)
                        throws ResourceNotFoundException {

                // Retrieves the training batch by its ID.
                TrainingBatch trainingBatch = getTrainingBatchEntityById(trainingBatchId);

                // Returns the training batch details.
                return trainingBatchMapper.toResponseDto(trainingBatch);
        }

        @Override
        public List<TrainingBatchResponseDto> getAllTrainingBatch() {

                // Retrieves all training batches.
                return trainingBatchRepository.findAll()
                                .stream()
                                .map(trainingBatchMapper::toResponseDto)
                                .toList();
        }

        @Override
        public TrainingBatchResponseDto updateTrainingBatch(
                        Long trainingBatchId,
                        TrainingBatchRequestDto requestDto)
                        throws ResourceNotFoundException, BatchException {

                // Validates that the batch end date is after the start date.
                validateBatchDates(requestDto);

                // Retrieves the existing training batch.
                TrainingBatch trainingBatch = getTrainingBatchEntityById(trainingBatchId);

                // Fetches the updated course.
                Course course = getCourseEntityById(requestDto.getCourseId());

                // Fetches the updated trainer.
                Trainer trainer = getTrainerEntityById(requestDto.getTrainerId());

                validateTrainerIsActive(trainer);

                // Updates the training batch details.
                trainingBatchMapper.updateEntity(trainingBatch, requestDto);

                // Updates the associated course and trainer.
                trainingBatch.setCourse(course);
                trainingBatch.setTrainer(trainer);

                // Saves the updated training batch.
                TrainingBatch updatedBatch = trainingBatchRepository.save(trainingBatch);

                // Returns the updated training batch.
                return trainingBatchMapper.toResponseDto(updatedBatch);
        }

        @Override
        public boolean deleteTrainingBatch(Long trainingBatchId)
                        throws ResourceNotFoundException, BatchException {

                TrainingBatch trainingBatch = getTrainingBatchEntityById(trainingBatchId);

                validateBatchCanBeDeleted(trainingBatch);

                trainingBatchRepository.delete(trainingBatch);

                return true;
        }

        @Override
        public List<TrainingBatchResponseDto> getBatchesByCourse(Long courseId)
                        throws ResourceNotFoundException {

                // Validates that the course exists.
                getCourseEntityById(courseId);

                // Retrieves all batches for the specified course.
                return trainingBatchRepository.findByCourseCourseId(courseId)
                                .stream()
                                .map(trainingBatchMapper::toResponseDto)
                                .toList();
        }

        @Override
        public List<TrainingBatchResponseDto> getBatchesByTrainer(Long trainerId)
                        throws ResourceNotFoundException {

                // Validates that the trainer exists.
                getTrainerEntityById(trainerId);

                // Retrieves all batches assigned to the specified trainer.
                return trainingBatchRepository.findByTrainerTrainerId(trainerId)
                                .stream()
                                .map(trainingBatchMapper::toResponseDto)
                                .toList();
        }

        @Override
        public TrainingBatchResponseDto updateBatchStatus(
                        Long trainingBatchId,
                        BatchStatus status)
                        throws ResourceNotFoundException {

                // Retrieves the training batch.
                TrainingBatch trainingBatch = getTrainingBatchEntityById(trainingBatchId);

                // Updates only the batch status.
                trainingBatch.setStatus(status);

                // Saves the updated training batch.
                TrainingBatch updatedBatch = trainingBatchRepository.save(trainingBatch);

                // Returns the updated training batch.
                return trainingBatchMapper.toResponseDto(updatedBatch);
        }

        // helper methods

        // Helper method to fetch a TrainingBatch by ID or throw an exception if not
        // found.
        private TrainingBatch getTrainingBatchEntityById(Long trainingBatchId)
                        throws ResourceNotFoundException {

                return trainingBatchRepository.findById(trainingBatchId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: " + trainingBatchId));
        }

        // Helper method to fetch a Course by ID or throw an exception if not found.
        private Course getCourseEntityById(Long courseId)
                        throws ResourceNotFoundException {

                return courseRepository.findById(courseId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Course not found with id: " + courseId));
        }

        // Helper method to fetch a Trainer by ID or throw an exception if not found.
        private Trainer getTrainerEntityById(Long trainerId)
                        throws ResourceNotFoundException {

                return trainerRepository.findById(trainerId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainer not found with id: " + trainerId));
        }

        // Helper method to validate that the batch end date is after the start date.
        private void validateBatchDates(TrainingBatchRequestDto requestDto) {

                if (!requestDto.getEndDate().isAfter(requestDto.getStartDate())) {
                        throw new IllegalArgumentException(
                                        "Batch end date must be after start date");
                }
        }

        // Helper method to validate that a trainer is not assigned to overlapping
        // batches.
        private void validateTrainerAvailability(
                        Trainer trainer,
                        TrainingBatchRequestDto requestDto)
                        throws TrainerException {

                List<TrainingBatch> batches = trainingBatchRepository.findByTrainerTrainerId(
                                trainer.getTrainerId());

                for (TrainingBatch batch : batches) {

                        boolean overlaps = !requestDto.getEndDate().isBefore(batch.getStartDate())
                                        && !requestDto.getStartDate().isAfter(batch.getEndDate());

                        if (overlaps) {
                                throw new TrainerException(
                                                "Trainer is already assigned to another batch during this period.");
                        }
                }
        }

        // Helper method to validate that the batch has no enrollments before deletion.
        private void validateBatchCanBeDeleted(TrainingBatch trainingBatch)
                        throws BatchException {

                if (enrollmentRepository.existsByTrainingBatch(trainingBatch)) {
                        throw new BatchException(
                                        "Training batch cannot be deleted because trainees are enrolled.");
                }
        }

        // Helper method to validate that inactive trainer cannot be assigned to a
        // batch.
        private void validateTrainerIsActive(Trainer trainer)
                        throws BatchException {

                if (!trainer.getIsActive()) {
                        throw new BatchException(
                                        "Inactive trainer cannot be assigned to a training batch.");
                }
        }
}
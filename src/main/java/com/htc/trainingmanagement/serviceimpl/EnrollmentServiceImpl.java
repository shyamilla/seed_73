package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.EnrollmentRequestDto;
import com.htc.trainingmanagement.dto.response.EnrollmentResponseDto;
import com.htc.trainingmanagement.entity.Enrollment;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.entity.TrainingBatch;
import com.htc.trainingmanagement.enums.EnrollmentStatus;
import com.htc.trainingmanagement.exception.CapacityExceededException;
import com.htc.trainingmanagement.exception.EnrollmentException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.EnrollmentMapper;
import com.htc.trainingmanagement.repository.EnrollmentRepository;
import com.htc.trainingmanagement.repository.TraineeRepository;
import com.htc.trainingmanagement.repository.TrainingBatchRepository;
import com.htc.trainingmanagement.service.EnrollmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

        private final EnrollmentRepository enrollmentRepository;
        private final TraineeRepository traineeRepository;
        private final TrainingBatchRepository trainingBatchRepository;
        private final EnrollmentMapper enrollmentMapper;

        // Creates a new enrollment for a trainee in a training batch.
        @Override
        public EnrollmentResponseDto createEnrollment(EnrollmentRequestDto requestDto)
                        throws ResourceNotFoundException, EnrollmentException, CapacityExceededException {

                // Fetches trainee.
                Trainee trainee = getTraineeEntityById(requestDto.getTraineeId());

                validateTraineeIsActive(trainee);

                // Fetches training batch.
                TrainingBatch trainingBatch = getTrainingBatchEntityById(requestDto.getTrainingBatchId());

                // Prevents duplicate enrollment in the same batch.
                if (enrollmentRepository.existsByTraineeAndTrainingBatch(trainee, trainingBatch)) {
                        throw new EnrollmentException(
                                        "Trainee is already enrolled in batch: " + trainingBatch.getBatchName());
                }

                // Prevents trainee from joining overlapping batches.
                validateBatchOverlapForCreate(trainee, trainingBatch);

                validateEnrollmentBeforeBatchStart(trainingBatch);

                // Reduces available seat count when trainee is enrolled.
                reduceAvailableSeats(trainingBatch);

                // Converts trainee and batch into Enrollment entity.
                Enrollment enrollment = enrollmentMapper.toEntity(trainee, trainingBatch);

                // Saves enrollment.
                Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

                // Returns saved enrollment response.
                return enrollmentMapper.toResponseDto(savedEnrollment);
        }

        // Retrieves enrollment by ID.
        @Override
        public EnrollmentResponseDto getEnrollmentById(Long enrollmentId)
                        throws ResourceNotFoundException {

                Enrollment enrollment = getEnrollmentEntityById(enrollmentId);

                return enrollmentMapper.toResponseDto(enrollment);
        }

        // Retrieves all enrollments.
        @Override
        public List<EnrollmentResponseDto> getAllEnrollments() {

                return enrollmentRepository.findAll()
                                .stream()
                                .map(enrollmentMapper::toResponseDto)
                                .toList();
        }

        // Updates an existing enrollment.
        @Override
        public EnrollmentResponseDto updateEnrollment(
                        Long enrollmentId,
                        EnrollmentRequestDto requestDto)
                        throws ResourceNotFoundException, EnrollmentException, CapacityExceededException {

                // Fetches existing enrollment.
                Enrollment enrollment = getEnrollmentEntityById(enrollmentId);

                // Fetches updated trainee.
                Trainee trainee = getTraineeEntityById(requestDto.getTraineeId());

                validateTraineeIsActive(trainee);

                // Fetches updated training batch.
                TrainingBatch newBatch = getTrainingBatchEntityById(requestDto.getTrainingBatchId());

                // Stores old batch before updating.
                TrainingBatch oldBatch = enrollment.getTrainingBatch();

                boolean sameEnrollment = enrollment.getTrainee().getTraineeId()
                                .equals(trainee.getTraineeId())
                                && oldBatch.getTrainingbatchId()
                                                .equals(newBatch.getTrainingbatchId());

                // Prevents duplicate enrollment while allowing same record update.
                if (!sameEnrollment
                                && enrollmentRepository.existsByTraineeAndTrainingBatch(trainee, newBatch)) {
                        throw new EnrollmentException(
                                        "Trainee is already enrolled in batch: " + newBatch.getBatchName());
                }

                // Prevents overlapping batch enrollment while ignoring current enrollment.
                validateBatchOverlapForUpdate(enrollment, trainee, newBatch);

                // If trainee is moved to another batch, update seat counts.
                if (!oldBatch.getTrainingbatchId().equals(newBatch.getTrainingbatchId())) {

                        // Increase seat in old batch.
                        increaseAvailableSeats(oldBatch);

                        // Reduce seat in new batch.
                        reduceAvailableSeats(newBatch);
                }

                // Updates enrollment entity.
                enrollmentMapper.updateEntity(enrollment, trainee, newBatch);

                // Saves updated enrollment.
                Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

                // Returns updated enrollment response.
                return enrollmentMapper.toResponseDto(updatedEnrollment);
        }

        // Deletes enrollment by ID.
        @Override
        public boolean deleteEnrollment(Long enrollmentId)
                        throws ResourceNotFoundException {

                // Fetches enrollment.
                Enrollment enrollment = getEnrollmentEntityById(enrollmentId);

                // Gets batch linked to enrollment.
                TrainingBatch trainingBatch = enrollment.getTrainingBatch();

                // Soft deletes enrollment.
                enrollmentRepository.delete(enrollment);

                // Increases available seat count when trainee leaves.
                increaseAvailableSeats(trainingBatch);

                return true;
        }

        // Retrieves enrollments by trainee ID.
        @Override
        public List<EnrollmentResponseDto> getEnrollmentsByTrainee(Long traineeId)
                        throws ResourceNotFoundException {

                // Validates trainee exists.
                getTraineeEntityById(traineeId);

                return enrollmentRepository.findByTraineeTraineeId(traineeId)
                                .stream()
                                .map(enrollmentMapper::toResponseDto)
                                .toList();
        }

        // Retrieves enrollments by training batch ID.
        @Override
        public List<EnrollmentResponseDto> getEnrollmentsByBatch(Long trainingBatchId)
                        throws ResourceNotFoundException {

                // Validates training batch exists.
                getTrainingBatchEntityById(trainingBatchId);

                return enrollmentRepository.findByTrainingBatchTrainingbatchId(trainingBatchId)
                                .stream()
                                .map(enrollmentMapper::toResponseDto)
                                .toList();
        }

        // Updates only enrollment completion status.
        @Override
        public EnrollmentResponseDto updateCompletionStatus(
                        Long enrollmentId,
                        EnrollmentStatus completionStatus)
                        throws ResourceNotFoundException {

                // Fetches enrollment.
                Enrollment enrollment = getEnrollmentEntityById(enrollmentId);

                // Updates completion status.
                enrollment.setCompletionStatus(completionStatus);

                // Saves updated enrollment.
                Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

                return enrollmentMapper.toResponseDto(updatedEnrollment);
        }

        // Helper method to fetch Enrollment by ID or throw exception if not found.
        private Enrollment getEnrollmentEntityById(Long enrollmentId)
                        throws ResourceNotFoundException {

                return enrollmentRepository.findById(enrollmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Enrollment not found with id: " + enrollmentId));
        }

        // Helper method to fetch Trainee by ID or throw exception if not found.
        private Trainee getTraineeEntityById(Long traineeId)
                        throws ResourceNotFoundException {

                return traineeRepository.findById(traineeId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + traineeId));
        }

        // Helper method to fetch TrainingBatch by ID or throw exception if not found.
        private TrainingBatch getTrainingBatchEntityById(Long trainingBatchId)
                        throws ResourceNotFoundException {

                return trainingBatchRepository.findById(trainingBatchId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: " + trainingBatchId));
        }

        // Helper method to reduce available seats during enrollment.
        private void reduceAvailableSeats(TrainingBatch trainingBatch)
                        throws CapacityExceededException {

                if (trainingBatch.getAvailableSeats() <= 0) {
                        throw new CapacityExceededException("Batch is full. No seats available.");
                }

                trainingBatch.setAvailableSeats(trainingBatch.getAvailableSeats() - 1);
                trainingBatchRepository.save(trainingBatch);
        }

        // Helper method to increase available seats when enrollment is deleted or
        // moved.
        private void increaseAvailableSeats(TrainingBatch trainingBatch) {

                if (trainingBatch.getAvailableSeats() < trainingBatch.getCapacity()) {
                        trainingBatch.setAvailableSeats(trainingBatch.getAvailableSeats() + 1);
                        trainingBatchRepository.save(trainingBatch);
                }
        }

        // Helper method to validate batch overlap during create.
        private void validateBatchOverlapForCreate(
                        Trainee trainee,
                        TrainingBatch newBatch)
                        throws EnrollmentException {

                List<Enrollment> enrollments = enrollmentRepository.findByTrainee(trainee);

                for (Enrollment enrollment : enrollments) {

                        TrainingBatch existingBatch = enrollment.getTrainingBatch();

                        boolean overlaps = !newBatch.getEndDate().isBefore(existingBatch.getStartDate())
                                        && !newBatch.getStartDate().isAfter(existingBatch.getEndDate());

                        if (overlaps) {
                                throw new EnrollmentException(
                                                "Trainee is already enrolled in another batch with overlapping dates.");
                        }
                }
        }

        // Helper method to validate batch overlap during update.
        private void validateBatchOverlapForUpdate(
                        Enrollment currentEnrollment,
                        Trainee trainee,
                        TrainingBatch newBatch)
                        throws EnrollmentException {

                List<Enrollment> enrollments = enrollmentRepository.findByTrainee(trainee);

                for (Enrollment enrollment : enrollments) {

                        // Ignores the current enrollment being updated.
                        if (enrollment.getEnrollmentId().equals(currentEnrollment.getEnrollmentId())) {
                                continue;
                        }

                        TrainingBatch existingBatch = enrollment.getTrainingBatch();

                        boolean overlaps = !newBatch.getEndDate().isBefore(existingBatch.getStartDate())
                                        && !newBatch.getStartDate().isAfter(existingBatch.getEndDate());

                        if (overlaps) {
                                throw new EnrollmentException(
                                                "Trainee is already enrolled in another batch with overlapping dates.");
                        }
                }
        }

        // Helper method to prevent enrollment after batch has started.
        private void validateEnrollmentBeforeBatchStart(TrainingBatch trainingBatch)
                        throws EnrollmentException {

                if (!java.time.LocalDate.now().isBefore(trainingBatch.getStartDate())) {
                        throw new EnrollmentException(
                                        "Enrollment is closed because the batch has already started.");
                }
        }

        // Helper method to validate that inactive trainee cannot enroll.
        private void validateTraineeIsActive(Trainee trainee)
                        throws EnrollmentException {

                if (!trainee.getIsActive()) {
                        throw new EnrollmentException(
                                        "Inactive trainee cannot be enrolled.");
                }
        }
}
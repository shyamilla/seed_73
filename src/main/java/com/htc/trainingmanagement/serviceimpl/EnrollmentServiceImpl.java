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

        @Override
        public EnrollmentResponseDto createEnrollment(EnrollmentRequestDto requestDto)
                        throws ResourceNotFoundException, EnrollmentException, CapacityExceededException {

                Trainee trainee = getTraineeEntityById(requestDto.getTraineeId());

                TrainingBatch trainingBatch = getTrainingBatchEntityById(
                                requestDto.getTrainingBatchId());

                // Checks whether the trainee is already enrolled in the same batch.
                if (enrollmentRepository.existsByTraineeAndTrainingBatch(trainee, trainingBatch)) {
                        throw new EnrollmentException(
                                        "Trainee is already enrolled in batch: " + trainingBatch.getBatchName());
                }

                validateBatchCapacity(trainingBatch);

                Enrollment enrollment = enrollmentMapper.toEntity(
                                trainee,
                                trainingBatch);

                Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

                return enrollmentMapper.toResponseDto(savedEnrollment);
        }

        @Override
        public EnrollmentResponseDto getEnrollmentById(Long enrollmentId)
                        throws ResourceNotFoundException {

                Enrollment enrollment = getEnrollmentEntityById(enrollmentId);

                return enrollmentMapper.toResponseDto(enrollment);
        }

        @Override
        public List<EnrollmentResponseDto> getAllEnrollments() {

                return enrollmentRepository.findAll()
                                .stream()
                                .map(enrollmentMapper::toResponseDto)
                                .toList();
        }

        @Override
        public EnrollmentResponseDto updateEnrollment(
                        Long enrollmentId,
                        EnrollmentRequestDto requestDto)
                        throws ResourceNotFoundException, EnrollmentException, CapacityExceededException {

                Enrollment enrollment = getEnrollmentEntityById(enrollmentId);

                Trainee trainee = getTraineeEntityById(requestDto.getTraineeId());

                TrainingBatch trainingBatch = getTrainingBatchEntityById(
                                requestDto.getTrainingBatchId());

                boolean sameEnrollment = enrollment.getTrainee().getTraineeId()
                                .equals(trainee.getTraineeId())
                                && enrollment.getTrainingBatch().getTrainingbatchId()
                                                .equals(trainingBatch.getTrainingbatchId());

                // Prevents duplicate enrollment while allowing the same record update.
                if (!sameEnrollment
                                && enrollmentRepository.existsByTraineeAndTrainingBatch(trainee, trainingBatch)) {
                        throw new EnrollmentException(
                                        "Trainee is already enrolled in batch: " + trainingBatch.getBatchName());
                }

                // Checks capacity only when moving enrollment to another batch.
                if (!sameEnrollment) {
                        validateBatchCapacity(trainingBatch);
                }

                enrollmentMapper.updateEntity(enrollment, trainee, trainingBatch);

                Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

                return enrollmentMapper.toResponseDto(updatedEnrollment);
        }

        @Override
        public boolean deleteEnrollment(Long enrollmentId)
                        throws ResourceNotFoundException {

                Enrollment enrollment = getEnrollmentEntityById(enrollmentId);

                enrollmentRepository.delete(enrollment);

                return true;
        }

        @Override
        public List<EnrollmentResponseDto> getEnrollmentsByTrainee(Long traineeId)
                        throws ResourceNotFoundException {

                getTraineeEntityById(traineeId);

                return enrollmentRepository.findByTraineeTraineeId(traineeId)
                                .stream()
                                .map(enrollmentMapper::toResponseDto)
                                .toList();
        }

        @Override
        public List<EnrollmentResponseDto> getEnrollmentsByBatch(Long trainingBatchId)
                        throws ResourceNotFoundException {

                getTrainingBatchEntityById(trainingBatchId);

                return enrollmentRepository.findByTrainingBatchTrainingbatchId(trainingBatchId)
                                .stream()
                                .map(enrollmentMapper::toResponseDto)
                                .toList();
        }

        @Override
        public EnrollmentResponseDto updateCompletionStatus(
                        Long enrollmentId,
                        EnrollmentStatus completionStatus)
                        throws ResourceNotFoundException {

                Enrollment enrollment = getEnrollmentEntityById(enrollmentId);

                // Updates only the enrollment completion status.
                enrollment.setCompletionStatus(completionStatus);

                Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

                return enrollmentMapper.toResponseDto(updatedEnrollment);
        }

        private Enrollment getEnrollmentEntityById(Long enrollmentId)
                        throws ResourceNotFoundException {

                return enrollmentRepository.findById(enrollmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Enrollment not found with id: " + enrollmentId));
        }

        private Trainee getTraineeEntityById(Long traineeId)
                        throws ResourceNotFoundException {

                return traineeRepository.findById(traineeId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + traineeId));
        }

        private TrainingBatch getTrainingBatchEntityById(Long trainingBatchId)
                        throws ResourceNotFoundException {

                return trainingBatchRepository.findById(trainingBatchId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: " + trainingBatchId));
        }

        private void validateBatchCapacity(TrainingBatch trainingBatch)
                        throws CapacityExceededException {

                long enrolledCount = enrollmentRepository.countByTrainingBatch(trainingBatch);

                if (enrolledCount >= trainingBatch.getCapacity()) {
                        throw new CapacityExceededException(
                                        "Batch capacity exceeded. Maximum capacity is "
                                                        + trainingBatch.getCapacity());
                }
        }
}
package com.htc.trainingmanagement.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
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

        @Override
        public EnrollmentResponseDto createEnrollment(
                        EnrollmentRequestDto requestDto)
                        throws ResourceNotFoundException,
                        EnrollmentException,
                        CapacityExceededException {

                Trainee trainee = traineeRepository.findById(
                                requestDto.getTraineeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: "
                                                                + requestDto.getTraineeId()));

                TrainingBatch trainingBatch = trainingBatchRepository.findById(
                                requestDto.getTrainingBatchId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: "
                                                                + requestDto.getTrainingBatchId()));

                // Check if trainee is already enrolled in the batch
                if (enrollmentRepository.existsByTraineeAndTrainingBatch(
                                trainee, trainingBatch)) {

                        throw new EnrollmentException(
                                        "Trainee is already enrolled in batch: "
                                                        + trainingBatch.getBatchName());
                }

                // Check batch capacity
                long enrolledCount = enrollmentRepository.countByTrainingBatch(trainingBatch);

                if (enrolledCount >= trainingBatch.getCapacity()) {

                        throw new CapacityExceededException(
                                        "Batch capacity exceeded. Maximum capacity is "
                                                        + trainingBatch.getCapacity());
                }

                Enrollment enrollment = new Enrollment();

                enrollment.setTrainee(trainee);
                enrollment.setTrainingBatch(trainingBatch);
                enrollment.setEnrollmentDate(LocalDate.now());
                enrollment.setCompletionStatus(EnrollmentStatus.ENROLLED);
                enrollment.setScore(0.0);

                Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

                return convertToResponseDto(savedEnrollment);
        }

        @Override
        public EnrollmentResponseDto getEnrollmentById(
                        Long enrollmentId)
                        throws ResourceNotFoundException {

                Enrollment enrollment = enrollmentRepository.findById(
                                enrollmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Enrollment not found with id: "
                                                                + enrollmentId));

                return convertToResponseDto(enrollment);
        }

        @Override
        public List<EnrollmentResponseDto> getAllEnrollments() {

                List<Enrollment> enrollments = enrollmentRepository.findAll();

                List<EnrollmentResponseDto> responseDtos = new ArrayList<>();

                for (Enrollment enrollment : enrollments) {
                        responseDtos.add(
                                        convertToResponseDto(enrollment));
                }

                return responseDtos;
        }

        @Override
        public EnrollmentResponseDto updateEnrollment(
                        Long enrollmentId,
                        EnrollmentRequestDto requestDto)
                        throws ResourceNotFoundException,
                        EnrollmentException {

                Enrollment enrollment = enrollmentRepository.findById(
                                enrollmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Enrollment not found with id: "
                                                                + enrollmentId));

                Trainee trainee = traineeRepository.findById(
                                requestDto.getTraineeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: "
                                                                + requestDto.getTraineeId()));

                TrainingBatch trainingBatch = trainingBatchRepository.findById(
                                requestDto.getTrainingBatchId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: "
                                                                + requestDto.getTrainingBatchId()));

                boolean alreadyExists = enrollmentRepository.existsByTraineeAndTrainingBatch(
                                trainee,
                                trainingBatch);

                if (alreadyExists
                                && !enrollment.getTrainee().getTraineeId().equals(
                                                trainee.getTraineeId())) {

                        throw new EnrollmentException(
                                        "Trainee is already enrolled in batch: "
                                                        + trainingBatch.getBatchName());
                }

                enrollment.setTrainee(trainee);
                enrollment.setTrainingBatch(trainingBatch);

                Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

                return convertToResponseDto(updatedEnrollment);
        }

        @Override
        public boolean deleteEnrollment(
                        Long enrollmentId)
                        throws ResourceNotFoundException {

                Enrollment enrollment = enrollmentRepository.findById(
                                enrollmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Enrollment not found with id: "
                                                                + enrollmentId));

                enrollmentRepository.delete(enrollment);

                return true;
        }

        private EnrollmentResponseDto convertToResponseDto(
                        Enrollment enrollment) {

                return new EnrollmentResponseDto(
                                enrollment.getEnrollmentId(),
                                enrollment.getEnrollmentDate(),
                                enrollment.getCompletionStatus().name(),
                                enrollment.getScore(),
                                enrollment.getFeedback(),
                                enrollment.getTrainee().getTraineeId(),
                                enrollment.getTrainee().getFirstName()
                                                + " "
                                                + enrollment.getTrainee().getLastName(),
                                enrollment.getTrainingBatch().getTrainingbatchId(),
                                enrollment.getTrainingBatch().getBatchName(),
                                enrollment.getCreatedAt(),
                                enrollment.getUpdatedAt());
        }
}
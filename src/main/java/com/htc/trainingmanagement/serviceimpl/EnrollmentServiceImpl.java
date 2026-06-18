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
        public EnrollmentResponseDto createEnrollment(EnrollmentRequestDto requestDto) {

                Trainee trainee = traineeRepository.findById(requestDto.getTraineeId()).orElseThrow();

                TrainingBatch trainingBatch = trainingBatchRepository.findById(requestDto.getTrainingBatchId())
                                .orElseThrow();

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
        public EnrollmentResponseDto getEnrollmentById(Long enrollmentId) {

                Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();

                return convertToResponseDto(enrollment);
        }

        @Override
        public List<EnrollmentResponseDto> getAllEnrollments() {

                List<Enrollment> enrollments = enrollmentRepository.findAll();

                List<EnrollmentResponseDto> responseDtos = new ArrayList<>();

                for (Enrollment enrollment : enrollments) {
                        responseDtos.add(convertToResponseDto(enrollment));
                }

                return responseDtos;
        }

        @Override
        public EnrollmentResponseDto updateEnrollment(Long enrollmentId,EnrollmentRequestDto requestDto) {

                Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();

                Trainee trainee = traineeRepository.findById(requestDto.getTraineeId()).orElseThrow();

                TrainingBatch trainingBatch = trainingBatchRepository.findById(requestDto.getTrainingBatchId()).orElseThrow();

                enrollment.setTrainee(trainee);
                enrollment.setTrainingBatch(trainingBatch);

                Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

                return convertToResponseDto(updatedEnrollment);
        }

        @Override
        public boolean deleteEnrollment(Long enrollmentId) {

                Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();

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
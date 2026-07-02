package com.htc.trainingmanagement.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.entity.Attendance;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.enums.AttendanceStatus;
import com.htc.trainingmanagement.enums.SessionStatus;
import com.htc.trainingmanagement.exception.AttendanceException;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.AttendanceMapper;
import com.htc.trainingmanagement.repository.AttendanceRepository;
import com.htc.trainingmanagement.repository.EnrollmentRepository;
import com.htc.trainingmanagement.repository.SessionRepository;
import com.htc.trainingmanagement.repository.TraineeRepository;
import com.htc.trainingmanagement.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

        private final AttendanceRepository attendanceRepository;
        private final AttendanceMapper attendanceMapper;
        private final TraineeRepository traineeRepository;
        private final SessionRepository sessionRepository;
        private final EnrollmentRepository enrollmentRepository;

        // Creates attendance for a trainee in a session.
        @Override
        public AttendanceResponseDto createAttendance(AttendanceRequestDto requestDto)
                        throws ResourceNotFoundException, DuplicateResourceException, AttendanceException {

                // Fetches the trainee.
                Trainee trainee = getTraineeEntityById(requestDto.getTraineeId());

                // Fetches the session.
                Session session = getSessionEntityById(requestDto.getSessionId());

                // Validates that the trainee is enrolled in the session's training batch.
                validateEnrollment(trainee, session);

                validateDuplicateAttendance(trainee, session);

                validateAttendanceDate(requestDto, session);

                validateAttendanceDateIsNotFuture(requestDto);

                // Converts request DTO to Attendance entity.
                Attendance attendance = attendanceMapper.toEntity(requestDto);

                // Sets trainee and session relationships.
                attendance.setTrainee(trainee);
                attendance.setSession(session);

                // Saves attendance.
                Attendance savedAttendance = attendanceRepository.save(attendance);

                // Returns saved attendance response.
                return attendanceMapper.toResponseDto(savedAttendance);
        }

        // Retrieves attendance by ID.
        @Override
        public AttendanceResponseDto getAttendanceById(Long attendanceId)
                        throws ResourceNotFoundException {

                // Fetches attendance by ID.
                Attendance attendance = getAttendanceEntityById(attendanceId);

                // Returns attendance response.
                return attendanceMapper.toResponseDto(attendance);
        }

        // Retrieves all attendance records.
        @Override
        public List<AttendanceResponseDto> getAllAttendances() {

                return attendanceRepository.findAll()
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        // Updates an existing attendance record.
        @Override
        public AttendanceResponseDto updateAttendance(
                        Long attendanceId,
                        AttendanceRequestDto requestDto)
                        throws DuplicateResourceException, ResourceNotFoundException, AttendanceException {

                // Fetches the existing attendance.
                Attendance attendance = getAttendanceEntityById(attendanceId);

                // Fetches the trainee.
                Trainee trainee = getTraineeEntityById(requestDto.getTraineeId());

                // Fetches the session.
                Session session = getSessionEntityById(requestDto.getSessionId());

                validateSessionNotCompleted(session);

                // Validates that the trainee is enrolled in the session's training batch.
                validateEnrollment(trainee, session);

                validateDuplicateAttendanceForUpdate(attendance, trainee, session);

                validateAttendanceDate(requestDto, session);

                validateAttendanceDateIsNotFuture(requestDto);

                // Updates attendance fields from request DTO.
                attendanceMapper.updateEntity(attendance, requestDto);

                // Updates trainee and session relationships.
                attendance.setTrainee(trainee);
                attendance.setSession(session);

                // Saves updated attendance.
                Attendance updatedAttendance = attendanceRepository.save(attendance);

                // Returns updated attendance response.
                return attendanceMapper.toResponseDto(updatedAttendance);
        }

        // Deletes attendance by ID.
        @Override
        public boolean deleteAttendance(Long attendanceId)
                        throws ResourceNotFoundException {

                // Fetches attendance to ensure it exists.
                Attendance attendance = getAttendanceEntityById(attendanceId);

                // Soft deletes attendance.
                attendanceRepository.delete(attendance);

                return true;
        }

        // Retrieves attendance records for a specific trainee.
        @Override
        public List<AttendanceResponseDto> getAttendanceByTrainee(Long traineeId)
                        throws ResourceNotFoundException {

                // Validates that the trainee exists.
                getTraineeEntityById(traineeId);

                return attendanceRepository.findByTraineeTraineeId(traineeId)
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        // Retrieves attendance records for a specific session.
        @Override
        public List<AttendanceResponseDto> getAttendanceBySession(Long sessionId)
                        throws ResourceNotFoundException {

                // Validates that the session exists.
                getSessionEntityById(sessionId);

                return attendanceRepository.findBySessionSessionId(sessionId)
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        // Retrieves attendance records by date.
        @Override
        public List<AttendanceResponseDto> getAttendanceByDate(LocalDate attendanceDate) {

                return attendanceRepository.findByAttendanceDate(attendanceDate)
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        // Updates only the attendance status.
        @Override
        public AttendanceResponseDto updateAttendanceStatus(
                        Long attendanceId,
                        AttendanceStatus attendanceStatus)
                        throws ResourceNotFoundException {

                // Fetches attendance by ID.
                Attendance attendance = getAttendanceEntityById(attendanceId);

                // Updates only the attendance status.
                attendance.setAttendanceStatus(attendanceStatus);

                // Saves updated attendance.
                Attendance updatedAttendance = attendanceRepository.save(attendance);

                // Returns updated attendance response.
                return attendanceMapper.toResponseDto(updatedAttendance);
        }

        // Helper method to fetch Attendance by ID or throw exception if not found.
        private Attendance getAttendanceEntityById(Long attendanceId)
                        throws ResourceNotFoundException {

                return attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));
        }

        // Helper method to fetch Trainee by ID or throw exception if not found.
        private Trainee getTraineeEntityById(Long traineeId)
                        throws ResourceNotFoundException {

                return traineeRepository.findById(traineeId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + traineeId));
        }

        // Helper method to fetch Session by ID or throw exception if not found.
        private Session getSessionEntityById(Long sessionId)
                        throws ResourceNotFoundException {

                return sessionRepository.findById(sessionId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Session not found with id: " + sessionId));
        }

        // Helper method to validate that the trainee is enrolled in the session's
        // training batch.
        private void validateEnrollment(Trainee trainee, Session session)
                        throws AttendanceException {

                boolean enrolled = enrollmentRepository.existsByTraineeAndTrainingBatch(
                                trainee,
                                session.getTrainingBatch());

                if (!enrolled) {
                        throw new AttendanceException(
                                        "Trainee is not enrolled in the training batch for this session.");
                }
        }

        // Helper method to prevent duplicate attendance for same trainee and session.
        private void validateDuplicateAttendance(Trainee trainee, Session session)
                        throws DuplicateResourceException {

                if (attendanceRepository.existsByTraineeAndSession(trainee, session)) {
                        throw new DuplicateResourceException(
                                        "Attendance already marked for this trainee in this session.");
                }
        }

        // Helper method to prevent duplicate attendance while updating.
        private void validateDuplicateAttendanceForUpdate(
                        Attendance attendance,
                        Trainee trainee,
                        Session session)
                        throws DuplicateResourceException {

                boolean sameAttendance = attendance.getTrainee().getTraineeId().equals(trainee.getTraineeId())
                                && attendance.getSession().getSessionId().equals(session.getSessionId());

                if (!sameAttendance
                                && attendanceRepository.existsByTraineeAndSession(trainee, session)) {
                        throw new DuplicateResourceException(
                                        "Attendance already marked for this trainee in this session.");
                }
        }

        // Helper method to validate that attendance date matches the session date.
        private void validateAttendanceDate(
                        AttendanceRequestDto requestDto,
                        Session session)
                        throws AttendanceException {

                if (!requestDto.getAttendanceDate().equals(session.getSessionDate())) {
                        throw new AttendanceException(
                                        "Attendance date must match the session date.");
                }
        }

        // Helper method to validate that attendance cannot be marked for a future date.
        private void validateAttendanceDateIsNotFuture(
                        AttendanceRequestDto requestDto)
                        throws AttendanceException {

                if (requestDto.getAttendanceDate().isAfter(LocalDate.now())) {
                        throw new AttendanceException(
                                        "Attendance cannot be marked for a future date.");
                }
        }

        // Helper method to validate that attendance cannot be modified after session
        // completion.
        private void validateSessionNotCompleted(Session session)
                        throws AttendanceException {

                if (session.getSessionStatus() == SessionStatus.COMPLETED) {
                        throw new AttendanceException(
                                        "Attendance cannot be modified after the session is completed.");
                }
        }
}
package com.htc.trainingmanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.entity.Attendance;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.repository.AttendanceRepository;
import com.htc.trainingmanagement.repository.SessionRepository;
import com.htc.trainingmanagement.repository.TraineeRepository;
import com.htc.trainingmanagement.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

        // Constructor injection with private final fields is better because it makes
        // dependencies immutable,  testable, and not need of  field injection (@Autowired).
        private final AttendanceRepository attendanceRepository;
        private final TraineeRepository traineeRepository;
        private final SessionRepository sessionRepository;

        @Override
        public AttendanceResponseDto createAttendance(AttendanceRequestDto requestDto)
                        throws ResourceNotFoundException {

                Trainee trainee = traineeRepository.findById(requestDto.getTraineeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + requestDto.getTraineeId()));

                Session session = sessionRepository.findById(requestDto.getSessionId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Session not found with id: " + requestDto.getSessionId()));

                Attendance attendance = new Attendance();

                attendance.setAttendanceDate(requestDto.getAttendanceDate());
                attendance.setAttendanceStatus(requestDto.getAttendanceStatus());
                attendance.setRemarks(requestDto.getRemarks());
                attendance.setTrainee(trainee);
                attendance.setSession(session);

                Attendance savedAttendance = attendanceRepository.save(attendance);

                return convertToResponseDto(savedAttendance);
        }

        @Override
        public AttendanceResponseDto getAttendanceById(Long attendanceId) throws ResourceNotFoundException {

                Attendance attendance = attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));

                return convertToResponseDto(attendance);
        }

        @Override
        public List<AttendanceResponseDto> getAllAttendances() {

                List<Attendance> attendances = attendanceRepository.findAll();

                List<AttendanceResponseDto> responseDtos = new ArrayList<>();

                for (Attendance attendance : attendances) {
                        responseDtos.add(convertToResponseDto(attendance));
                }

                return responseDtos;
        }

        @Override
        public AttendanceResponseDto updateAttendance(
                        Long attendanceId,
                        AttendanceRequestDto requestDto) throws ResourceNotFoundException {

                Attendance attendance = attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));

                Trainee trainee = traineeRepository.findById(requestDto.getTraineeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + requestDto.getTraineeId()));

                Session session = sessionRepository.findById(requestDto.getSessionId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Session not found with id: " + requestDto.getSessionId()));

                attendance.setAttendanceDate(requestDto.getAttendanceDate());
                attendance.setAttendanceStatus(requestDto.getAttendanceStatus());
                attendance.setRemarks(requestDto.getRemarks());
                attendance.setTrainee(trainee);
                attendance.setSession(session);

                Attendance updatedAttendance = attendanceRepository.save(attendance);

                return convertToResponseDto(updatedAttendance);
        }

        @Override
        public boolean deleteAttendance(Long attendanceId) throws ResourceNotFoundException {

                Attendance attendance = attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));

                attendanceRepository.delete(attendance);

                return true;
        }

        private AttendanceResponseDto convertToResponseDto(Attendance attendance) {

                return new AttendanceResponseDto(
                                attendance.getAttendanceId(),
                                attendance.getAttendanceDate(),
                                attendance.getAttendanceStatus(),
                                attendance.getRemarks(),
                                attendance.getTrainee().getTraineeId(),
                                attendance.getSession().getSessionId(),
                                attendance.getCreatedAt(),
                                attendance.getUpdatedAt());
        }
}
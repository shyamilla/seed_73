package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.entity.Attendance;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.enums.AttendanceStatus;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.AttendanceMapper;
import com.htc.trainingmanagement.repository.AttendanceRepository;
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

        @Override
        public AttendanceResponseDto createAttendance(AttendanceRequestDto requestDto)
                        throws ResourceNotFoundException {

                Trainee trainee = traineeRepository.findById(requestDto.getTraineeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + requestDto.getTraineeId()));

                Session session = sessionRepository.findById(requestDto.getSessionId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Session not found with id: " + requestDto.getSessionId()));

                Attendance attendance = attendanceMapper.toEntity(requestDto);

                attendance.setTrainee(trainee);
                attendance.setSession(session);

                Attendance savedAttendance = attendanceRepository.save(attendance);

                return attendanceMapper.toResponseDto(savedAttendance);
        }

        @Override
        public AttendanceResponseDto getAttendanceById(Long attendanceId)
                        throws ResourceNotFoundException {

                Attendance attendance = attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));

                return attendanceMapper.toResponseDto(attendance);
        }

        @Override
        public List<AttendanceResponseDto> getAllAttendances() {

                return attendanceRepository.findAll()
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        @Override
        public AttendanceResponseDto updateAttendance(Long attendanceId, AttendanceRequestDto requestDto)
                        throws ResourceNotFoundException {

                Attendance attendance = attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));

                Trainee trainee = traineeRepository.findById(requestDto.getTraineeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + requestDto.getTraineeId()));

                Session session = sessionRepository.findById(requestDto.getSessionId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Session not found with id: " + requestDto.getSessionId()));

                attendanceMapper.updateEntity(attendance, requestDto);

                attendance.setTrainee(trainee);
                attendance.setSession(session);

                Attendance updatedAttendance = attendanceRepository.save(attendance);

                return attendanceMapper.toResponseDto(updatedAttendance);
        }

        @Override
        public boolean deleteAttendance(Long attendanceId)
                        throws ResourceNotFoundException {

                Attendance attendance = attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));

                attendanceRepository.delete(attendance);

                return true;
        }

        // extra methods apart from crud

        @Override
        public List<AttendanceResponseDto> getAttendanceByTrainee(Long traineeId)
                        throws ResourceNotFoundException {

                traineeRepository.findById(traineeId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + traineeId));

                return attendanceRepository.findByTraineeTraineeId(traineeId)
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        @Override
        public AttendanceResponseDto updateAttendanceStatus(
                        Long attendanceId,
                        AttendanceStatus status)
                        throws ResourceNotFoundException {

                Attendance attendance = attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));

                attendance.setAttendanceStatus(status);

                Attendance updatedAttendance = attendanceRepository.save(attendance);

                return attendanceMapper.toResponseDto(updatedAttendance);
        }
}
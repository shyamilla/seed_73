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
import com.htc.trainingmanagement.exception.DuplicateResourceException;
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
                        throws ResourceNotFoundException, DuplicateResourceException {

                Trainee trainee = getTraineeEntityById(requestDto.getTraineeId());

                Session session = getSessionEntityById(requestDto.getSessionId());

                Attendance attendance = attendanceMapper.toEntity(requestDto);

                attendance.setTrainee(trainee);
                attendance.setSession(session);

                Attendance savedAttendance = attendanceRepository.save(attendance);

                return attendanceMapper.toResponseDto(savedAttendance);
        }

        @Override
        public AttendanceResponseDto getAttendanceById(Long attendanceId)
                        throws ResourceNotFoundException {

                Attendance attendance = getAttendanceEntityById(attendanceId);

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
        public AttendanceResponseDto updateAttendance(
                        Long attendanceId,
                        AttendanceRequestDto requestDto)
                        throws DuplicateResourceException, ResourceNotFoundException {

                Attendance attendance = getAttendanceEntityById(attendanceId);

                Trainee trainee = getTraineeEntityById(requestDto.getTraineeId());

                Session session = getSessionEntityById(requestDto.getSessionId());

                attendanceMapper.updateEntity(attendance, requestDto);

                attendance.setTrainee(trainee);
                attendance.setSession(session);

                Attendance updatedAttendance = attendanceRepository.save(attendance);

                return attendanceMapper.toResponseDto(updatedAttendance);
        }

        @Override
        public boolean deleteAttendance(Long attendanceId)
                        throws ResourceNotFoundException {

                Attendance attendance = getAttendanceEntityById(attendanceId);

                attendanceRepository.delete(attendance);

                return true;
        }

        @Override
        public List<AttendanceResponseDto> getAttendanceByTrainee(Long traineeId)
                        throws ResourceNotFoundException {

                getTraineeEntityById(traineeId);

                return attendanceRepository.findByTraineeTraineeId(traineeId)
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        @Override
        public List<AttendanceResponseDto> getAttendanceBySession(Long sessionId)
                        throws ResourceNotFoundException {

                getSessionEntityById(sessionId);

                return attendanceRepository.findBySessionSessionId(sessionId)
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        @Override
        public List<AttendanceResponseDto> getAttendanceByDate(LocalDate attendanceDate) {

                return attendanceRepository.findByAttendanceDate(attendanceDate)
                                .stream()
                                .map(attendanceMapper::toResponseDto)
                                .toList();
        }

        @Override
        public AttendanceResponseDto updateAttendanceStatus(
                        Long attendanceId,
                        AttendanceStatus attendanceStatus)
                        throws ResourceNotFoundException {

                Attendance attendance = getAttendanceEntityById(attendanceId);

                // Updates only the attendance status.
                attendance.setAttendanceStatus(attendanceStatus);

                Attendance updatedAttendance = attendanceRepository.save(attendance);

                return attendanceMapper.toResponseDto(updatedAttendance);
        }

        private Attendance getAttendanceEntityById(Long attendanceId)
                        throws ResourceNotFoundException {

                return attendanceRepository.findById(attendanceId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Attendance not found with id: " + attendanceId));
        }

        private Trainee getTraineeEntityById(Long traineeId)
                        throws ResourceNotFoundException {

                return traineeRepository.findById(traineeId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Trainee not found with id: " + traineeId));
        }

        private Session getSessionEntityById(Long sessionId)
                        throws ResourceNotFoundException {

                return sessionRepository.findById(sessionId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Session not found with id: " + sessionId));
        }
}
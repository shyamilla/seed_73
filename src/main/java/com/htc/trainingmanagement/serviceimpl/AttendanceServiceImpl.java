package com.htc.trainingmanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.entity.Attendance;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.repository.AttendanceRepository;
import com.htc.trainingmanagement.repository.SessionRepository;
import com.htc.trainingmanagement.repository.TraineeRepository;
import com.htc.trainingmanagement.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final TraineeRepository traineeRepository;
    private final SessionRepository sessionRepository;

    @Override
    public AttendanceResponseDto createAttendance(AttendanceRequestDto requestDto) {

        Trainee trainee = traineeRepository.findById(requestDto.getTraineeId()).orElseThrow();

        Session session = sessionRepository.findById(requestDto.getSessionId()).orElseThrow();

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
    public AttendanceResponseDto getAttendanceById(Long attendanceId) {

        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow();

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
    public AttendanceResponseDto updateAttendance(Long attendanceId, AttendanceRequestDto requestDto) {

        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow();

        Trainee trainee = traineeRepository.findById(requestDto.getTraineeId()).orElseThrow();

        Session session = sessionRepository.findById(requestDto.getSessionId()).orElseThrow();

        attendance.setAttendanceDate(requestDto.getAttendanceDate());

        attendance.setAttendanceStatus(requestDto.getAttendanceStatus());

        attendance.setRemarks(requestDto.getRemarks());

        attendance.setTrainee(trainee);
        attendance.setSession(session);

        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return convertToResponseDto(updatedAttendance);
    }

    @Override
    public boolean deleteAttendance(Long attendanceId) {

        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow();

        attendanceRepository.delete(attendance);

        return true;
    }

    private AttendanceResponseDto convertToResponseDto(
            Attendance attendance) {

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
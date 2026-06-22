package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.entity.Attendance;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.AttendanceMapper;
import com.htc.trainingmanagement.repository.AttendanceRepository;
import com.htc.trainingmanagement.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public AttendanceResponseDto createAttendance(
            AttendanceRequestDto requestDto)
            throws ResourceNotFoundException {

        Attendance attendance = attendanceMapper.toEntity(requestDto);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return attendanceMapper.toResponseDto(savedAttendance);
    }

    @Override
    public AttendanceResponseDto getAttendanceById(
            Long attendanceId)
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
    public AttendanceResponseDto updateAttendance(
            Long attendanceId,
            AttendanceRequestDto requestDto)
            throws ResourceNotFoundException {

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attendance not found with id: " + attendanceId));

        attendanceMapper.updateEntity(attendance, requestDto);

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
}
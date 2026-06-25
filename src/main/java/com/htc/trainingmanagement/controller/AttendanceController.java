package com.htc.trainingmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.enums.AttendanceStatus;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.serviceimpl.AttendanceServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceServiceImpl attendanceServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<AttendanceResponseDto> addAttendance(@Valid @RequestBody AttendanceRequestDto requestDto)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceServiceImpl.createAttendance(requestDto));
    }

    @PutMapping("/update/{attendanceId}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(@PathVariable Long attendanceId,
            @Valid @RequestBody AttendanceRequestDto requestDto) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(attendanceServiceImpl.updateAttendance(attendanceId, requestDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceServiceImpl.getAllAttendances());
    }

    @GetMapping("/find/{attendanceId}")
    public ResponseEntity<AttendanceResponseDto> findAttendanceById(@PathVariable Long attendanceId)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceServiceImpl.getAttendanceById(attendanceId));

    }

    @DeleteMapping("/delete/{attendanceId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long attendanceId) throws ResourceNotFoundException {
        return ResponseEntity.ok(attendanceServiceImpl.deleteAttendance(attendanceId));
    }

    // other methods

    @GetMapping("/trainee/{traineeId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByTrainee(@PathVariable Long traineeId)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceServiceImpl.getAttendanceByTrainee(traineeId));
    }

    @PatchMapping("/{attendanceId}/status")
    public ResponseEntity<AttendanceResponseDto> updateAttendanceStatus(@PathVariable Long attendanceId,
            @RequestParam AttendanceStatus attendanceStatus) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceServiceImpl.updateAttendanceStatus(attendanceId, attendanceStatus));
    }
}

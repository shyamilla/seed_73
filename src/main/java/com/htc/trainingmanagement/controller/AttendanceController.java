package com.htc.trainingmanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.htc.trainingmanagement.exception.AttendanceException;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.service.AttendanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/add")
    public ResponseEntity<AttendanceResponseDto> addAttendance(
            @Valid @RequestBody AttendanceRequestDto requestDto)
            throws ResourceNotFoundException, DuplicateResourceException, AttendanceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(attendanceService.createAttendance(requestDto));
    }

    @GetMapping("/find/{attendanceId}")
    public ResponseEntity<AttendanceResponseDto> getAttendanceById(
            @PathVariable Long attendanceId)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.getAttendanceById(attendanceId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceResponseDto>> getAllAttendances() {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.getAllAttendances());
    }

    @PutMapping("/update/{attendanceId}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(
            @PathVariable Long attendanceId,
            @Valid @RequestBody AttendanceRequestDto requestDto)
            throws ResourceNotFoundException, DuplicateResourceException, AttendanceException {

        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.updateAttendance(attendanceId, requestDto));
    }

    @DeleteMapping("/delete/{attendanceId}")
    public ResponseEntity<Boolean> deleteAttendance(
            @PathVariable Long attendanceId)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.deleteAttendance(attendanceId));
    }

    @GetMapping("/trainee/{traineeId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByTrainee(
            @PathVariable Long traineeId)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.getAttendanceByTrainee(traineeId));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceBySession(
            @PathVariable Long sessionId)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.getAttendanceBySession(sessionId));
    }

    @GetMapping("/date")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate attendanceDate) {
        return ResponseEntity.status(HttpStatus.OK).body(attendanceService.getAttendanceByDate(attendanceDate));
    }

    @PatchMapping("/{attendanceId}/status")
    public ResponseEntity<AttendanceResponseDto> updateAttendanceStatus(
            @PathVariable Long attendanceId,
            @RequestParam AttendanceStatus attendanceStatus)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(attendanceService.updateAttendanceStatus(attendanceId, attendanceStatus));
    }
}
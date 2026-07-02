package com.htc.trainingmanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import com.htc.trainingmanagement.dto.request.SessionRequestDto;
import com.htc.trainingmanagement.dto.response.SessionResponseDto;
import com.htc.trainingmanagement.enums.SessionStatus;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.service.SessionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {

        private final SessionService sessionService;

        @PostMapping("/add")
        public ResponseEntity<SessionResponseDto> addSession(
                        @Valid @RequestBody SessionRequestDto requestDto)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.createSession(requestDto));
        }

        @PutMapping("/update/{sessionId}")
        public ResponseEntity<SessionResponseDto> updateSession(
                        @PathVariable Long sessionId,
                        @Valid @RequestBody SessionRequestDto requestDto)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(sessionService.updateSession(sessionId, requestDto));
                                
        }

        @GetMapping("/find/{sessionId}")
        public ResponseEntity<SessionResponseDto> getSessionById(
                        @PathVariable Long sessionId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(sessionService.getSessionById(sessionId));
        }

        @GetMapping("/all")
        public ResponseEntity<List<SessionResponseDto>> getAllSessions() {
                return ResponseEntity.status(HttpStatus.OK).body(sessionService.getAllSessions());
        }

        @DeleteMapping("/delete/{sessionId}")
        public ResponseEntity<Boolean> deleteSession(
                        @PathVariable Long sessionId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(sessionService.deleteSession(sessionId));
        }

        @GetMapping("/batch/{trainingBatchId}")
        public ResponseEntity<List<SessionResponseDto>> getSessionsByBatch(
                        @PathVariable Long trainingBatchId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(sessionService.getSessionsByBatch(trainingBatchId));
        }

        @GetMapping("/date")
        public ResponseEntity<List<SessionResponseDto>> getSessionsByDate(
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sessionDate) {
                return ResponseEntity.status(HttpStatus.OK).body(sessionService.getSessionsByDate(sessionDate));
        }

        @PatchMapping("/{sessionId}/status")
        public ResponseEntity<SessionResponseDto> updateSessionStatus(
                        @PathVariable Long sessionId,
                        @RequestParam SessionStatus status)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(sessionService.updateSessionStatus(sessionId, status));
        }

}
package com.htc.trainingmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htc.trainingmanagement.dto.request.SessionRequestDto;
import com.htc.trainingmanagement.dto.response.SessionResponseDto;
import com.htc.trainingmanagement.serviceimpl.SessionServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionServiceImpl sessionServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<SessionResponseDto> addSession(@Valid @RequestBody SessionRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionServiceImpl.createSession(requestDto));
    }

    @PutMapping("/update/{sessionId}")
    public ResponseEntity<SessionResponseDto> updateSession(@PathVariable Long sessionId,
            @Valid @RequestBody SessionRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(sessionServiceImpl.updateSession(sessionId, requestDto));
    }

    @GetMapping("/find/{sessionId}")
    public ResponseEntity<SessionResponseDto> findBySessionId(@PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(sessionServiceImpl.getSessionById(sessionId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(sessionServiceImpl.getAllSessions());
    }

    @DeleteMapping("/delete/{sessionId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(sessionServiceImpl.deleteSession(sessionId));
    }
}

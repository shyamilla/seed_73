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

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.serviceimpl.TraineeServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trainees")
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeServiceImpl traineeServiceImpl;

    // @PostMapping("/add")
    // public ResponseEntity<TraineeResponseDto> createTrainee(@RequestBody TraineeRequestDto requestDto) throws ResourceNotFoundException {
    //     return ResponseEntity.status(HttpStatus.CREATED).body(traineeServiceImpl.createTrainee(requestDto));
    // }

    @PutMapping("/update/{traineeId}")
    public ResponseEntity<TraineeResponseDto> updateTrainee(@PathVariable Long traineeId,
            @RequestBody TraineeRequestDto requestDto) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(traineeServiceImpl.updateTrainee(traineeId, requestDto));
    }

    @GetMapping("/find/{traineeId}")
    public ResponseEntity<TraineeResponseDto> updateById(@PathVariable Long traineeId)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(traineeServiceImpl.getTraineeById(traineeId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TraineeResponseDto>> getAllTrainees() {
        return ResponseEntity.status(HttpStatus.OK).body(traineeServiceImpl.getAllTrainees());
    }

    @DeleteMapping("/delete/{traineeId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long traineeId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(traineeServiceImpl.deleteTrainee(traineeId));
    }

}

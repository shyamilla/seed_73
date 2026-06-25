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

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.serviceimpl.TrainerServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerServiceImpl trainerServiceImpl;

    

    // @PostMapping("/add")
    // public ResponseEntity<TrainerResponseDto> createTrainer(@Valid @RequestBody TrainerRequestDto requestDto) throws ResourceNotFoundException {
    //     return ResponseEntity.status(HttpStatus.CREATED).body(trainerServiceImpl.createTrainer(requestDto));
    // }

    @PutMapping("/update/{trainerId}")
    public ResponseEntity<TrainerResponseDto> updateTrainer(@PathVariable Long trainerId,
            @Valid @RequestBody TrainerRequestDto requestDto) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(trainerServiceImpl.updateTrainer(trainerId, requestDto));
    }

    @GetMapping("/find/{trainerId}")
    public ResponseEntity<TrainerResponseDto> findById(@PathVariable Long trainerId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(trainerServiceImpl.getTrainerById(trainerId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrainerResponseDto>> getAllTrainers() {
        return ResponseEntity.status(HttpStatus.OK).body(trainerServiceImpl.getAllTrainers());
    }

    @DeleteMapping("/delete/{trainerId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long trainerId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(trainerServiceImpl.deleteTrainer(trainerId));
    }

}

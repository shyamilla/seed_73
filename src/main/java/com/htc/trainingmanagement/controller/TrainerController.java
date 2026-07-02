package com.htc.trainingmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;
import com.htc.trainingmanagement.dto.response.TrainerTraineeResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.service.TrainerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {

        private final TrainerService trainerService;

        

        @PutMapping("/update/{trainerId}")
        public ResponseEntity<TrainerResponseDto> updateTrainer(
                        @PathVariable Long trainerId,
                        @Valid @RequestBody TrainerRequestDto requestDto)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.updateTrainer(trainerId, requestDto));
        }

        @GetMapping("/find/{trainerId}")
        public ResponseEntity<TrainerResponseDto> getTrainerById(
                        @PathVariable Long trainerId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.getTrainerById(trainerId));
        }

        @GetMapping("/all")
        public ResponseEntity<List<TrainerResponseDto>> getAllTrainers() {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.getAllTrainers());
        }

        @DeleteMapping("/delete/{trainerId}")
        public ResponseEntity<Boolean> deleteTrainer(
                        @PathVariable Long trainerId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.deleteTrainer(trainerId));
        }

        @GetMapping("/specialization/{specialization}")
        public ResponseEntity<List<TrainerResponseDto>> getTrainersBySpecialization(
                        @PathVariable String specialization) {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.getTrainersBySpecialization(specialization));
        }

        @GetMapping("/experience/{yearsOfExperience}")
        public ResponseEntity<List<TrainerResponseDto>> getExperiencedTrainers(
                        @PathVariable Integer yearsOfExperience) {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.getExperiencedTrainers(yearsOfExperience));
        }

        @PatchMapping("/{trainerId}/specialization")
        public ResponseEntity<TrainerResponseDto> updateTrainerSpecialization(
                        @PathVariable Long trainerId,
                        @RequestParam String specialization)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.updateTrainerSpecialization(
                                                trainerId,
                                                specialization));
        }

        @GetMapping("/my-trainees")
        public ResponseEntity<List<TrainerTraineeResponseDto>> getMyTrainees() throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.getMyTrainees());
        }

        @GetMapping("/inactive")
        public ResponseEntity<List<TrainerResponseDto>> getInactiveTrainers() {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.getInactiveTrainers());
        }

        @GetMapping("/active")
        public ResponseEntity<List<TrainerResponseDto>> getActiveTrainers() {
                return ResponseEntity.status(HttpStatus.OK).body(trainerService.getActiveTrainers());
        }
}
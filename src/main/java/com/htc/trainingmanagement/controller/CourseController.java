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

import com.htc.trainingmanagement.dto.request.CourseDurationUpdateDto;
import com.htc.trainingmanagement.dto.request.CourseRequestDto;
import com.htc.trainingmanagement.dto.response.CourseAdminResponseDto;
import com.htc.trainingmanagement.dto.response.CourseResponseDto;
import com.htc.trainingmanagement.enums.CourseStatus;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.service.CourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

        private final CourseService courseService;

        @PostMapping("/add")
        public ResponseEntity<CourseResponseDto> createCourse(
                        @Valid @RequestBody CourseRequestDto requestDto)
                        throws DuplicateResourceException {

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(courseService.createCourse(requestDto));
        }

        @PutMapping("/update/{courseId}")
        public ResponseEntity<CourseResponseDto> updateCourse(
                        @PathVariable Long courseId,
                        @Valid @RequestBody CourseRequestDto requestDto)
                        throws ResourceNotFoundException, DuplicateResourceException {

                return ResponseEntity.ok(
                                courseService.updateCourse(courseId, requestDto));
        }

        @GetMapping("/all")
        public ResponseEntity<List<CourseResponseDto>> getAllCourses() {

                return ResponseEntity.ok(courseService.getAllCourses());
        }

        @GetMapping("/find/{courseId}")
        public ResponseEntity<CourseResponseDto> getCourseById(
                        @PathVariable Long courseId)
                        throws ResourceNotFoundException {

                return ResponseEntity.ok(
                                courseService.getCourseById(courseId));
        }

        @DeleteMapping("/delete/{courseId}")
        public ResponseEntity<Boolean> deleteCourse(
                        @PathVariable Long courseId)
                        throws ResourceNotFoundException {

                return ResponseEntity.ok(
                                courseService.deleteCourse(courseId));
        }

        @GetMapping("/search")
        public ResponseEntity<List<CourseResponseDto>> searchCourses(
                        @RequestParam String name) {

                return ResponseEntity.ok(
                                courseService.searchCoursesByName(name));
        }

        @GetMapping("/active")
        public ResponseEntity<List<CourseResponseDto>> getActiveCourses() {

                return ResponseEntity.ok(
                                courseService.getActiveCourses());
        }

        @PatchMapping("/{courseId}/duration")
        public ResponseEntity<CourseResponseDto> updateCourseDuration(
                        @PathVariable Long courseId,
                        @Valid @RequestBody CourseDurationUpdateDto dto)
                        throws ResourceNotFoundException {

                return ResponseEntity.ok(
                                courseService.updateCourseDuration(
                                                courseId,
                                                dto.getDurationInDays()));
        }

        @PatchMapping("/{courseId}/status")
        public ResponseEntity<CourseResponseDto> updateCourseStatus(
                        @PathVariable Long courseId,
                        @RequestParam CourseStatus status)
                        throws ResourceNotFoundException {

                return ResponseEntity.ok(
                                courseService.updateCourseStatus(courseId, status));
        }

        @GetMapping("/admin/{id}")
        public ResponseEntity<CourseAdminResponseDto> getCourseAdminById(@PathVariable Long id) throws ResourceNotFoundException {

                CourseAdminResponseDto response = courseService.getCourseAdminById(id);
                return ResponseEntity.ok(response);
        }
}
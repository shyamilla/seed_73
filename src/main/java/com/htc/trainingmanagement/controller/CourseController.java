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

import com.htc.trainingmanagement.dto.request.CourseRequestDto;
import com.htc.trainingmanagement.dto.response.CourseResponseDto;
import com.htc.trainingmanagement.serviceimpl.CourseServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServiceImpl courseServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto reqDto) {
        CourseResponseDto responseDto = courseServiceImpl.createCourse(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long courseId,
            @Valid @RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(courseServiceImpl.updateCourse(courseId, requestDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(courseServiceImpl.getAllCourses());
    }

    @GetMapping("/find/{courseId}")
    public ResponseEntity<CourseResponseDto> findById(@PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseServiceImpl.getCourseById(courseId));
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseServiceImpl.deleteCourse(courseId));
    }

}

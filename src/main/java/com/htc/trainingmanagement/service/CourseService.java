package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.CourseRequestDto;
import com.htc.trainingmanagement.dto.response.CourseResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface CourseService {

    CourseResponseDto createCourse(CourseRequestDto requestDto);

    CourseResponseDto getCourseById(Long courseId) throws ResourceNotFoundException;

    List<CourseResponseDto> getAllCourses();

    CourseResponseDto updateCourse(Long courseId,CourseRequestDto requestDto) throws ResourceNotFoundException;

    boolean deleteCourse(Long courseId) throws ResourceNotFoundException;
}
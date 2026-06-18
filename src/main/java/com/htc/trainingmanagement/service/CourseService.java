package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.CourseRequestDto;
import com.htc.trainingmanagement.dto.response.CourseResponseDto;

public interface CourseService {

    CourseResponseDto createCourse(CourseRequestDto requestDto);

    CourseResponseDto getCourseById(Long courseId);

    List<CourseResponseDto> getAllCourses();

    CourseResponseDto updateCourse(Long courseId,CourseRequestDto requestDto);

    boolean deleteCourse(Long courseId);
}
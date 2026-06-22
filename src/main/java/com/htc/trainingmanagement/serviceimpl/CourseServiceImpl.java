package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.CourseRequestDto;
import com.htc.trainingmanagement.dto.response.CourseResponseDto;
import com.htc.trainingmanagement.entity.Course;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.CourseMapper;
import com.htc.trainingmanagement.repository.CourseRepository;
import com.htc.trainingmanagement.service.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto requestDto) throws DuplicateResourceException {
        if (courseRepository.existsByCourseName(requestDto.getCourseName())) {
            throw new DuplicateResourceException("Course already exists with name: "
                    + requestDto.getCourseName());
        }

        Course course = courseMapper.toEntity(requestDto);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponseDto(savedCourse);
    }

    @Override
    public CourseResponseDto getCourseById(Long courseId) throws ResourceNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + courseId));
        return courseMapper.toResponseDto(course);
    }

    @Override
    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponseDto)
                .toList();
    }

    @Override
    public CourseResponseDto updateCourse(Long courseId, CourseRequestDto requestDto) throws ResourceNotFoundException {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + courseId));

        courseMapper.updateEntity(course, requestDto);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toResponseDto(updatedCourse);
    }

    @Override
    public boolean deleteCourse(Long courseId) throws ResourceNotFoundException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + courseId));
        courseRepository.delete(course);
        return true;
    }
}
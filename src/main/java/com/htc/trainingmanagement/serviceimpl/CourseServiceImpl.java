package com.htc.trainingmanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.CourseRequestDto;
import com.htc.trainingmanagement.dto.response.CourseResponseDto;
import com.htc.trainingmanagement.entity.Course;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.repository.CourseRepository;
import com.htc.trainingmanagement.service.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto requestDto)
            throws DuplicateResourceException {

        if (courseRepository.existsByCourseName(requestDto.getCourseName())) {
            throw new DuplicateResourceException(
                    "Course already exists with name: "
                            + requestDto.getCourseName());
        }

        Course course = new Course();

        course.setCourseName(requestDto.getCourseName());
        course.setDescription(requestDto.getDescription());
        course.setDurationInDays(requestDto.getDurationInDays());
        course.setMaxCapacity(requestDto.getMaxCapacity());
        course.setStatus(requestDto.getStatus());

        Course savedCourse = courseRepository.save(course);

        return convertToResponseDto(savedCourse);
    }

    @Override
    public CourseResponseDto getCourseById(Long courseId)
            throws ResourceNotFoundException {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + courseId));

        return convertToResponseDto(course);
    }

    @Override
    public List<CourseResponseDto> getAllCourses() {

        List<Course> courses = courseRepository.findAll();

        List<CourseResponseDto> responseDtos = new ArrayList<>();

        for (Course course : courses) {
            responseDtos.add(convertToResponseDto(course));
        }

        return responseDtos;
    }

    @Override
    public CourseResponseDto updateCourse(
            Long courseId,
            CourseRequestDto requestDto)
            throws ResourceNotFoundException {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + courseId));

        course.setCourseName(requestDto.getCourseName());
        course.setDescription(requestDto.getDescription());
        course.setDurationInDays(requestDto.getDurationInDays());
        course.setMaxCapacity(requestDto.getMaxCapacity());
        course.setStatus(requestDto.getStatus());

        Course updatedCourse = courseRepository.save(course);

        return convertToResponseDto(updatedCourse);
    }

    @Override
    public boolean deleteCourse(Long courseId)
            throws ResourceNotFoundException {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + courseId));

        courseRepository.delete(course);

        return true;
    }

    private CourseResponseDto convertToResponseDto(Course course) {

        return new CourseResponseDto(
                course.getCourseId(),
                course.getCourseName(),
                course.getDescription(),
                course.getDurationInDays(),
                course.getMaxCapacity(),
                course.getStatus(),
                course.getCreatedAt(),
                course.getUpdatedAt());
    }
}
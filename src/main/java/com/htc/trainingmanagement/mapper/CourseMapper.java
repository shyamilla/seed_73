package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.CourseRequestDto;
import com.htc.trainingmanagement.dto.response.CourseResponseDto;
import com.htc.trainingmanagement.entity.Course;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequestDto requestDto) {

        Course course = new Course();

        course.setCourseName(requestDto.getCourseName());
        course.setDescription(requestDto.getDescription());
        course.setDurationInDays(requestDto.getDurationInDays());
        course.setMaxCapacity(requestDto.getMaxCapacity());
        course.setStatus(requestDto.getStatus());

        return course;
    }

    public void updateEntity(Course course, CourseRequestDto requestDto) {

        course.setCourseName(requestDto.getCourseName());
        course.setDescription(requestDto.getDescription());
        course.setDurationInDays(requestDto.getDurationInDays());
        course.setMaxCapacity(requestDto.getMaxCapacity());
        course.setStatus(requestDto.getStatus());
    }

    public CourseResponseDto toResponseDto(Course course) {

        return new CourseResponseDto(
                course.getCourseId(),
                course.getCourseName(),
                course.getDescription(),
                course.getDurationInDays(),
                course.getMaxCapacity(),
                course.getStatus(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }
}
package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.CourseRequestDto;
import com.htc.trainingmanagement.dto.response.CourseAdminResponseDto;
import com.htc.trainingmanagement.dto.response.CourseResponseDto;
import com.htc.trainingmanagement.entity.Course;
import com.htc.trainingmanagement.enums.BatchStatus;
import com.htc.trainingmanagement.enums.CourseStatus;
import com.htc.trainingmanagement.exception.CourseException;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.CourseMapper;
import com.htc.trainingmanagement.repository.CourseRepository;
import com.htc.trainingmanagement.repository.TrainingBatchRepository;
import com.htc.trainingmanagement.service.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

        private final CourseRepository courseRepository;
        private final CourseMapper courseMapper;
        private final TrainingBatchRepository trainingBatchRepository;

        @Override
        public CourseResponseDto createCourse(CourseRequestDto requestDto)
                        throws DuplicateResourceException {

                // Checks whether a course with the same name already exists.
                if (courseRepository.existsByCourseName(requestDto.getCourseName())) {
                        throw new DuplicateResourceException(
                                        "Course already exists with name: " + requestDto.getCourseName());
                }

                Course course = courseMapper.toEntity(requestDto);

                Course savedCourse = courseRepository.save(course);

                return courseMapper.toResponseDto(savedCourse);
        }

        @Override
        public CourseResponseDto getCourseById(Long courseId)
                        throws ResourceNotFoundException {

                Course course = getCourseEntityById(courseId);

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
        public CourseResponseDto updateCourse(Long courseId, CourseRequestDto requestDto)
                        throws ResourceNotFoundException, DuplicateResourceException, CourseException {

                Course course = getCourseEntityById(courseId);

                if (requestDto.getStatus() == CourseStatus.INACTIVE) {
                        validateCourseCanBeInactive(course);
                }

                // Prevents updating a course with another existing course name.
                if (!course.getCourseName().equalsIgnoreCase(requestDto.getCourseName())
                                && courseRepository.existsByCourseName(requestDto.getCourseName())) {
                        throw new DuplicateResourceException(
                                        "Course already exists with name: " + requestDto.getCourseName());
                }

                courseMapper.updateEntity(course, requestDto);

                Course updatedCourse = courseRepository.save(course);

                return courseMapper.toResponseDto(updatedCourse);
        }

        @Override
        public boolean deleteCourse(Long courseId)
                        throws ResourceNotFoundException, CourseException {

                Course course = getCourseEntityById(courseId);

                validateCourseCanBeDeleted(courseId);

                courseRepository.delete(course);

                return true;
        }

        @Override
        public List<CourseResponseDto> searchCoursesByName(String courseName) {

                return courseRepository.findByCourseNameContainingIgnoreCase(courseName)
                                .stream()
                                .map(courseMapper::toResponseDto)
                                .toList();
        }

        @Override
        public List<CourseResponseDto> getActiveCourses() {

                return courseRepository.findByStatus(CourseStatus.ACTIVE)
                                .stream()
                                .map(courseMapper::toResponseDto)
                                .toList();
        }

        @Override
        public CourseResponseDto updateCourseDuration(Long courseId, Integer duration)
                        throws ResourceNotFoundException {

                // Course duration should be greater than zero.
                if (duration == null || duration <= 0) {
                        throw new IllegalArgumentException(
                                        "Course duration must be greater than 0");
                }

                Course course = getCourseEntityById(courseId);

                course.setDurationInDays(duration);

                Course updatedCourse = courseRepository.save(course);

                return courseMapper.toResponseDto(updatedCourse);
        }

        @Override
        public CourseResponseDto updateCourseStatus(Long courseId, CourseStatus status)
                        throws ResourceNotFoundException {

                if (status == null) {
                        throw new IllegalArgumentException("Course status is required");
                }

                Course course = getCourseEntityById(courseId);

                // Updates only the course status.
                course.setStatus(status);

                Course updatedCourse = courseRepository.save(course);

                return courseMapper.toResponseDto(updatedCourse);
        }

        private Course getCourseEntityById(Long courseId)
                        throws ResourceNotFoundException {

                return courseRepository.findById(courseId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Course not found with id: " + courseId));
        }

        public CourseAdminResponseDto getCourseAdminById(Long courseId) throws ResourceNotFoundException {

                Course course = courseRepository.findById(courseId)
                                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

                return courseMapper.toAdminResponseDto(course);
        }

        private void validateCourseCanBeDeleted(Long courseId)
                        throws CourseException {

                if (trainingBatchRepository.existsByCourseCourseId(courseId)) {
                        throw new CourseException(
                                        "Course cannot be deleted because batches are assigned to it.");
                }
        }

        // Helper method to prevent making a course inactive if active batches exist.
        private void validateCourseCanBeInactive(Course course)
                        throws CourseException {

                boolean hasActiveBatches = trainingBatchRepository.existsByCourseAndStatus(
                                course,
                                BatchStatus.ONGOING);

                if (hasActiveBatches) {
                        throw new CourseException(
                                        "Course cannot be made inactive because active batches exist.");
                }
        }
}
package com.htc.trainingmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Handles exceptions thrown by all REST controllers globally
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles ResourceNotFoundException (404 Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handles DuplicateResourceException (409 Conflict)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleDuplicateResourceException(DuplicateResourceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // Handles CapacityExceededException (409 Conflict)
    @ExceptionHandler(CapacityExceededException.class)
    public ResponseEntity<Object> handleCapacityExceededException(CapacityExceededException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // Handles EnrollmentException (400 Bad Request)
    @ExceptionHandler(EnrollmentException.class)
    public ResponseEntity<Object> handleEnrollmentException(EnrollmentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handles AttendanceException (400 Bad Request)
    @ExceptionHandler(AttendanceException.class)
    public ResponseEntity<Object> handleAttendanceException(AttendanceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handles any unhandled exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<Object> handleCourseException(CourseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TrainerException.class)
    public ResponseEntity<Object> handleTrainerException(TrainerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BatchException.class)
    public ResponseEntity<Object> handleBatchException(BatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // Handles UserException (401 Unauthorized)
    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // security by spring to validate inactive user
    @ExceptionHandler(org.springframework.security.authentication.DisabledException.class)
    public ResponseEntity<Object> handleDisabledException(
            org.springframework.security.authentication.DisabledException e) {

        return new ResponseEntity<>(
                "User account is inactive. Please contact the administrator.",
                HttpStatus.UNAUTHORIZED);
    }
}
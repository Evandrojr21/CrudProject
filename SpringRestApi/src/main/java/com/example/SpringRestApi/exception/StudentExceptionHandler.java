package com.example.SpringRestApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentExceptionHandler {

  @ExceptionHandler(value = { StudentNotFoundException.class})
  public ResponseEntity<Object> handleStudentNotFoundException(StudentNotFoundException studentNotFoundException){
    StudentException studentException = new StudentException(
        studentNotFoundException.getMessage(),
        studentNotFoundException.getCause(),
        HttpStatus.NOT_FOUND
    );

    return new ResponseEntity<>(studentException,HttpStatus.NOT_FOUND);
  }

}

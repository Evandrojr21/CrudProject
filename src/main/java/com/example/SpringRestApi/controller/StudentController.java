package com.example.SpringRestApi.controller;

import com.example.SpringRestApi.dto.StudentDto;
import com.example.SpringRestApi.entity.Student;
import com.example.SpringRestApi.mapper.StudentConvert;
import com.example.SpringRestApi.repository.StudentRepository;
import com.example.SpringRestApi.service.StudentService;
import com.example.SpringRestApi.sqs.model.StudentUpdateMessage;
import com.example.SpringRestApi.sqs.model.UpdateType;
import com.example.SpringRestApi.sqs.service.MessageSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

  @RestController
  public class StudentController {

    @Autowired
    StudentRepository repo;

    @Autowired
    MessageSender messageSender;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentConvert studentConvert;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/students")
    public List<StudentDto> getAllStudents() throws JsonProcessingException {
      List<Student> students = studentService.getAllStudents();
      List<StudentDto> studentDtos = students.stream()
          .map(studentConvert::convertStudentToStudentDTO)
          .collect(Collectors.toList());

      String message = objectMapper.writeValueAsString(new StudentUpdateMessage("All Students Retrieved", null, UpdateType.GET));
      messageSender.sendMessage(message);

      return studentDtos;
    }

    @GetMapping("/students/{id}")
    public StudentDto getStudentById(@PathVariable int id) throws JsonProcessingException {
      Student student = studentService.getStudentById(id);
      StudentDto studentDto = studentConvert.convertStudentToStudentDTO(student);

      StudentUpdateMessage studentGetMessage = new StudentUpdateMessage(student.getName(), student.getBranch(), UpdateType.GET);
      String stringMessage = objectMapper.writeValueAsString(studentGetMessage);
      messageSender.sendMessage(stringMessage);

      return studentDto;
    }

    @PostMapping("/students/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody @Valid StudentDto studentDto) throws JsonProcessingException {
      Student student = studentConvert.convertStudentDTOtoStudent(studentDto);
      studentService.createStudent(student);
    }

    @PutMapping("/students/{id}")
    public StudentDto updateStudent(@PathVariable int id, @RequestBody StudentDto studentDto) throws JsonProcessingException {
      Student student = studentConvert.convertStudentDTOtoStudent(studentDto);
      Student updatedStudent = studentService.updateStudent(id, student);

      StudentDto updatedStudentDto = studentConvert.convertStudentToStudentDTO(updatedStudent);

      StudentUpdateMessage studentUpdateMessage = new StudentUpdateMessage(updatedStudentDto.getName(), updatedStudentDto.getBranch(), UpdateType.UPDATE);
      String stringMessage = objectMapper.writeValueAsString(studentUpdateMessage);
      messageSender.sendMessage(stringMessage);

      return updatedStudentDto;
    }

  @DeleteMapping("/students/{id}")
  public void removeStudent(@PathVariable int id) throws JsonProcessingException {
    studentService.deleteStudent(id);
  }

}

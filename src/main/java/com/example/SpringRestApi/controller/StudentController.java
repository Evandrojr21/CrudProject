package com.example.SpringRestApi.controller;

import com.example.SpringRestApi.dto.StudentDto;
import com.example.SpringRestApi.entity.Student;
import com.example.SpringRestApi.mapper.StudentMapper;
import com.example.SpringRestApi.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    StudentService studentService;

    @Autowired
    StudentMapper studentMapper;


    @GetMapping("/students")
    public List<StudentDto> getAllStudents() throws JsonProcessingException {
      List<Student> students = studentService.getAllStudents();
      List<StudentDto> studentDtos = students.stream()
          .map(studentMapper::convertStudentToStudentDTO)
          .collect(Collectors.toList());

      return studentDtos;
    }

    @GetMapping("/students/{id}")
    public StudentDto getStudentById(@PathVariable int id) throws JsonProcessingException {
      Student student = studentService.getStudentById(id);

      return studentMapper.convertStudentToStudentDTO(student);
    }

    @PostMapping("/students/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody @Valid StudentDto studentDto) throws JsonProcessingException {
      Student student = studentMapper.convertStudentDTOtoStudent(studentDto);
      studentService.createStudent(student);
    }

    @PutMapping("/students/{id}")
    public StudentDto updateStudent(@PathVariable int id, @RequestBody StudentDto studentDto) throws JsonProcessingException {
      Student student = studentMapper.convertStudentDTOtoStudent(studentDto);
      Student updatedStudent = studentService.updateStudent(id, student);

      return studentMapper.convertStudentToStudentDTO(updatedStudent);
    }

  @DeleteMapping("/students/{id}")
  public void removeStudent(@PathVariable int id) throws JsonProcessingException {
    studentService.deleteStudent(id);
  }

}

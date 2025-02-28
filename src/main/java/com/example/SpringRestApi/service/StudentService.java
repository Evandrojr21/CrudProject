package com.example.SpringRestApi.service;

import com.example.SpringRestApi.entity.Student;
import com.example.SpringRestApi.exception.StudentNotFoundException;
import com.example.SpringRestApi.mapper.StudentConvert;
import com.example.SpringRestApi.repository.StudentRepository;
import com.example.SpringRestApi.sqs.model.StudentUpdateMessage;
import com.example.SpringRestApi.sqs.model.UpdateType;
import com.example.SpringRestApi.sqs.service.MessageSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

  @Autowired
  MessageSender messageSender;

  ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  StudentConvert studentConvert;

  @Autowired
  StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Student getStudentById(int id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found."));
  }

  public void createStudent(Student student) throws JsonProcessingException {
    studentRepository.save(student);

    StudentUpdateMessage studentCreatedMessage = new StudentUpdateMessage(student.getName(), student.getBranch(), UpdateType.NEW);
    String createdMessage = objectMapper.writeValueAsString(studentCreatedMessage);
    messageSender.sendMessage(createdMessage);
  }

  public Student updateStudent(int id, Student updatedStudent) {
    return studentRepository.findById(id).map(existingStudent -> {
      existingStudent.setName(updatedStudent.getName());
      existingStudent.setBranch(updatedStudent.getBranch());
      existingStudent.setPercentage(updatedStudent.getPercentage());
      return studentRepository.save(existingStudent);
    }).orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found."));
  }

  public void deleteStudent(int id) throws JsonProcessingException {
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found."));
    StudentUpdateMessage studentDeleteMessage = new StudentUpdateMessage(student.getName(),student.getBranch(), UpdateType.DELETE);
    String message = objectMapper.writeValueAsString(studentDeleteMessage);
    studentRepository.delete(student);
    messageSender.sendMessage(message);
  }
}

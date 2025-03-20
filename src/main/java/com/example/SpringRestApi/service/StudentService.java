package com.example.SpringRestApi.service;

import com.example.SpringRestApi.entity.Student;
import com.example.SpringRestApi.exception.StudentNotFoundException;
import com.example.SpringRestApi.repository.StudentRepository;
import com.example.SpringRestApi.sqs.model.UpdateType;
import com.example.SpringRestApi.sqs.service.MessageSender;
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
  StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;

  }

  public List<Student> getAllStudents() {
    List<Student> students = studentRepository.findAll();

    return students;
  }


  public Student getStudentById(int id) {
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found."));
    messageSender.sendStudentMessage(student.getName(), student.getBranch(), UpdateType.GET);

    return student;
  }

  public void createStudent(Student student){
    studentRepository.save(student);
    messageSender.sendStudentMessage(student.getName(), student.getBranch(), UpdateType.NEW);

  }

  public Student updateStudent(int id, Student updatedStudent) {
    Student student = studentRepository.findById(id).map(existingStudent -> {
      existingStudent.setName(updatedStudent.getName());
      existingStudent.setBranch(updatedStudent.getBranch());
      existingStudent.setPercentage(updatedStudent.getPercentage());
      return studentRepository.save(existingStudent);
    }).orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found."));

    messageSender.sendStudentMessage(student.getName(), student.getBranch(), UpdateType.UPDATE);
    return student;
  }

  public void deleteStudent(int id){
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found."));
    studentRepository.delete(student);
    messageSender.sendStudentMessage(student.getName(), student.getBranch(), UpdateType.DELETE);
  }
}

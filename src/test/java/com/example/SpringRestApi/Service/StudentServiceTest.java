package com.example.SpringRestApi.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;


import com.example.SpringRestApi.entity.Student;
import com.example.SpringRestApi.repository.StudentRepository;
import com.example.SpringRestApi.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class StudentServiceTest {

 @Autowired
 EntityManager entityManager;

 @InjectMocks
 private StudentService studentService;

 @Mock
 private StudentRepository studentRepository;

 @BeforeEach
 void setUp() {
  Student student = new Student("Heitor",76.01f,"IT");
  lenient().when(studentRepository.findAll()).thenReturn(Collections.singletonList(student));
  lenient().when(studentRepository.findById(0)).thenReturn(Optional.of(student));
 }

 @Test
 @DisplayName("Should return a list with only one student")
 public void getAlltudentsSucess() throws JsonProcessingException {
  List<Student> students = studentService.getAllStudents();
  assertEquals(1,students.size());
 }

 @Test
 @DisplayName("Should return a list with only one student")
 public void getAlltudentsError(){
  List<Student> students = studentService.getAllStudents();
  assertEquals(students,students.size());

 }

 @Test
 @DisplayName("Get student by Id")
 public void getSudentByIdSucess() throws JsonProcessingException {
  Student searchStudent = studentService.getStudentById(0);
  assertNotNull(searchStudent);
  assertThat(searchStudent.getId()).isEqualTo(0);
  System.out.println(searchStudent);
 }

}

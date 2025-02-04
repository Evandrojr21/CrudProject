package com.example.SpringRestApi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.SpringRestApi.entity.Student;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Student successfully from DB")
    void findStudentByNameSucess() {
      Student data = new Student("Fernanda",76.01f,"IT");
      this.createStudent(data);

      Optional<Student> result = this.studentRepository.findStudentByName(data.getName());

      assertThat(result.isPresent()).isTrue();
    }

  @Test
  @DisplayName("Should not get Student successfully from DB")
  void findStudentByNameError() {
    Student data = new Student("Cleyton",66.83f,"IT");
    this.createStudent(data);
    Optional<Student> result = this.studentRepository.findStudentByName("Lorem");

    assertThat(result.isEmpty()).isTrue();
  }

    private Student createStudent(Student data){
      Student newStudent = new Student(data);
      this.entityManager.persist(newStudent);
      this.entityManager.flush();
      return newStudent;
    }

  }

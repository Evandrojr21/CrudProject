package com.example.SpringRestApi.repository;

import com.example.SpringRestApi.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer>{

   Optional<Student> findStudentByName(String name);
}

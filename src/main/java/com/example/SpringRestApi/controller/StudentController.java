package com.example.SpringRestApi.controller;

import com.example.SpringRestApi.entity.Student;
import com.example.SpringRestApi.exception.StudentNotFoundException;
import com.example.SpringRestApi.repository.StudentRepository;
import java.util.List;
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


  @GetMapping("/students")
  public List<Student> getALStudents(){
    List<Student> students = repo.findAll();
      return students;
    }

  @GetMapping("/students/{id}")
  public Student getStudentById(@PathVariable int id){
    if (repo.findById(id).isEmpty()){
      throw new StudentNotFoundException("Requested Student doesn't exist");
    }
    Student student = repo.findById(id).get();
      return student;
  }

  @PostMapping("student/add")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void createStudent(@RequestBody Student student){
    repo.save(student);
  }

  @PutMapping("/student/update/{id}")
  public Student updateStudents(@PathVariable int id){
    Student student = repo.findById(id).get();
    student.setName("loremipsum");
    student.setPercentage(92);
    repo.save(student);
    return student;
  }

  @DeleteMapping("/student/delete/{id}")
  public void removeStudent(@PathVariable int id){
  Student student = repo.findById(id).get();
  repo.delete(student);
  }

}

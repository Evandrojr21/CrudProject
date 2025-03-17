package com.example.SpringRestApi.mapper;

import com.example.SpringRestApi.dto.StudentDto;
import com.example.SpringRestApi.entity.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
  @Autowired
  private ModelMapper modelMapper;

  public StudentDto convertStudentToStudentDTO(Student student){

    StudentDto studentDto =  modelMapper.map(student,StudentDto.class);
        return studentDto;
  }

  public Student convertStudentDTOtoStudent(StudentDto studentDto){
    Student student =  modelMapper.map(studentDto,Student.class);
    return student;
  }

}

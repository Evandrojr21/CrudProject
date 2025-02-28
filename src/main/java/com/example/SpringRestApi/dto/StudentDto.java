package com.example.SpringRestApi.dto;

import com.example.SpringRestApi.entity.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StudentDto {

  @JsonIgnore
  private int id;

  @NotEmpty(message = "The Student name cannot be empty")
  @Size(min = 3, max = 15, message = "Invalid name")
  private String name;

  @Min(value = 0, message = "the percentage cannot be less than 0 ")
  @Max(value = 100, message = "the percentage cannot be greater than 100 ")
  private float percentage;

  @NotNull(message = "The student branch cannot be null")
  private String branch;

  public StudentDto() {
  }

  public StudentDto(Student student) {
    id = student.getId();
    name = student.getName();
    percentage = student.getPercentage();
    branch = student.getBranch();
  }

  public StudentDto(int id, String name, String branch) {
    this.id = id;
    this.name = name;
    this.branch = branch;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBranch() {
    return branch;
  }

  public float getPercentage() {
    return percentage;
  }

  public void setPercentage(float percentage) {
    this.percentage = percentage;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

}
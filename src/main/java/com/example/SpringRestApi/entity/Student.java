package com.example.SpringRestApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotEmpty(message = "The Student name cannot be empty")
  @Size(min = 3, max = 15, message = "Invalid name")
  @Column
  private String name;


  @Min(value = 0, message = "the percentage cannot be less than 0 ")
  @Max(value = 100, message = "the percentage cannot be greater than 100 ")
  @Column
  private float percentage;

  @NotNull(message = "The student branch cannot be null")
  @Column
  private String branch;

  public Student() {
  }

  public Student(Student studentData){
    this.name = studentData.name;
    this.percentage = studentData.percentage;
    this.branch = studentData.branch;
  }

  public Student(String name, float percentage, String branch) {
    this.name = name;
    this.percentage = percentage;
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

  public float getPercentage() {
    return percentage;
  }

  public void setPercentage(float percentage) {
    this.percentage = percentage;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", percentage=" + percentage +
        ", branch='" + branch + '\'' +
        '}';
  }
}

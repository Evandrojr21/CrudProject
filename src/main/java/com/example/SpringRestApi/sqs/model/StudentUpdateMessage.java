package com.example.SpringRestApi.sqs.model;


public class StudentUpdateMessage {

  private String name;
  private UpdateType type;
  private String branch;

  public StudentUpdateMessage(String name, String branch,UpdateType type) {
    this.name = name;
    this.branch = branch;
    this.type = type;
  }

  public StudentUpdateMessage(String name) {
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UpdateType getType() {
    return type;
  }

  public void setType(UpdateType type) {
    this.type = type;
  }
}

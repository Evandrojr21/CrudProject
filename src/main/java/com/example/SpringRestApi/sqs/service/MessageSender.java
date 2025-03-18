package com.example.SpringRestApi.sqs.service;

import com.example.SpringRestApi.sqs.model.StudentUpdateMessage;
import com.example.SpringRestApi.sqs.model.UpdateType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class MessageSender {

  private final SqsAsyncClient sqsAsyncClient;
  private final ObjectMapper objectMapper;

  @Value("${sqsQueueName}")
  private String queueName;

  @Autowired
  public MessageSender(SqsAsyncClient sqsAsyncClient, ObjectMapper objectMapper) {
    this.sqsAsyncClient = sqsAsyncClient;
    this.objectMapper = objectMapper;
  }

  public void sendStudentMessage(String name, String branch, UpdateType updateType) {
    StudentUpdateMessage message = new StudentUpdateMessage(name, branch, updateType);

    try {
      String jsonMessage = objectMapper.writeValueAsString(message);
      SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
          .queueUrl(queueName)
          .messageBody(jsonMessage)
          .delaySeconds(5)
          .build();
      sqsAsyncClient.sendMessage(sendMsgRequest);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize message", e);
    }
  }
}
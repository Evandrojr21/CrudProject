package com.example.SpringRestApi.sqs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class MessageSender {

  private SqsAsyncClient sqsAsyncClient;

  private final String queueName="sqsSpringRestApi";

  @Autowired
  public MessageSender(SqsAsyncClient sqsAsyncClient){
    this.sqsAsyncClient =sqsAsyncClient;
  }

  public void sendMessage(String message) {
    SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
        .queueUrl(queueName)
        .messageBody(message)
        .delaySeconds(5)
        .build();

    sqsAsyncClient.sendMessage(sendMsgRequest);
  }
}

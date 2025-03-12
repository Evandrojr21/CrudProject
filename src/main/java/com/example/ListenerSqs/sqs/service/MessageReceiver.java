package com.example.ListenerSqs.sqs.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

@Component
public class MessageReceiver {
  private static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);
  private final SqsAsyncClient sqsAsyncClient;
  private final String queueUrl = "http://localhost:4566/000000000000/sqsSpringRestApi";

  public MessageReceiver(SqsAsyncClient sqsAsyncClient) {
    this.sqsAsyncClient = sqsAsyncClient;
  }

  @PostConstruct
  public void startReceivingMessages() {
    new Thread(this::receiveMessagesContinuously).start();
  }

  private void receiveMessagesContinuously() {
    log.info("Iniciando recebimento contínuo de mensagens...");
    while (true) {
      try {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .waitTimeSeconds(4)
            .visibilityTimeout(60)
            .build();

        List<Message> messages = sqsAsyncClient.receiveMessage(receiveMessageRequest).join().messages();

        if (messages != null && !messages.isEmpty()) {
          for (Message message : messages) {
            log.info("Mensagem recebida: {}", message.body());
          
            try {
                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
                sqsAsyncClient.deleteMessage(deleteMessageRequest);
            } catch (SqsException e) {
              System.err.println(e.awsErrorDetails().errorMessage());
              System.exit(1);
            }

          }
        } else {
          log.info("Nenhuma mensagem na fila.");
        }

        Thread.sleep(4000);
      } catch (SqsException e) {
        log.error("Erro ao receber mensagens: {}", e.awsErrorDetails().errorMessage());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        log.error("Thread interrompida. Encerrando recebimento contínuo.");
        break;
      }
    }
  }
}

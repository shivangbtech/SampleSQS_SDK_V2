package com.example.sqs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsSender {

  private final SqsClient sqsClient;

  @Value("${sqs.queueName}")
  private String queueName;

  public SqsSender(SqsClient sqsClient) {
    this.sqsClient = sqsClient;
  }

  public void send(String message) {
    String queueUrl = sqsClient.getQueueUrl(b -> b.queueName(queueName)).queueUrl();
    SendMessageRequest request = SendMessageRequest.builder()
        .queueUrl(queueUrl)
        .messageBody(message)
        .build();
    sqsClient.sendMessage(request);
    System.out.println("Message sent: " + message);
  }
}
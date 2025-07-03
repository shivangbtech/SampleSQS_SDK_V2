package com.example.sqs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

@Service
public class SqsReceiver {

  private final SqsClient sqsClient;

  @Value("${sqs.queueName}")
  private String queueName;

  public SqsReceiver(SqsClient sqsClient) {
    this.sqsClient = sqsClient;
  }

  @PostConstruct
  public void pollMessages() {
    Executors.newSingleThreadExecutor().submit(() -> {
      String queueUrl = sqsClient.getQueueUrl(b -> b.queueName(queueName)).queueUrl();
      while (true) {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .maxNumberOfMessages(5)
            .waitTimeSeconds(10)
            .build();
        for (Message msg : sqsClient.receiveMessage(request).messages()) {
          System.out.println("Received: " + msg.body());
          sqsClient.deleteMessage(b -> b.queueUrl(queueUrl).receiptHandle(msg.receiptHandle()));
        }
        Thread.sleep(2000);
      }
    });
  }
}
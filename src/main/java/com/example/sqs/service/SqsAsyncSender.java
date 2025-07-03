package com.example.sqs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class SqsAsyncSender {

  private SqsAsyncClient sqsAsyncClient;

  @Value("${aws.region}")
  private String awsRegion;

  @Value("${aws.sqs.standard-queue-url}")
  private String standardQueueUrl;

  @PostConstruct
  public void init() {
    sqsAsyncClient = SqsAsyncClient.builder()
        .region(Region.of(awsRegion))
        .credentialsProvider(DefaultCredentialsProvider.create())
        // Optional: for local testing (ElasticMQ etc.)
        // .endpointOverride(URI.create("http://localhost:9324"))
        .build();
  }

  public void send(String message) {
    SendMessageRequest request = SendMessageRequest.builder()
        .queueUrl(standardQueueUrl)
        .messageBody(message)
        .build();

    CompletableFuture<Void> future = sqsAsyncClient.sendMessage(request)
        .thenAccept(response ->
            System.out.println("Message sent, ID: " + response.messageId()))
        .exceptionally(ex -> {
          System.err.println("Failed to send message: " + ex.getMessage());
          return null;
        });
  }

  @PreDestroy
  public void shutdown() {
    if (sqsAsyncClient != null) {
      sqsAsyncClient.close();
    }
  }
}

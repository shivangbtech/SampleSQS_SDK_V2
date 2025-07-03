package com.example.sqs;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleSQS_SDK_V2Application {
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(SampleSQS_SDK_V2Application.class);
    springApplication.setBannerMode(Banner.Mode.OFF);
    springApplication.run(args);
  }
}

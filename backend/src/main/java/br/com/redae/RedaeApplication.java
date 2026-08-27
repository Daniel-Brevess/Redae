package br.com.redae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedaeApplication {
  public static void main(String[] args) {
    SpringApplication.run(RedaeApplication.class, args);
  }
}

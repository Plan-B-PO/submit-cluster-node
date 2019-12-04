package pl.ee.verificator.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pl.ee.verificator")
public class VerificatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(VerificatorApplication .class, args);
  }
}

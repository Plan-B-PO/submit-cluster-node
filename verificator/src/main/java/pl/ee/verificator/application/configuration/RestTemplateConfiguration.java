package pl.ee.verificator.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

  @Bean(name = "machineRestTemplate")
  public RestTemplate restTemplate(RestTemplateBuilder templateBuilder, @Value("${app.machine-manager-url}") String mmUrl) {
    return templateBuilder.rootUri(mmUrl).build();
  }

}

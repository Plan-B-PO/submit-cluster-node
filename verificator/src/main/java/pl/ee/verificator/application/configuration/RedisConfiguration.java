package pl.ee.verificator.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(basePackages = {"pl.ee.verificator.domain"})
@Configuration
public class RedisConfiguration {

  @Value("${spring.redis.url}")
  private String url;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new JedisConnectionFactory(new RedisStandaloneConfiguration(url));
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {

    var template = new RedisTemplate<byte[], byte[]>();
    template.setConnectionFactory(redisConnectionFactory());
    return template;
  }
}

package pl.ee.verificator.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(basePackages = {"pl.ee.verificator.domain"})
@Configuration
public class RedisConfiguration {
  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {

    var template = new RedisTemplate<byte[], byte[]>();
    template.setConnectionFactory(redisConnectionFactory());
    return template;
  }
}

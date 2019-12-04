package pl.ee.verificator.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@RedisHash("verfication-result")
@Data
public class VerificationResultEntity {
  @Id
  private String token;

  private Long cpu;
  private Double gpu;
  private Double memory;
  private Long localStorage;




}

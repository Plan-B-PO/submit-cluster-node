package pl.ee.verificator.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RequiredArgsConstructor
@Service
public class SaveVerificationResultCommand {


  private final VerificationResultRepository verificationResultRepository;



  public void logic(@Valid final SaveVerificationResultRequest request) {
    var entity = VerificationResultEntity.builder()
      .cpu(request.getCpu())
      .localStorage(request.getLocalStorage())
      .memory(request.getMemory())
      .gpu(request.getGpu())
      .token(request.getToken())
      .build();

    verificationResultRepository.save(entity);
  }

  @Data
  public static class SaveVerificationResultRequest {

    @NotNull
    @NotBlank
    private String token;
    @NotNull
    private Long cpu;
    @NotNull
    private Double gpu;
    @NotNull
    private Double memory;
    @NotNull
    @JsonAlias("local_storage")
    private Long localStorage;

  }
}


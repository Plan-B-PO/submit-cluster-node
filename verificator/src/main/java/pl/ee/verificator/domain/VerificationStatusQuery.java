package pl.ee.verificator.domain;

import io.vavr.control.Try;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class VerificationStatusQuery {

  @Qualifier("machineRestTemplate")
  private final RestTemplate restTemplate;
  private final VerificationResultRepository verificationResultRepository;

  public VerificationStatusResponse logic(String uuid) {
    var verificationResultOpt = verificationResultRepository.findById(uuid);

    if(verificationResultOpt.isEmpty()) {
      return new VerificationStatusResponse(VerificationStatusResponse.VerificationStatus.PRE_VERIFICATION);
    }
    var response = restTemplate.getForEntity("/machine-manager/management/machines/{uuid}",MachineResponse.class, Map.of("uuid", uuid));


    var registeredCpu = Try.of(() -> response.getBody().getCpus().getMax()).getOrElse(-1L);
    var registeredGpu = Try.of(() -> response.getBody().getGpus().getMax()).getOrElse(-1L);
    var registeredLocalStorage = Try.of(() -> response.getBody().getLocalStorage().getMax()).getOrElse(-1L);
    var registeredMemory = Try.of(() -> response.getBody().getMemory().getMax()).getOrElse(-1L);

    var verificationResult = verificationResultOpt.get();
    var verifiedCpu = verificationResult.getCpu();
    var verifiedGpu = Long.valueOf(verificationResult.getGpu().longValue());
    var verifiedLocalStorage = verificationResult.getLocalStorage();
    var verifiedMemory = Long.valueOf(verificationResult.getMemory().longValue());

    if(registeredCpu.equals(verifiedCpu) && registeredGpu.equals(verifiedGpu) && registeredLocalStorage.equals(verifiedLocalStorage) && registeredMemory.equals(verifiedMemory)) {
      return new VerificationStatusResponse(VerificationStatusResponse.VerificationStatus.VERIFICATION_SUCCESS);
    }

    return new VerificationStatusResponse(VerificationStatusResponse.VerificationStatus.VERIFICATION_FAILURE);
  }

  @Data
  public static class MachineResponse {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Long supplierId;
    @NotNull
    private String status;

    @NotNull
    private Value cpus;
    @NotNull
    private Value gpus;
    @NotNull
    private Value memory;
    @NotNull
    private Value localStorage;

      @Data
      public static class Value {
        private Long current;
        private Long max;
      }

  }

  @Value
  public static class VerificationStatusResponse {

    VerificationStatus status;


    public enum VerificationStatus {
      PRE_VERIFICATION, VERIFICATION_SUCCESS, VERIFICATION_FAILURE
    }
  }

}

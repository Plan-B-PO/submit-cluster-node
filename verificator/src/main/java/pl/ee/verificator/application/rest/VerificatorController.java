package pl.ee.verificator.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ee.verificator.domain.SaveVerificationResultCommand;
import pl.ee.verificator.domain.VerificationStatusQuery;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/verificator")
public class VerificatorController {

  private final SaveVerificationResultCommand saveVerificationResultCommand;
  private final VerificationStatusQuery verificationStatusQuery;

  @PutMapping
  public ResponseEntity<?> saveVerificationResult(@RequestBody SaveVerificationResultCommand.SaveVerificationResultRequest request) {
    saveVerificationResultCommand.logic(request);

    return ResponseEntity.created(URI.create("/verificator/" + request.getToken())).build();
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<VerificationStatusQuery.VerificationStatusResponse> getMachineStatus(@PathVariable("uuid") String uuid) {
    return ResponseEntity.ok(verificationStatusQuery.logic(uuid));
  }

}

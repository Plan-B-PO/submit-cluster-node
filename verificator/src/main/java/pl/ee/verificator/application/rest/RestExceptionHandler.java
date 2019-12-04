package pl.ee.verificator.application.rest;

import lombok.Builder;
import lombok.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.ee.verificator.infrastructure.GenericException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(GenericException.class)
  protected ResponseEntity<ApiError> handleGenericException(GenericException ex) {
    var response = ApiError.builder()
      .error(ex.getCause())
      .errorMessage(ex.getMessage())
      .build();
    return ResponseEntity.status(ex.getStatus()).body(response);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
    var message = ex.getConstraintViolations().stream().map((ConstraintViolation::getMessage)).collect(Collectors.joining(", "));

    var response = ApiError.builder()
      .error(ex.getCause())
      .errorMessage(message)
      .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }


  @Builder
  @Value
  static class ApiError {

    String errorMessage;
    Throwable error;

  }
}

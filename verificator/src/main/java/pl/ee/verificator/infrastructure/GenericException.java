package pl.ee.verificator.infrastructure;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends Exception {

  final Throwable cause;
  final HttpStatus status;
  final String message;


  GenericException(Throwable cause, HttpStatus httpStatus, String message) {
    super(message, cause);
    this.cause = cause;
    this.status = httpStatus;
    this.message = message;
  }

}

package br.com.lucassmelo.keylocker.exception;

import org.springframework.http.HttpStatus;

public class KeyLimitException extends RuntimeException {

  public KeyLimitException(String message) {
    super(String.format(message));
  }

  public HttpStatus getStatus() {
    return HttpStatus.UNPROCESSABLE_ENTITY;
  }


}

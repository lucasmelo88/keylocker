package br.com.lucassmelo.keylocker.exception;

import org.springframework.http.HttpStatus;

public class AccountKeyLimitException extends RuntimeException {

  public AccountKeyLimitException(String message) {
    super(String.format(message));
  }

  public HttpStatus getStatus() {
    return HttpStatus.UNPROCESSABLE_ENTITY;
  }

}

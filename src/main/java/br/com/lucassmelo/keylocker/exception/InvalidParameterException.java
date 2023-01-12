package br.com.lucassmelo.keylocker.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends RuntimeException {

  public InvalidParameterException(String message) {
    super(String.format("O valor informado no parâmetro %s é inválido", message));
  }

  public HttpStatus getStatus() {
    return HttpStatus.UNPROCESSABLE_ENTITY;
  }

}

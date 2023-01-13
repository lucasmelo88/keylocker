package br.com.lucassmelo.keylocker.exception;

public class InvalidParameterException extends RuntimeException {

  public InvalidParameterException(String message) {
    super(String.format("O valor informado no parâmetro %s é inválido", message));
  }
}

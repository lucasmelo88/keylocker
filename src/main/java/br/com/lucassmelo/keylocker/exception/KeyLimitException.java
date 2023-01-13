package br.com.lucassmelo.keylocker.exception;

public class KeyLimitException extends RuntimeException {
  public KeyLimitException(String message) {
    super(message);
  }
}

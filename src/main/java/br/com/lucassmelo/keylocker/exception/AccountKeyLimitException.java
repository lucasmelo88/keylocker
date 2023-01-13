package br.com.lucassmelo.keylocker.exception;

public class AccountKeyLimitException extends RuntimeException {

  public AccountKeyLimitException(String message) {
    super(String.format(message));
  }

}

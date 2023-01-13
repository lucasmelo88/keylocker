package br.com.lucassmelo.keylocker.exception;

public class KeyNotFoundException extends RuntimeException {

  public KeyNotFoundException(String message) {
    super(String.format("Uma chave com o tipo %s não foi encontrada", message));
  }
}

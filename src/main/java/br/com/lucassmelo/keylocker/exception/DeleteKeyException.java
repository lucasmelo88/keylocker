package br.com.lucassmelo.keylocker.exception;

public class DeleteKeyException extends RuntimeException {

  public DeleteKeyException(String message) {
    super(String.format("A chave com id %s já foi removida", message));
  }
}

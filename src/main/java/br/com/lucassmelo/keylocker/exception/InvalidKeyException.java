package br.com.lucassmelo.keylocker.exception;

public class InvalidKeyException extends RuntimeException {

  public InvalidKeyException(String message) {
    super(String.format("Valor informado para a chave do tipo %s não é válido", message));
  }
}

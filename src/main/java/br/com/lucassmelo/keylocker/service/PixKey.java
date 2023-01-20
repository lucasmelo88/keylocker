package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.enums.KeyType;

public interface PixKey {

  boolean isValid();
  KeyType keyType();

}

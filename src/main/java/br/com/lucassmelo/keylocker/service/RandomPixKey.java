package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.enums.KeyType;

public class RandomPixKey implements PixKey {

  private static final int KEY_SIZE = 36;

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public KeyType keyType() {
    return KeyType.ALEATORIA;
  }

  public String generateKey() {

    String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "0123456789"
        + "abcdefghijklmnopqrstuvxyz";

    StringBuilder sb = new StringBuilder(KEY_SIZE);

    for (int i = 0; i < KEY_SIZE; i++) {
      int index
          = (int) (AlphaNumericString.length()
          * Math.random());
      sb.append(AlphaNumericString
          .charAt(index));
    }
    return sb.toString();
  }
}

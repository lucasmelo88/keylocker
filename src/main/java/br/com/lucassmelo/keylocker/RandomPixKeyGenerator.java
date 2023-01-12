package br.com.lucassmelo.keylocker;

public class RandomPixKeyGenerator {

  private static final int KEY_SIZE = 36;

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

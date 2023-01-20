package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.enums.KeyType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellphonePixKey implements PixKey {

  private final String keyValue;

  public CellphonePixKey(String keyValue) {
    this.keyValue = keyValue;
  }

  private final Pattern cellphonePattern = Pattern.compile("(\\+[0-9]{2})([0-9]{2})([0-9]{9})");

  @Override
  public boolean isValid() {
    Matcher matcher = cellphonePattern.matcher(keyValue);
    return matcher.find();
  }

  @Override
  public KeyType keyType() {
    return KeyType.CELULAR;
  }
}

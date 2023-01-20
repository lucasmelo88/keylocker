package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.enums.KeyType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaturalPersonPixKey implements PixKey{

  private final String keyValue;

  public NaturalPersonPixKey(String keyValue) {
    this.keyValue = keyValue;
  }

  private final Pattern naturalPersonCodePattern = Pattern.compile(
      "^\\d{3}\\d{3}\\d{3}\\d{2}$");

  @Override
  public boolean isValid() {
    Matcher matcher = naturalPersonCodePattern.matcher(keyValue);
    return matcher.find();
  }

  @Override
  public KeyType keyType() {
    return KeyType.CPF;
  }
}

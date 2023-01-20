package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.enums.KeyType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LegalEntityPixKey implements PixKey {

  private final String keyValue;

  public LegalEntityPixKey(String keyValue) {
    this.keyValue = keyValue;
  }

  private final Pattern legalEntityCodePattern = Pattern.compile(
      "^\\d{2}\\d{3}\\d{3}\\d{4}\\d{2}$");

  @Override
  public boolean isValid() {
    Matcher matcher = legalEntityCodePattern.matcher(keyValue);
    return matcher.find();
  }

  @Override
  public KeyType keyType() {
    return KeyType.CNPJ;
  }
}

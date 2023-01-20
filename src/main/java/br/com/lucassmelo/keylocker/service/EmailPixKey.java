package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.enums.KeyType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPixKey implements PixKey {

  private final String keyValue;

  public EmailPixKey(String keyValue) {
    this.keyValue = keyValue;
  }

  private final Pattern emailPattern = Pattern.compile("[^\\s]*@[a-z0-9.-]{0,77}$");

  @Override
  public boolean isValid() {
    Matcher matcher = emailPattern.matcher(keyValue);
    return matcher.find();
  }

  @Override
  public KeyType keyType() {
    return KeyType.EMAIL;
  }
}

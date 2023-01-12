package br.com.lucassmelo.keylocker.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaturalPersonPixKey {

  private final String keyValue;

  public NaturalPersonPixKey(String keyValue) {
    this.keyValue = keyValue;
  }

  private final Pattern naturalPersonCodePattern = Pattern.compile(
      "^\\d{3}\\d{3}\\d{3}\\d{2}$");

  public boolean isValid() {
    Matcher matcher = naturalPersonCodePattern.matcher(keyValue);
    return matcher.find();
  }
}

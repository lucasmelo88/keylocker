package br.com.lucassmelo.keylocker.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPixKey {

  private final String keyValue;

  public EmailPixKey(String keyValue) {
    this.keyValue = keyValue;
  }

  private final Pattern emailPattern = Pattern.compile("^(.+)@(\\S+)$");

  public boolean isValid() {
    Matcher matcher = emailPattern.matcher(keyValue);
    return matcher.find();
  }
}

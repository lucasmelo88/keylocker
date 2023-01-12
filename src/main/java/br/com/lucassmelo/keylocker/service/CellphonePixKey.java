package br.com.lucassmelo.keylocker.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CellphonePixKey {

  private final String keyValue;

  public CellphonePixKey(String keyValue) {
    this.keyValue = keyValue;
  }

  private final Pattern cellphonePattern = Pattern.compile("(\\+[0-9]{2})([0-9]{2})([0-9]{9})");

  public boolean isValid() {
    Matcher matcher = cellphonePattern.matcher(keyValue);
    return matcher.find();
  }
}

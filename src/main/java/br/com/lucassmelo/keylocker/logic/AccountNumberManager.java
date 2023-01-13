package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.exception.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountNumberManager {

  private final String accountNumber;

  public AccountNumberManager(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  private final Pattern accountNumberPattern = Pattern.compile(
      "^[0-9]{0,8}$");

  public boolean isValid() {
    Matcher matcher = accountNumberPattern.matcher(accountNumber);
    return matcher.find();
  }

  public boolean validateAccountNumber() {
    if (!isValid()) {
      throw new InvalidParameterException("conta");
    }
    return true;
  }

}

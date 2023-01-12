package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.exception.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgencyNumberManager {

  private final String agencyNumber;

  public AgencyNumberManager(String agencyNumber) {
    this.agencyNumber = agencyNumber;
  }

  private final Pattern agencyNumberPattern = Pattern.compile(
      "[0-9]{0,4}");

  public boolean isValid() {
    Matcher matcher = agencyNumberPattern.matcher(agencyNumber);
    return matcher.find();
  }

  public boolean validateAgencyNumber() {
    if (!isValid()) {
      throw new InvalidParameterException("agencia");
    }
    return true;
  }

}

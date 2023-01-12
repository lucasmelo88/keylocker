package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.service.CellphonePixKey;
import br.com.lucassmelo.keylocker.service.EmailPixKey;
import br.com.lucassmelo.keylocker.service.LegalEntityPixKey;
import br.com.lucassmelo.keylocker.service.NaturalPersonPixKey;

public class KeyTypeValidations {

  private final String keyValue;
  private final String keyType;

  public KeyTypeValidations(String keyType, String keyValue) {
    this.keyType = keyType;
    this.keyValue = keyValue;
  }

  public boolean validate() {
    switch (keyType) {
      case "CELULAR":
        return new CellphonePixKey(keyValue).isValid();
      case "EMAIL":
        return new EmailPixKey(keyValue).isValid();
      case "CPF":
        return new NaturalPersonPixKey(keyValue).isValid();
      case "CNPJ":
        return new LegalEntityPixKey(keyValue).isValid();
      case "ALEATORIA":
        return true;
      default:
        return false;
    }
  }
}

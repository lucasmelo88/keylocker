package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.exception.InvalidKeyException;
import br.com.lucassmelo.keylocker.service.CellphonePixKey;
import br.com.lucassmelo.keylocker.service.EmailPixKey;
import br.com.lucassmelo.keylocker.service.LegalEntityPixKey;
import br.com.lucassmelo.keylocker.service.NaturalPersonPixKey;

public class KeyTypeValidations {

  private final String keyValue;
  private final String keyType;

  private boolean isValid = false;

  public KeyTypeValidations(String keyType, String keyValue) {
    this.keyType = keyType;
    this.keyValue = keyValue;
  }

  public void validate() {
    switch (keyType) {
      case "CELULAR":
        isValid = new CellphonePixKey(keyValue).isValid();
        break;
      case "EMAIL":
        isValid = new EmailPixKey(keyValue).isValid();
        break;
      case "CPF":
        isValid = new NaturalPersonPixKey(keyValue).isValid();
        break;
      case "CNPJ":
        isValid = new LegalEntityPixKey(keyValue).isValid();
        break;
      case "ALEATORIA":
        isValid = true;
        break;
    }
  }

  public boolean validateKeyValue() {
    validate();
    if (!isValid) {
      throw new InvalidKeyException(keyType);
    }
    return true;
  }
}

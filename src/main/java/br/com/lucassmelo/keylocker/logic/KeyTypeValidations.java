package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.exception.InvalidKeyException;
import br.com.lucassmelo.keylocker.service.CellphonePixKey;
import br.com.lucassmelo.keylocker.service.EmailPixKey;
import br.com.lucassmelo.keylocker.service.KeyLockerService;
import br.com.lucassmelo.keylocker.service.LegalEntityPixKey;
import br.com.lucassmelo.keylocker.service.NaturalPersonPixKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyTypeValidations {
  private final String keyValue;
  private final String keyType;
  private boolean isValid = false;
  private static final Logger logger = LoggerFactory
      .getLogger(KeyTypeValidations.class.getName());

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
      logger.error("Chave inválida! O Valor informado não é suportado para nenhum tipo de chave");
      throw new InvalidKeyException(keyType);
    }
    return true;
  }
}

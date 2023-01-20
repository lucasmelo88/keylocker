package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.exception.InvalidKeyException;
import br.com.lucassmelo.keylocker.service.CellphonePixKey;
import br.com.lucassmelo.keylocker.service.EmailPixKey;
import br.com.lucassmelo.keylocker.service.LegalEntityPixKey;
import br.com.lucassmelo.keylocker.service.NaturalPersonPixKey;
import br.com.lucassmelo.keylocker.service.PixKey;
import br.com.lucassmelo.keylocker.service.RandomPixKey;
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

  public PixKey validate() {
    switch (keyType) {
      case "CELULAR":
        return new CellphonePixKey(keyValue);
      case "EMAIL":
        return new EmailPixKey(keyValue);
      case "CPF":
        return new NaturalPersonPixKey(keyValue);
      case "CNPJ":
        return new LegalEntityPixKey(keyValue);
      case "ALEATORIA":
        return new RandomPixKey();
      default:
        return null;
    }
  }

  public boolean validateKeyValue() {
    PixKey pixKey = validate();
    if (pixKey == null || !pixKey.isValid()) {
      logger.error("Chave inválida! O Valor informado não é suportado para nenhum tipo de chave");
      throw new InvalidKeyException(keyType);
    }
    return true;
  }
}

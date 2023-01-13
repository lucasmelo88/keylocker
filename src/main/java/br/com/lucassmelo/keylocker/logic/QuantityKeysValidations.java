package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.exception.KeyLimitException;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.repository.KeysRepository;
import java.util.List;

public class QuantityKeysValidations {

  private KeysRepository keysRepository;

  public QuantityKeysValidations(KeysRepository keysRepository) {
    this.keysRepository = keysRepository;
  }

  public List<KeysInfo> keysInfoListByAccountNumberAndType(final String accountNumber,
      final String keyType) {
    return keysRepository.findByAccountNumberAndKeyType(
        accountNumber, keyType);
  }


  public List<KeysInfo> keysInfoListByValue(final String keyValue) {
    return keysRepository.findByKeyValue(
        keyValue);
  }

  public boolean validateLimitKeyValue(final String keyValue, final String keyType) {
    if (!keysInfoListByValue(keyValue).isEmpty()) {
      throw new KeyLimitException(
          String.format("Já existe uma chave do tipo %s com esse esse valor",
              keyType));
    }
    return true;
  }

  public boolean validateLimitKeyTypeByAccount(final String accountNumber, final String keyType) {
    if (!keysInfoListByAccountNumberAndType(accountNumber, keyType).isEmpty()) {
      throw new KeyLimitException(
          String.format("Já existe uma chave do tipo %s para essa conta corrente",
              keyType));
    }
    return true;
  }
//TODO ENTENDER COMO DIFERENCIAR UMA CONTA DE PJ E PF

//  public boolean isValidCountToLegalEntity() {
//    return countKeysByAccountNumber() <= 20;
//  }
//
//  public boolean isValidCountToNaturalPerson() {
//    return countKeysByAccountNumber() <= 5;
//  }

}

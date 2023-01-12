package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.exception.KeyLimitException;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.repository.KeysRepository;
import java.util.List;

public class QuantityKeysValidations {

  private KeysRepository keysRepository;
  private KeyRequestDto keyRequestDto;

  public QuantityKeysValidations(KeysRepository keysRepository, KeyRequestDto keyRequestDto) {
    this.keysRepository = keysRepository;
    this.keyRequestDto = keyRequestDto;
  }

  public List<KeysInfo> keysInfoListByAccountNumberAndType() {
    return keysRepository.findByAccountNumberAndKeyType(
        keyRequestDto.getAccountNumber(), keyRequestDto.getKeyType().name());
  }


  public List<KeysInfo> keysInfoListByValue() {
    return keysRepository.findByKeyValue(
        keyRequestDto.getValue());
  }

  public boolean validateLimitKeyValue(final KeyRequestDto keyRequestDto) {
    List<KeysInfo> keysInfoListByValue = new QuantityKeysValidations(keysRepository,
        keyRequestDto).keysInfoListByValue();
    if (!keysInfoListByValue.isEmpty()) {
      throw new KeyLimitException(
          String.format("Já existe uma chave do tipo %s com esse esse valor",
              keyRequestDto.getKeyType().name()));
    }
    return true;
  }

  public boolean validateLimitKeyTypeByAccount(final KeyRequestDto keyRequestDto) {
    List<KeysInfo> keysInfoListByValue = new QuantityKeysValidations(keysRepository,
        keyRequestDto).keysInfoListByAccountNumberAndType();
    if (!keysInfoListByValue.isEmpty()) {
      throw new KeyLimitException(
          String.format("Já existe uma chave do tipo %s para essa conta corrente",
              keyRequestDto.getKeyType().name()));
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

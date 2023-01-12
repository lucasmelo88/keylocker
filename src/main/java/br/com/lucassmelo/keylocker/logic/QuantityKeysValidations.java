package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
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

  private int countKeysByAccountNumber() {
    List<KeysInfo> allKeysInfoByAccountNumber = keysRepository.findByAccountNumber(
        keyRequestDto.getAccountNumber());
    return allKeysInfoByAccountNumber.size();
  }

  private int countKeysByValue() {
    List<KeysInfo> allKeysInfoByAccountNumber = keysRepository.findByKeyValue(
        keyRequestDto.getValue());
    return allKeysInfoByAccountNumber.size();
  }

  public boolean isValidCountToLegalEntity() {
    return countKeysByAccountNumber() <= 20;
  }

  public boolean isValidCountToNaturalPerson() {
    return countKeysByAccountNumber() <= 5;
  }

  public boolean isAlreadyExistsKeyWithSameValue() {
    return countKeysByValue() > 1;
  }

}

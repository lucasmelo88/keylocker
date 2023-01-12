package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.RandomPixKeyGenerator;
import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.dto.KeyResponseDto;
import br.com.lucassmelo.keylocker.exception.InvalidKeyException;
import br.com.lucassmelo.keylocker.exception.InvalidParameterException;
import br.com.lucassmelo.keylocker.exception.KeyLimitException;
import br.com.lucassmelo.keylocker.logic.KeyTypeValidations;
import br.com.lucassmelo.keylocker.logic.QuantityKeysValidations;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.repository.KeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyLockerService {

  @Autowired
  private KeysRepository keysRepository;

  public KeyResponseDto processKey(final KeyRequestDto keyRequestDto) {
    validateKeyValue(keyRequestDto);
    validateLimitKeyValue(keyRequestDto);
    validateAgencyNumber(keyRequestDto);
    validateAccountNumber(keyRequestDto);
    return createKey(keyRequestDto);
  }

  public KeyResponseDto createKey(final KeyRequestDto keyRequestDto) {
    KeysInfo keysInfo = buildKeyInfo(keyRequestDto);
    keysInfo = keysRepository.save(keysInfo);
    KeyResponseDto keyResponseDto = new KeyResponseDto();
    keyResponseDto.setId(keysInfo.getId());
    return keyResponseDto;
  }

  private KeysInfo buildKeyInfo(final KeyRequestDto keyRequestDto) {
    KeysInfo keysInfo = new KeysInfo();
    if (isRandomKey(keyRequestDto)) {
      String randomKey = new RandomPixKeyGenerator().generateKey();
      keysInfo.setValue(randomKey);
    } else {
      keysInfo.setValue(keyRequestDto.getValue());
    }
    keysInfo.setKeyType(keyRequestDto.getKeyType());
    keysInfo.setAccountType(keyRequestDto.getAccountType());
    keysInfo.setAgencyNumber(keyRequestDto.getAgencyNumber());
    keysInfo.setAccountNumber(keyRequestDto.getAccountNumber());
    keysInfo.setAccountHolderFirstName(keyRequestDto.getAccountHolderFirstName());
    keysInfo.setAccountHolderLastName(keyRequestDto.getAccountHolderLastName());
    return keysInfo;
  }

  private boolean isRandomKey(final KeyRequestDto keyRequestDto) {
    return keyRequestDto.getKeyType().name().equals("ALEATORIA");
  }

  private boolean validateKeyValue(final KeyRequestDto keyRequestDto) {
    boolean isValidKeyByType = new KeyTypeValidations(keyRequestDto.getKeyType().name(),
        keyRequestDto.getValue()).validate();
    if (!isValidKeyByType) {
      throw new InvalidKeyException(keyRequestDto.getKeyType().name());
    }
    return true;
  }

  private boolean validateAgencyNumber(final KeyRequestDto keyRequestDto) {
    boolean isValidAgencyNumber = keyRequestDto.isValidAgencyNumber();
    if (!isValidAgencyNumber) {
      throw new InvalidParameterException("agencia");
    }
    return true;
  }

  private boolean validateAccountNumber(final KeyRequestDto keyRequestDto) {
    boolean isValidAccountNumber = keyRequestDto.isValidAccountNumber();
    if (!isValidAccountNumber) {
      throw new InvalidParameterException("conta");
    }
    return true;
  }

  private boolean validateLimitKeyValue(final KeyRequestDto keyRequestDto) {
    boolean isValidLimitByKeyValue = new QuantityKeysValidations(keysRepository,
        keyRequestDto).isAlreadyExistsKeyWithSameValue();
    if (!isValidLimitByKeyValue) {
      throw new KeyLimitException(
          String.format("Já existe uma chave do tipo %s com esse esse valor",
              keyRequestDto.getKeyType().name()));
    }
    return true;
  }

}

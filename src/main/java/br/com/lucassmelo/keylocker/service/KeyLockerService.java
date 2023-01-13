package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.RandomPixKeyGenerator;
import br.com.lucassmelo.keylocker.dto.CreateKeyResponseDto;
import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.dto.UpdateKeyRequestDto;
import br.com.lucassmelo.keylocker.dto.UpdateKeyResponseDto;
import br.com.lucassmelo.keylocker.exception.KeyNotFoundException;
import br.com.lucassmelo.keylocker.logic.AccountNumberManager;
import br.com.lucassmelo.keylocker.logic.AgencyNumberManager;
import br.com.lucassmelo.keylocker.logic.KeyTypeValidations;
import br.com.lucassmelo.keylocker.logic.QuantityKeysValidations;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.repository.KeysRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyLockerService {

  @Autowired
  private KeysRepository keysRepository;

  public CreateKeyResponseDto processKey(final KeyRequestDto keyRequestDto) {
    QuantityKeysValidations quantityKeysValidations = new QuantityKeysValidations(keysRepository);
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());
    AccountNumberManager accountNumber = new AccountNumberManager(keyRequestDto.getAccountNumber());
    AgencyNumberManager agencyNumber = new AgencyNumberManager(keyRequestDto.getAgencyNumber());

    executeKeyTypeValidations(keyRequestDto.getValue(),
        keyRequestDto.getKeyType().name());
    executeQuantityValidations(keyRequestDto.getValue(),
        keyRequestDto.getKeyType().name());
    agencyNumber.validateAgencyNumber();
    accountNumber.validateAccountNumber();

    return createKey(keyRequestDto);
  }

  public CreateKeyResponseDto createKey(final KeyRequestDto keyRequestDto) {
    KeysInfo keysInfo = buildKeyInfo(keyRequestDto);
    keysInfo = keysRepository.save(keysInfo);
    CreateKeyResponseDto keyResponseDto = new CreateKeyResponseDto();
    keyResponseDto.setId(keysInfo.getId());
    return keyResponseDto;
  }

  public UpdateKeyResponseDto updateKey(final UpdateKeyRequestDto updateKeyRequestDto) {
    KeysInfo keyToUpdate;
    List<KeysInfo> allKeysFromAgencyAndAccountNUmber = getKeysByAgencyAndAccountNumber(
        updateKeyRequestDto.getAgencyNumber(), updateKeyRequestDto.getAccountNumber());

    Optional<KeysInfo> keysInfoFoundWithSameType = allKeysFromAgencyAndAccountNUmber.stream()
        .filter(keysInfo ->
            keysInfo.getKeyType().equals(updateKeyRequestDto.getKeyType())).findFirst();

    if (keysInfoFoundWithSameType.isPresent()) {
      keyToUpdate = keysInfoFoundWithSameType.get();
    } else {
      throw new KeyNotFoundException(updateKeyRequestDto.getKeyType().name());
    }

    executeKeyTypeValidations(updateKeyRequestDto.getValue(),
        updateKeyRequestDto.getKeyType().name());
    executeQuantityValidations(updateKeyRequestDto.getValue(),
        updateKeyRequestDto.getKeyType().name());

    keyToUpdate.setValue(updateKeyRequestDto.getValue());

    KeysInfo keysInfo = keysRepository.save(keyToUpdate);

    UpdateKeyResponseDto updateKeyResponseDto = new UpdateKeyResponseDto();
    updateKeyResponseDto.setId(keysInfo.getId());
    updateKeyResponseDto.setKeyType(keysInfo.getKeyType());
    updateKeyResponseDto.setValue(keysInfo.getValue());
    updateKeyResponseDto.setAgencyNumber(keysInfo.getAgencyNumber());
    updateKeyResponseDto.setAccountNumber(keysInfo.getAccountNumber());
    updateKeyResponseDto.setAccountType(keysInfo.getAccountType());
    updateKeyResponseDto.setAccountHolderFirstName(keysInfo.getAccountHolderFirstName());
    updateKeyResponseDto.setAccountHolderLastName(keysInfo.getAccountHolderLastName());
    return updateKeyResponseDto;
  }

  public List<KeysInfo> getKeysByAgencyAndAccountNumber(final String agencyNumber,
      final String accountNumber) {
    return keysRepository.findByAgencyAndAccountNumber(agencyNumber, accountNumber);
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

  private void executeKeyTypeValidations(final String keyValue, final String keyType) {
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyType, keyValue);
    keyTypeValidations.validateKeyValue();
  }

  private void executeQuantityValidations(final String keyValue, final String keyType) {
    QuantityKeysValidations quantityKeysValidations = new QuantityKeysValidations(keysRepository);
    quantityKeysValidations.validateLimitKeyValue(keyValue,
        keyType);
    quantityKeysValidations.validateLimitKeyTypeByAccount(keyValue,
        keyType);
  }
}

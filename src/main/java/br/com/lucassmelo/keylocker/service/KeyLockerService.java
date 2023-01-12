package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.RandomPixKeyGenerator;
import br.com.lucassmelo.keylocker.dto.CreateKeyResponseDto;
import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.logic.AccountNumberManager;
import br.com.lucassmelo.keylocker.logic.AgencyNumberManager;
import br.com.lucassmelo.keylocker.logic.KeyTypeValidations;
import br.com.lucassmelo.keylocker.logic.QuantityKeysValidations;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.repository.KeysRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyLockerService {

  @Autowired
  private KeysRepository keysRepository;

  public CreateKeyResponseDto processKey(final KeyRequestDto keyRequestDto) {
    QuantityKeysValidations quantityKeysValidations = new QuantityKeysValidations(keysRepository,
        keyRequestDto);
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());
    AccountNumberManager accountNumber = new AccountNumberManager(keyRequestDto.getAccountNumber());
    AgencyNumberManager agencyNumber = new AgencyNumberManager(keyRequestDto.getAgencyNumber());

    keyTypeValidations.validateKeyValue();
    quantityKeysValidations.validateLimitKeyValue(keyRequestDto);
    quantityKeysValidations.validateLimitKeyTypeByAccount(keyRequestDto);
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
}

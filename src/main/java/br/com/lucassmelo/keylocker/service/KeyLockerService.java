package br.com.lucassmelo.keylocker.service;

import br.com.lucassmelo.keylocker.FormattedDateTime;
import br.com.lucassmelo.keylocker.RandomPixKeyGenerator;
import br.com.lucassmelo.keylocker.dto.CreateKeyResponseDto;
import br.com.lucassmelo.keylocker.dto.DeleteKeyResponseDto;
import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.dto.UpdateKeyRequestDto;
import br.com.lucassmelo.keylocker.dto.UpdateKeyResponseDto;
import br.com.lucassmelo.keylocker.enums.KeyType;
import br.com.lucassmelo.keylocker.exception.DeleteKeyException;
import br.com.lucassmelo.keylocker.exception.InvalidOperationException;
import br.com.lucassmelo.keylocker.exception.KeyNotFoundException;
import br.com.lucassmelo.keylocker.logic.AccountNumberManager;
import br.com.lucassmelo.keylocker.logic.AgencyNumberManager;
import br.com.lucassmelo.keylocker.logic.KeyTypeValidations;
import br.com.lucassmelo.keylocker.logic.QuantityKeysValidations;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.repository.KeysRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeyLockerService {

  private static final Logger logger = LoggerFactory
      .getLogger(KeyLockerService.class.getName());

  @Autowired
  private KeysRepository keysRepository;

  public CreateKeyResponseDto createKey(final KeyRequestDto keyRequestDto) {
    logger.info("Iniciando criação de chave");
    AccountNumberManager accountNumber = new AccountNumberManager(keyRequestDto.getAccountNumber());
    AgencyNumberManager agencyNumber = new AgencyNumberManager(keyRequestDto.getAgencyNumber());
    logger.info("Iniciando validação para criação de chaves");
    executeKeyTypeValidations(keyRequestDto.getValue(),
        keyRequestDto.getKeyType().name());
    executeQuantityValidations(keyRequestDto.getValue(),
        keyRequestDto.getKeyType().name());
    agencyNumber.validateAgencyNumber();
    accountNumber.validateAccountNumber();
    logger.info("Chave válida para cadastro, iniciando processo de persistência");
    KeysInfo keysInfo = buildKeyInfo(keyRequestDto);
    keysInfo = keysRepository.save(keysInfo);
    CreateKeyResponseDto keyResponseDto = new CreateKeyResponseDto();
    keyResponseDto.setId(keysInfo.getId());
    logger.info("Uma chave com o id {} foi criada com sucesso", keysInfo.getId());
    return keyResponseDto;
  }

  public UpdateKeyResponseDto updateKey(final UpdateKeyRequestDto updateKeyRequestDto) {
    KeysInfo keyToUpdate;
    logger.info("Iniciando processo de atualização de chave");
    if (!updateKeyRequestDto.getKeyType().equals(KeyType.ALEATORIA)) {
      List<KeysInfo> allKeysFromAgencyAndAccountNumber = getKeysByAgencyAndAccountNumber(
          updateKeyRequestDto.getAgencyNumber(), updateKeyRequestDto.getAccountNumber());
      Optional<KeysInfo> keysInfoFoundWithSameType = allKeysFromAgencyAndAccountNumber.stream()
          .filter(keysInfo ->
              keysInfo.getKeyType().equals(updateKeyRequestDto.getKeyType())).findFirst();
      if (keysInfoFoundWithSameType.isPresent()) {
        keyToUpdate = keysInfoFoundWithSameType.get();
      } else {
        logger.error("Não foi encontrada nenhuma chave para atualização");
        throw new KeyNotFoundException(String.format("Uma chave com o tipo %s não foi encontrada",
            updateKeyRequestDto.getKeyType().name()));
      }
      logger.info("Iniciando validação para atualização de chaves");
      verifyKeyIsAlreadyDeleted(keyToUpdate);
      executeKeyTypeValidations(updateKeyRequestDto.getValue(),
          updateKeyRequestDto.getKeyType().name());
      executeQuantityValidations(updateKeyRequestDto.getValue(),
          updateKeyRequestDto.getKeyType().name());

      logger.info("Chave válida para atualização, iniciando processo de persistência");
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
      logger.info("Finalizando processo de atualização de chave com sucesso");
      return updateKeyResponseDto;

    } else {
      logger.error("Não foi possível atualizar uma chave do tipo aleatória");
      throw new InvalidOperationException("Não é possível alterar uma chave aleatória");
    }
  }

  public DeleteKeyResponseDto deleteById(final String keyId) {
    KeysInfo keysInfoToDelete = getKeyById(keyId);
    verifyKeyIsAlreadyDeleted(keysInfoToDelete);

    keysInfoToDelete.setDeletedAt(new FormattedDateTime().getFormattedDateTimeNow());

    KeysInfo keysInfo = keysRepository.save(keysInfoToDelete);

    DeleteKeyResponseDto deleteKeyResponseDto = new DeleteKeyResponseDto();
    deleteKeyResponseDto.setId(keysInfo.getId());
    deleteKeyResponseDto.setKeyType(keysInfo.getKeyType());
    deleteKeyResponseDto.setValue(keysInfo.getValue());
    deleteKeyResponseDto.setAgencyNumber(keysInfo.getAgencyNumber());
    deleteKeyResponseDto.setAccountNumber(keysInfo.getAccountNumber());
    deleteKeyResponseDto.setAccountType(keysInfo.getAccountType());
    deleteKeyResponseDto.setAccountHolderFirstName(keysInfo.getAccountHolderFirstName());
    deleteKeyResponseDto.setAccountHolderLastName(keysInfo.getAccountHolderLastName());
    deleteKeyResponseDto.setCreatedAt(keysInfo.getCreatedAt());
    deleteKeyResponseDto.setDeletedAt(keysInfo.getDeletedAt());
    return deleteKeyResponseDto;
  }


  public List<KeysInfo> getKeysByAgencyAndAccountNumber(final String agencyNumber,
      final String accountNumber) {
    return keysRepository.findByAgencyAndAccountNumber(agencyNumber, accountNumber);
  }

  public KeysInfo getKeyById(final String keyId) {
    Optional<KeysInfo> foundKeyInfo = keysRepository.findById(keyId);
    if (foundKeyInfo.isPresent()) {
      return foundKeyInfo.get();
    } else {
      logger.error("Não foi encontrada nenhuma chave");
      throw new KeyNotFoundException(keyId);
    }

  }

  private KeysInfo buildKeyInfo(final KeyRequestDto keyRequestDto) {
    KeysInfo keysInfo = new KeysInfo();
    if (isRandomKey(keyRequestDto)) {
      logger.info("A chave informada é do tipo aleatoria, gerando...");
      String randomKey = new RandomPixKeyGenerator().generateKey();
      logger.info("A chave aleatoria gerada: {}", randomKey);
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
    keysInfo.setCreatedAt(getFormattedDateTimeNow());
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

  private String getFormattedDateTimeNow() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    return LocalDateTime.now().format(dateTimeFormatter);
  }

  private void verifyKeyIsAlreadyDeleted(final KeysInfo keysInfo) {
    if (Objects.nonNull(keysInfo.getDeletedAt())) {
      throw new DeleteKeyException(keysInfo.getId());
    }
  }
}

package br.com.lucassmelo.keylocker.service;

import static org.mockito.Mockito.when;

import br.com.lucassmelo.keylocker.RandomPixKeyGenerator;
import br.com.lucassmelo.keylocker.dto.CreateKeyResponseDto;
import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.dto.UpdateKeyRequestDto;
import br.com.lucassmelo.keylocker.dto.UpdateKeyResponseDto;
import br.com.lucassmelo.keylocker.enums.AccountType;
import br.com.lucassmelo.keylocker.enums.KeyType;
import br.com.lucassmelo.keylocker.exception.InvalidOperationException;
import br.com.lucassmelo.keylocker.exception.KeyNotFoundException;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.repository.KeysRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KeyLockerServiceTest {

  @Mock
  private KeysRepository keysRepository;

  @InjectMocks
  private KeyLockerService keyLockerService = new KeyLockerService();

  @Test
  public void shouldCreateKeyWithSuccess() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CPF);
    keyRequestDto.setValue("11447884552");
    keyRequestDto.setAccountType(AccountType.CORRENTE);
    keyRequestDto.setAgencyNumber("1845");
    keyRequestDto.setAccountNumber("48998745");
    keyRequestDto.setAccountHolderFirstName("Joao");
    keyRequestDto.setAccountHolderLastName("Silva");

    KeysInfo mockKeyInfo = new KeysInfo();
    mockKeyInfo.setId("63c08424b0940a7217195c5e");
    mockKeyInfo.setKeyType(KeyType.CPF);
    mockKeyInfo.setValue("11447884552");
    mockKeyInfo.setAccountType(AccountType.CORRENTE);
    mockKeyInfo.setAgencyNumber("1845");
    mockKeyInfo.setAccountNumber("48998745");
    mockKeyInfo.setAccountHolderFirstName("Joao");
    mockKeyInfo.setAccountHolderLastName("Silva");

    when(keysRepository.save(Mockito.any())).thenReturn(mockKeyInfo);

    CreateKeyResponseDto createKeyResponseDto = keyLockerService.createKey(keyRequestDto);
    Assert.assertEquals("63c08424b0940a7217195c5e", createKeyResponseDto.getId());
  }

  @Test
  public void shouldCreateAnRandomKeyWithSuccess() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.ALEATORIA);
    keyRequestDto.setAccountType(AccountType.CORRENTE);
    keyRequestDto.setAgencyNumber("1845");
    keyRequestDto.setAccountNumber("48998745");
    keyRequestDto.setAccountHolderFirstName("Joao");
    keyRequestDto.setAccountHolderLastName("Silva");

    String randomGeneratedKey = new RandomPixKeyGenerator().generateKey();
    KeysInfo mockKeyInfo = new KeysInfo();
    mockKeyInfo.setId("63c08424b0940a7217195c5e");
    mockKeyInfo.setKeyType(KeyType.ALEATORIA);
    mockKeyInfo.setValue(randomGeneratedKey);
    mockKeyInfo.setAccountType(AccountType.CORRENTE);
    mockKeyInfo.setAgencyNumber("1845");
    mockKeyInfo.setAccountNumber("48998745");
    mockKeyInfo.setAccountHolderFirstName("Joao");
    mockKeyInfo.setAccountHolderLastName("Silva");

    when(keysRepository.save(Mockito.any())).thenReturn(mockKeyInfo);

    CreateKeyResponseDto createKeyResponseDto = keyLockerService.createKey(keyRequestDto);
    Assert.assertEquals("63c08424b0940a7217195c5e", createKeyResponseDto.getId());
  }


  @Test
  public void shouldUpdateKeyWithSuccess() {
    UpdateKeyRequestDto updateKeyRequestDto = new UpdateKeyRequestDto();
    updateKeyRequestDto.setKeyType(KeyType.CPF);
    updateKeyRequestDto.setValue("38445887789");
    updateKeyRequestDto.setAgencyNumber("1845");
    updateKeyRequestDto.setAccountNumber("48998745");

    KeysInfo mockRetrievedKeyInfo = new KeysInfo();
    mockRetrievedKeyInfo.setId("63c08424b0940a7217195c5e");
    mockRetrievedKeyInfo.setKeyType(KeyType.CPF);
    mockRetrievedKeyInfo.setValue("11447884552");
    mockRetrievedKeyInfo.setAccountType(AccountType.CORRENTE);
    mockRetrievedKeyInfo.setAgencyNumber("1845");
    mockRetrievedKeyInfo.setAccountNumber("48998745");
    mockRetrievedKeyInfo.setAccountHolderFirstName("Joao");
    mockRetrievedKeyInfo.setAccountHolderLastName("Silva");

    KeysInfo mockSavedKeyInfo = new KeysInfo();
    mockSavedKeyInfo.setId("63c08424b0940a7217195c5e");
    mockSavedKeyInfo.setKeyType(KeyType.CPF);
    mockSavedKeyInfo.setValue("38445887789");
    mockSavedKeyInfo.setAccountType(AccountType.CORRENTE);
    mockSavedKeyInfo.setAgencyNumber("1845");
    mockSavedKeyInfo.setAccountNumber("48998745");
    mockSavedKeyInfo.setAccountHolderFirstName("Joao");
    mockSavedKeyInfo.setAccountHolderLastName("Silva");

    List<KeysInfo> allKeysByTypeAndAccountNumberMock = Arrays.asList(mockRetrievedKeyInfo);

    when(keysRepository.save(Mockito.any())).thenReturn(mockSavedKeyInfo);

    when(keysRepository.findByAgencyAndAccountNumber(Mockito.anyString(),
        Mockito.anyString())).thenReturn(allKeysByTypeAndAccountNumberMock);

    UpdateKeyResponseDto updateKeyResponseDto = keyLockerService.updateKey(updateKeyRequestDto);
    Assert.assertEquals("38445887789", updateKeyResponseDto.getValue());
  }

  @Test
  public void shouldThrownExceptionWhenTryToUpdateRandomKey() {
    UpdateKeyRequestDto updateKeyRequestDto = new UpdateKeyRequestDto();
    updateKeyRequestDto.setKeyType(KeyType.ALEATORIA);
    updateKeyRequestDto.setValue("1234");
    updateKeyRequestDto.setAgencyNumber("1845");
    updateKeyRequestDto.setAccountNumber("48998745");

    Assert.assertThrows(InvalidOperationException.class,
        () -> keyLockerService.updateKey(updateKeyRequestDto));

  }

  @Test
  public void shouldThrownExceptionWhenTryToUpdateAKeyNotFound() {
    UpdateKeyRequestDto updateKeyRequestDto = new UpdateKeyRequestDto();
    updateKeyRequestDto.setKeyType(KeyType.CPF);
    updateKeyRequestDto.setValue("38445887789");
    updateKeyRequestDto.setAgencyNumber("1845");
    updateKeyRequestDto.setAccountNumber("48998745");

    List<KeysInfo> allKeysByTypeAndAccountNumberMock = new ArrayList<>();

    when(keysRepository.findByAgencyAndAccountNumber(Mockito.anyString(),
        Mockito.anyString())).thenReturn(allKeysByTypeAndAccountNumberMock);

    Assert.assertThrows(KeyNotFoundException.class,
        () -> keyLockerService.updateKey(updateKeyRequestDto));

  }
}

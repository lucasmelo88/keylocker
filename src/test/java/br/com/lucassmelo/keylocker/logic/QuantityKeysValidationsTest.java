package br.com.lucassmelo.keylocker.logic;

import static org.mockito.Mockito.when;

import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.enums.KeyType;
import br.com.lucassmelo.keylocker.exception.KeyLimitException;
import br.com.lucassmelo.keylocker.repository.KeysInfo;
import br.com.lucassmelo.keylocker.repository.KeysRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QuantityKeysValidationsTest {

  @Mock
  private KeysRepository keysRepository;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void shouldReturnTrueWhenQuantityKeysByTypeIsNotReached() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.EMAIL);
    keyRequestDto.setAccountNumber("12345678");
    keyRequestDto.setValue("abc@gmail.com.br");
    when(keysRepository.findByAccountNumberAndKeyType(Mockito.anyString(),
        Mockito.anyString())).thenReturn(new ArrayList<>());
    QuantityKeysValidations quantityKeysValidations = new QuantityKeysValidations(keysRepository);
    Assert.assertTrue(
        quantityKeysValidations.validateLimitKeyTypeByAccount(keyRequestDto.getValue(),
            keyRequestDto.getKeyType().name()));
  }


  @Test
  public void shouldReturnThrownExceptionWhenValueAlreadyExistsToAnotherKey() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CPF);
    keyRequestDto.setValue("45888777450");
    KeysInfo keysInfo1 = new KeysInfo();
    keysInfo1.setValue("45888777450");
    when(keysRepository.findByKeyValue(Mockito.anyString())).thenReturn(Arrays.asList(keysInfo1));
    QuantityKeysValidations quantityKeysValidations = new QuantityKeysValidations(keysRepository);

    exception.expect(KeyLimitException.class);

    quantityKeysValidations.validateLimitKeyValue(keyRequestDto.getValue(),
        keyRequestDto.getKeyType().name());
  }

  @Test
  public void shouldReturnTrueWhenValueIsValid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CPF);
    keyRequestDto.setValue("45888777450");
    when(keysRepository.findByKeyValue(Mockito.anyString())).thenReturn(new ArrayList<>());
    QuantityKeysValidations quantityKeysValidations = new QuantityKeysValidations(keysRepository);
    Assert.assertTrue(quantityKeysValidations.validateLimitKeyValue(keyRequestDto.getValue(),
        keyRequestDto.getKeyType().name()));
  }

  @Test
  public void shouldReturnTrueWhenKeyTypeNotExistsForAnAccountNumber() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CPF);
    keyRequestDto.setAccountNumber("12345678");
    keyRequestDto.setValue("45888777450");
    QuantityKeysValidations quantityKeysValidations = new QuantityKeysValidations(keysRepository);
    Assert.assertTrue(
        quantityKeysValidations.validateLimitKeyTypeByAccount(keyRequestDto.getValue(),
            keyRequestDto.getKeyType().name()));
  }

  @Test
  public void shouldReturnThrownExceptionWhenQuantityKeysByTypeIsReachedToAccountNumber() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CPF);
    keyRequestDto.setAccountNumber("12345678");
    keyRequestDto.setValue("45888777450");

    KeysInfo keysInfo1 = new KeysInfo();
    keyRequestDto.setKeyType(KeyType.CPF);
    keyRequestDto.setAccountNumber("12345678");
    keysInfo1.setValue("38888777550");

    when(keysRepository.findByAccountNumberAndKeyType(Mockito.anyString(),
        Mockito.anyString())).thenReturn(Arrays.asList(keysInfo1));
    QuantityKeysValidations quantityKeysValidations = new QuantityKeysValidations(keysRepository);

    exception.expect(KeyLimitException.class);

    quantityKeysValidations.validateLimitKeyTypeByAccount(keyRequestDto.getValue(),
        keyRequestDto.getKeyType().name());
  }

}

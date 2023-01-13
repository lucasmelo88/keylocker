package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.exception.InvalidParameterException;
import org.junit.Assert;
import org.junit.Test;

public class AccountNumberManagerTest {

  @Test
  public void shouldReturnTrueWhenAccountNumberIsValid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setAccountNumber("12345678");
    AccountNumberManager accountNumberManager = new AccountNumberManager(
        keyRequestDto.getAccountNumber());
    Assert.assertTrue(accountNumberManager.validateAccountNumber());
  }

  @Test
  public void shouldReturnTrueWhenAccountNumberIsValid1() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setAccountNumber("00000001");
    AccountNumberManager accountNumberManager = new AccountNumberManager(
        keyRequestDto.getAccountNumber());
    Assert.assertTrue(accountNumberManager.validateAccountNumber());
  }

  @Test
  public void shouldReturnFalseWhenAccountNumberIsInvalid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setAccountNumber("123456783242134213");
    AccountNumberManager accountNumberManager = new AccountNumberManager(
        keyRequestDto.getAccountNumber());
    Assert.assertThrows(InvalidParameterException.class,
        accountNumberManager::validateAccountNumber);
  }

}

package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.exception.InvalidParameterException;
import org.junit.Assert;
import org.junit.Test;

public class AgencyNumberManagerTest {

  @Test
  public void shouldReturnTrueWhenAccountNumberIsValid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setAgencyNumber("1516");
    AgencyNumberManager accountNumberManager = new AgencyNumberManager(
        keyRequestDto.getAgencyNumber());
    Assert.assertTrue(accountNumberManager.validateAgencyNumber());
  }

  @Test
  public void shouldReturnTrueWhenAccountNumberIsValid1() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setAgencyNumber("0001");
    AgencyNumberManager accountNumberManager = new AgencyNumberManager(
        keyRequestDto.getAgencyNumber());
    Assert.assertTrue(accountNumberManager.validateAgencyNumber());
  }

  @Test
  public void shouldReturnFalseWhenAccountNumberIsInvalid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setAgencyNumber("123456783242134213");
    AgencyNumberManager accountNumberManager = new AgencyNumberManager(
        keyRequestDto.getAgencyNumber());
    Assert.assertThrows(InvalidParameterException.class,
        accountNumberManager::validateAgencyNumber);
  }

}

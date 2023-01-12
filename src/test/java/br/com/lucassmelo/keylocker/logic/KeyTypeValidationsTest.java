package br.com.lucassmelo.keylocker.logic;

import br.com.lucassmelo.keylocker.dto.KeyRequestDto;
import br.com.lucassmelo.keylocker.enums.KeyType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class KeyTypeValidationsTest {

  @Test
  public void shouldReturnTrueWhenCPFKeyIsValid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CPF);
    keyRequestDto.setValue("58841823763");
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());

    Assert.assertTrue(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnFalseWhenCPFKeyIsInvalid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CPF);
    keyRequestDto.setValue("1234");
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());

    Assert.assertFalse(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnTrueWhenCNPJKeyIsValid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CNPJ);
    keyRequestDto.setValue("84172632446805");
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());

    Assert.assertTrue(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnFalseWhenCNPJKeyIsInvalid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CNPJ);
    keyRequestDto.setValue("1234");
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());

    Assert.assertFalse(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnTrueWhenEmailKeyIsValid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.EMAIL);
    keyRequestDto.setValue("abc@abobrinha.com.br");
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());

    Assert.assertTrue(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnFalseWhenEmailKeyIsInvalid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.EMAIL);
    keyRequestDto.setValue("abcabobrinha.com.br");
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());

    Assert.assertFalse(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnTrueWhenCellphoneKeyIsValid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CELULAR);
    keyRequestDto.setValue("+5519998745445");
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());

    Assert.assertTrue(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnFalseWhenCellphoneKeyIsInvalid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.CELULAR);
    keyRequestDto.setValue("5519998745445");
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());

    Assert.assertFalse(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnTrueWhenKeyIsRandom() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    keyRequestDto.setKeyType(KeyType.ALEATORIA);
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        keyRequestDto.getKeyType().name(), keyRequestDto.getValue());
    Assert.assertTrue(keyTypeValidations.validate());
  }

  @Test
  public void shouldReturnFalseWhenKeyTypeIsInvalid() {
    KeyRequestDto keyRequestDto = new KeyRequestDto();
    KeyTypeValidations keyTypeValidations = new KeyTypeValidations(
        "abc", keyRequestDto.getValue());
    Assert.assertFalse(keyTypeValidations.validate());
  }

}

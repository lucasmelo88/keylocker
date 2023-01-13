package br.com.lucassmelo.keylocker.dto;

import br.com.lucassmelo.keylocker.enums.KeyType;

public class UpdateKeyRequestDto {

  private String agencyNumber;
  private String accountNumber;
  private KeyType keyType;
  private String value;

  public UpdateKeyRequestDto() {
  }

  public String getAgencyNumber() {
    return agencyNumber;
  }

  public void setAgencyNumber(String agencyNumber) {
    this.agencyNumber = agencyNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public KeyType getKeyType() {
    return keyType;
  }

  public void setKeyType(KeyType keyType) {
    this.keyType = keyType;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}

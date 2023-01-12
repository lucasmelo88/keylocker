package br.com.lucassmelo.keylocker.dto;

import br.com.lucassmelo.keylocker.enums.AccountType;
import br.com.lucassmelo.keylocker.enums.KeyType;
import org.springframework.lang.NonNull;

public class KeyRequestDto {

  @NonNull
  private KeyType keyType;
  @NonNull
  private String value;
  @NonNull
  private AccountType accountType;
  @NonNull
  private String agencyNumber;
  @NonNull
  private String accountNumber;
  @NonNull
  private String accountHolderFirstName;
  private String accountHolderLastName;

  public KeyRequestDto() {
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

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
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

  public String getAccountHolderFirstName() {
    return accountHolderFirstName;
  }

  public void setAccountHolderFirstName(String accountHolderFirstName) {
    this.accountHolderFirstName = accountHolderFirstName;
  }

  public String getAccountHolderLastName() {
    return accountHolderLastName;
  }

  public void setAccountHolderLastName(String accountHolderLastName) {
    this.accountHolderLastName = accountHolderLastName;
  }
}

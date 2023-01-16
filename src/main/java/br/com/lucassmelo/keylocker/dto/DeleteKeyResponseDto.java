package br.com.lucassmelo.keylocker.dto;

import br.com.lucassmelo.keylocker.enums.AccountType;
import br.com.lucassmelo.keylocker.enums.KeyType;
import java.time.LocalDateTime;
import java.util.Date;

public class DeleteKeyResponseDto {

  private String id;
  private KeyType keyType;
  private String value;
  private AccountType accountType;
  private String agencyNumber;
  private String accountNumber;
  private String accountHolderFirstName;
  private String accountHolderLastName;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  public DeleteKeyResponseDto() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }
}

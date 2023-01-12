package br.com.lucassmelo.keylocker.repository;

import br.com.lucassmelo.keylocker.enums.AccountType;
import br.com.lucassmelo.keylocker.enums.KeyType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "keysInfo")
public class KeysInfo {

  @Id
  private String id;
  private KeyType keyType;
  private String value;
  private AccountType accountType;
  private int agencyNumber;
  private int accountNumber;
  private String accountHolderFirstName;
  private String accountHolderLastName;

  public KeysInfo() {
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

  public int getAgencyNumber() {
    return agencyNumber;
  }

  public void setAgencyNumber(int agencyNumber) {
    this.agencyNumber = agencyNumber;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
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

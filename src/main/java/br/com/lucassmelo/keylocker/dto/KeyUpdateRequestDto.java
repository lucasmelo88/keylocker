package br.com.lucassmelo.keylocker.dto;

import br.com.lucassmelo.keylocker.enums.AccountType;
import org.springframework.lang.NonNull;

public class KeyUpdateRequestDto {

  @NonNull
  private String id;

  private AccountType accountType;
  @NonNull
  private String agencyNumber;
  @NonNull
  private String accountNumber;
  @NonNull
  private String accountHolderFirstName;

  private String accountHolderLastName;

  public KeyUpdateRequestDto() {
  }

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  @NonNull
  public String getAgencyNumber() {
    return agencyNumber;
  }

  public void setAgencyNumber(@NonNull String agencyNumber) {
    this.agencyNumber = agencyNumber;
  }

  @NonNull
  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(@NonNull String accountNumber) {
    this.accountNumber = accountNumber;
  }

  @NonNull
  public String getAccountHolderFirstName() {
    return accountHolderFirstName;
  }

  public void setAccountHolderFirstName(@NonNull String accountHolderFirstName) {
    this.accountHolderFirstName = accountHolderFirstName;
  }

  public String getAccountHolderLastName() {
    return accountHolderLastName;
  }

  public void setAccountHolderLastName(String accountHolderLastName) {
    this.accountHolderLastName = accountHolderLastName;
  }
}

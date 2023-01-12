package br.com.lucassmelo.keylocker.enums;

public enum AccountType {
  CORRENTE("Corrente"),
  POUPANCA("Poupança");

  private String description;

  AccountType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

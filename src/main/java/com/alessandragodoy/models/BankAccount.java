package com.alessandragodoy.models;

public class BankAccount {
	private final String accountNumber;	// Unique, auto-generated
	protected Double balance;
	private final AccountType accountType;	// Checking or Savings

	public BankAccount(String accountNumber, AccountType accountType) {
		this.accountNumber = accountNumber;
		this.balance = 0.0;
		this.accountType = accountType;
	}

	public void deposit(Double amount) {
		this.balance += amount;
		System.out.println("Deposit successful");
	}

	public void withdraw(Double amount) {
		this.balance -= amount;
	}

	public Double getBalance() {
		return this.balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public AccountType getAccountType() {
		return accountType;
	}

}

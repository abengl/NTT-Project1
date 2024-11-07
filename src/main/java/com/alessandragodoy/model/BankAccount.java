package com.alessandragodoy.model;

/**
 * Represents an abstract bank account with core attributes and operations. This serves as a base
 * class for specific account types like checking and savings accounts.
 * <p>
 * Each account has a unique account number, a defined account type (Checking or Savings), and a balance.
 * Subclasses are expected to implement specific behaviors for deposit and withdrawal operations.
 * </p>
 */
public abstract class BankAccount {

	private final String accountNumber;
	private final AccountType accountType;
	protected double balance;

	public BankAccount(String accountNumber, AccountType accountType) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = 0.0;
	}

	public abstract void deposit(double amount);

	public abstract void withdraw(double amount);

	@Override
	public String toString() {
		return "\n---Bank Account Details---\nAccount Number: " + getAccountNumber() + "\nBalance: $" + getBalance() + "\nAccount Type: " + getAccountType();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return this.balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}

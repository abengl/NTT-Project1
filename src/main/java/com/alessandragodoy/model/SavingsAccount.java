package com.alessandragodoy.model;

/**
 * Represents a savings account with core attributes and operations.
 * <p>
 * This class extends {@link BankAccount} to provide specific functionality for savings accounts.
 * </p>
 */
public class SavingsAccount extends BankAccount {

	public SavingsAccount(String accountNumber) {
		super(accountNumber, AccountType.SAVINGS);
	}

	@Override
	public void deposit(double amount) {
		balance += amount;
	}

	@Override
	public void withdraw(double amount) {
		balance -= amount;
	}

}

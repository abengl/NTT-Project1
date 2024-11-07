package com.alessandragodoy.model;

/**
 * Represents a checking account with core attributes and operations.
 * <p>
 * This class extends {@link BankAccount} to provide specific functionality for checking accounts.
 * </p>
 */
public class CheckingAccount extends BankAccount {

	public CheckingAccount(String accountNumber) {
		super(accountNumber, AccountType.CHECKING);
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

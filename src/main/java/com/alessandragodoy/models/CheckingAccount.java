package com.alessandragodoy.models;

/**
 * Represents a checking account with overdraft protection. Allows deposits and withdrawals with an
 * overdraft limit of up to -500.
 * <p>
 * This class extends {@link BankAccount} to provide specific functionality for checking accounts,
 * including a set overdraft limit and validation during withdrawals to prevent overdrafts exceeding
 * the allowed limit.
 * </p>
 */
public class CheckingAccount extends BankAccount {
	private static final double OVERDRAFT_LIMIT = -500;

	public CheckingAccount(String accountNumber) {
		super(accountNumber, AccountType.CHECKING);
	}

	@Override
	public void deposit(Double amount) {
		balance += amount;
		System.out.println("\nDeposit successful. New balance: $" + getBalance());
	}

	@Override
	public void withdraw(double amount) {
		if (balance - amount >= OVERDRAFT_LIMIT) {
			balance -= amount;
			System.out.println("\nWithdraw successful. New balance: $" + getBalance());
		} else {
			System.out.println("\nAccount limit exceeded. Operation cancelled.");
		}
	}
}

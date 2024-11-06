package com.alessandragodoy.models;

/**
 * Represents a savings account, which does not allow overdrafts. Allows deposits and withdrawals,
 * but ensures the balance does not go below zero.
 * <p>
 * This class extends {@link BankAccount} to provide specific functionality for savings accounts,
 * which have no overdraft capability. Withdrawals are permitted only if there are sufficient funds
 * in the account.
 * </p>
 */
public class SavingsAccount extends BankAccount {
	public SavingsAccount(String accountNumber) {
		super(accountNumber, AccountType.SAVINGS);
	}

	@Override
	public void deposit(Double amount) {
		balance += amount;
		System.out.println("\nDeposit successful. New balance: $" + getBalance());
	}

	@Override
	public void withdraw(double amount) {
		if (balance - amount >= 0) {
			balance -= amount;
			System.out.println("\nWithdraw successful. New balance: $" + getBalance());
		} else {
			System.out.println("\nInsufficient funds. Operation cancelled.");
		}
	}
}

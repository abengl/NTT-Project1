package com.alessandragodoy.models;

public class CheckingAccount extends BankAccount {

	public CheckingAccount(String accountNumber, AccountType accountType) {
		super(accountNumber, accountType);
	}

	@Override
	public void withdraw(Double amount) {
		if (this.balance - amount < -500) {
			System.out.println("Exceeds the account limit of overdraft");
		} else {
			this.balance -= amount;
			System.out.println("Withdrawal successful");
		}
	}
}

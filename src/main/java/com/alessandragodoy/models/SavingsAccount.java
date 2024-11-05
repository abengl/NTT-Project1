package com.alessandragodoy.models;

import com.alessandragodoy.models.AccountType;
import com.alessandragodoy.models.BankAccount;

public class SavingsAccount extends BankAccount {

	public SavingsAccount(String accountNumber, AccountType accountType) {
		super(accountNumber, accountType);
	}

	@Override
	public void withdraw(Double amount) {
		if (this.balance < amount) {
			System.out.println("Insufficient funds");
		} else {
			this.balance -= amount;
			System.out.println("Withdrawal successful");
		}
	}


}

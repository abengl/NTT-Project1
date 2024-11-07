package com.alessandragodoy;

import com.alessandragodoy.model.AccountType;
import com.alessandragodoy.model.BankAccount;
import com.alessandragodoy.service.BankService;

/**
 * A test driver for the banking system. This class demonstrates various operations
 * such as client registration, account creation, deposits, withdrawals, and balance checks.
 *
 * <p>This class serves as the main entry point for testing the {@link BankService}
 * functionality. Each operation is performed with sample data to verify the behavior
 * of the banking system, including error handling for overdraft and insufficient funds.</p>
 */
public class SystemDriver {
	public static void main(String[] args) throws Exception {
		BankService bankService = new BankService();

		// Register a new client
		//bankService.registerClient("Eli", "Smith", "15935785", "eli.smith@mail.com");

		// Open savings accounts for the registered client and performing operations
		//BankAccount account1 = bankService.openAccount("15935785", AccountType.SAVINGS);
		String accountNumber1 = "A17309976517";
		//bankService.deposit(accountNumber1, 200); // ok
		//bankService.withdraw(accountNumber1, 200); // ok
		//bankService.withdraw(accountNumber1, 400); // error
		bankService.checkBalance(accountNumber1);

		// Open checking accounts for the registered client and performing operations
		//BankAccount account2 = bankService.openAccount("15935785", AccountType.CHECKING);
		String accountNumber2 = "A17309989520";
		//bankService.withdraw(accountNumber2, 500); // ok
		//bankService.withdraw(accountNumber2, 1000); // error
		//bankService.deposit(accountNumber2, 1000); // ok
		bankService.checkBalance(accountNumber2);
	}
}

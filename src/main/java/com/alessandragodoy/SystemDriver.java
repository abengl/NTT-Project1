package com.alessandragodoy;

import com.alessandragodoy.models.AccountType;
import com.alessandragodoy.models.BankAccount;
import com.alessandragodoy.services.BankService;

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
		bankService.registerClient("Alessandra", "Godoy", "12345678", "agodoy@mail.com");

		// Uncomment to test handling of null values (will throw an exception)
		// bankService.registerClient("John", "Doe", null, null);

		// Open checking and savings accounts for the registered client
		BankAccount account1 = bankService.openAccount("12345678", AccountType.CHECKING);
		BankAccount account2 = bankService.openAccount("12345678", AccountType.SAVINGS);

		// Perform deposit and withdrawal operations
		bankService.deposit(account2.getAccountNumber(), 200); // ok
		bankService.withdraw(account1.getAccountNumber(), 500); // ok
		bankService.withdraw(account2.getAccountNumber(), 200); // ok
		bankService.withdraw(account1.getAccountNumber(), 1000); // error
		bankService.withdraw(account2.getAccountNumber(), 1000); // error

		// Check balances of both accounts
		bankService.checkBalance(account1.getAccountNumber());
		bankService.checkBalance(account2.getAccountNumber());
	}
}

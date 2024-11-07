package com.alessandragodoy.service;

import com.alessandragodoy.model.*;
import com.alessandragodoy.persistence.BankAccountDAO;
import com.alessandragodoy.persistence.ClientDAO;

import java.util.function.Predicate;

/**
 * Provides services for managing clients, opening accounts, and processing transactions.
 * <p>
 * This class supports client registration, account opening (savings and checking accounts),
 * and operations like deposits, withdrawals, and balance checks.
 * </p>
 */
public class BankService {

	private final ClientDAO clientDAO = new ClientDAO();
	private final BankAccountDAO bankAccountDAO = new BankAccountDAO();

	// Validates email format using a regular expression.
	private final Predicate<String> isEmailValid = email -> email.matches("^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+$");

	// Validates DNI format, expecting exactly 8 digits.
	private final Predicate<String> isDniValid = dni -> dni.matches("[0-9]{8}");

	/**
	 * Registers a new client with the specified details.
	 *
	 * @param firstname the client's first name, must not be null
	 * @param lastName  the client's last name, must not be null
	 * @param dni       the client's DNI, must be unique and follow the format
	 * @param email     the client's email, must follow a valid email format
	 * @throws IllegalArgumentException if any field is null, the DNI or email are invalid, or the DNI already exists
	 */
	public void registerClient(String firstname, String lastName, String dni, String email) {
		if (firstname == null || lastName == null || dni == null || email == null) {
			throw new IllegalArgumentException("All fields are required.");
		}
		if (!isDniValid.test(dni)) {
			throw new IllegalArgumentException("Invalid DNI format. Please, verify the DNI format includes 8 digits.");
		}
		if (!isEmailValid.test(email)) {
			throw new IllegalArgumentException(
					"Invalid email format. Please, verify the email format includes valid " + "characters.");
		}
		if (clientDAO.findClientByDni(dni) != null) {
			throw new IllegalArgumentException("DNI already exists. User can't be registered.");
		}

		Client client = new Client(firstname, lastName, dni, email);
		clientDAO.saveClient(client);

		System.out.println("\nClient registered successfully.");
		System.out.println(client);
	}


	/**
	 * Opens a new bank account for a specified client.
	 *
	 * @param dni         the DNI of the client for whom the account is to be opened
	 * @param accountType the type of account to open, either CHECKING or SAVINGS
	 * @return the newly created bank account
	 */
	public BankAccount openAccount(String dni, AccountType accountType) {
		Client client = clientDAO.findClientByDni(dni);
		if (client == null) {
			throw new RuntimeException("Client not found.");
		}

		String accountNumber = generateAccountNumber();

		BankAccount account =
				accountType == AccountType.SAVINGS ? new SavingsAccount(accountNumber) : new CheckingAccount(
						accountNumber);

		bankAccountDAO.saveAccount(account, client.getIdClient());
		client.addAccount(account);

		System.out.println("\nAccount opened successfully. Account number: " + account.getAccountNumber());
		return account;
	}

	/**
	 * Generates a unique account number for new bank accounts.
	 *
	 * @return a unique string representing the account number
	 */
	private String generateAccountNumber() {
		return "A" + (System.currentTimeMillis() / 100);
	}

	/**
	 * Deposits a specified amount into the given account.
	 *
	 * @param accountNumber the account number of the account to deposit into
	 * @param amount        the amount to deposit, must be greater than zero
	 * @throws IllegalArgumentException if the deposit amount is less than or equal to zero
	 */
	public void deposit(String accountNumber, double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Deposit amount must be greater than 0.");
		}
		BankAccount account = bankAccountDAO.findAccount(accountNumber);
		if (account == null) {
			throw new RuntimeException("Account not found.");
		}

		bankAccountDAO.updateBalance(account);
		System.out.print("\nOperation to deposit started... ");
		bankAccountDAO.deposit(accountNumber, amount);
		account.deposit(amount);
		System.out.println("\nDeposit successful. New balance: $" + account.getBalance());
	}


	/**
	 * Withdraws a specified amount from the given account.
	 *
	 * @param accountNumber the account number of the account to withdraw from
	 * @param amount        the amount to withdraw, must be greater than zero
	 * @throws IllegalArgumentException if the withdrawal amount is less than or equal to zero
	 */
	public void withdraw(String accountNumber, double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Withdraw amount must be greater than 0.");
		}
		BankAccount account = bankAccountDAO.findAccount(accountNumber);
		if (account == null) {
			throw new RuntimeException("Account not found.");
		}
		bankAccountDAO.updateBalance(account);
		if (account.getAccountType() == AccountType.SAVINGS && (account.getBalance() - amount >= 0)) {
			System.out.print("\nOperation to withdraw started... ");
			bankAccountDAO.withdraw(accountNumber, amount);
			account.withdraw(amount);
			System.out.println("\nWithdraw successful. New balance: $" + account.getBalance());
		} else if (account.getAccountType() == AccountType.CHECKING && (account.getBalance() - amount >= -500)) {
			System.out.print("\nOperation to withdraw started... ");
			bankAccountDAO.withdraw(accountNumber, amount);
			account.withdraw(amount);
			System.out.println("\nWithdraw successful. New balance: $" + account.getBalance());
		} else {
			throw new RuntimeException("\nAccount limit exceeded. Operation cancelled.");
		}
	}

	/**
	 * Checks and returns the balance of the specified account.
	 *
	 * @param accountNumber the account number of the account to check balance for
	 */
	public double checkBalance(String accountNumber) {
		BankAccount account = bankAccountDAO.findAccount(accountNumber);
		if (account == null) {
			throw new RuntimeException("Account not found.");
		}

		double balance = bankAccountDAO.checkBalance(accountNumber);
		return balance;
	}
}

package com.alessandragodoy.services;

import com.alessandragodoy.models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Provides services for managing clients, opening accounts, and processing transactions.
 * <p>
 * This class supports client registration, account opening (savings and checking accounts),
 * and operations like deposits, withdrawals, and balance checks.
 * </p>
 */
public class BankService {

	/**
	 * Stores clients by their unique DNI for quick access.
	 */
	private final Map<String, Client> clients = new HashMap<>();

	/**
	 * Validates email format using a regular expression.
	 */
	private final Predicate<String> isEmailValid = email -> email.matches("^[A-Za-z0-9_.-]+@[A-Za-z0-9.-]+$");

	/**
	 * Validates DNI format, expecting exactly 8 digits.
	 */
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
		if (clients.containsKey(dni)) {
			throw new IllegalArgumentException("DNI already exists. User can't be registered.");
		}
		int idClient = generateClientId();
		Client client = new Client(idClient, firstname, lastName, dni, email);
		clients.put(dni, client);
		System.out.println("\nClient registered successfully.");
		System.out.println(client);
	}

	/**
	 * Generates a unique ID for each client. This is an auto-incremented value based on
	 * the current size of the clients map.
	 *
	 * @return a unique integer client ID
	 */
	private int generateClientId() {
		return clients.size() + 1;
	}

	/**
	 * Opens a new bank account for a specified client.
	 *
	 * @param dni         the DNI of the client for whom the account is to be opened
	 * @param accountType the type of account to open, either CHECKING or SAVINGS
	 * @return the newly created bank account
	 * @throws Exception if the client is not found
	 */
	public BankAccount openAccount(String dni, AccountType accountType) throws Exception {
		Client client = clients.get(dni);
		if (client == null) {
			throw new Exception("Client not found.");
		}

		String accountNumber = generateAccountNumber();
		BankAccount account;
		if (accountType == AccountType.SAVINGS) {
			account = new SavingsAccount(accountNumber);
		} else {
			account = new CheckingAccount(accountNumber);
		}
		client.addAccount(account);
		System.out.println("\nAccount opened successfully. Account number: " + account.getAccountNumber());
		return account;
	}

	/**
	 * Generates a unique account number for new bank accounts. This number is randomly
	 * generated and should ideally be unique.
	 *
	 * @return a unique string representing the account number
	 */
	private String generateAccountNumber() {
		return String.valueOf((int) ((Math.random() + 1) * 1000000));
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
		BankAccount account = findAccount(accountNumber);
		if (account != null) {
			System.out.print("\nOperation to deposit started... ");
			account.deposit(amount);
		}
	}

	/**
	 * Finds an account by account number, searching through all clients' accounts.
	 *
	 * @param accountNumber the account number to search for
	 * @return the bank account if found, or null if no matching account exists
	 */
	private BankAccount findAccount(String accountNumber) {
		for (Client client : clients.values()) {
			for (BankAccount account : client.getAccounts()) {
				if (account.getAccountNumber().equals(accountNumber)) {
					return account;
				}
			}
		}
		return null;
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
		BankAccount account = findAccount(accountNumber);
		if (account != null) {
			System.out.print("\nOperation to withdraw started... ");
			account.withdraw(amount);
		}
	}

	/**
	 * Checks and displays the balance of the specified account.
	 *
	 * @param accountNumber the account number of the account to check balance for
	 */
	public void checkBalance(String accountNumber) {
		BankAccount account = findAccount(accountNumber);
		if (account != null) {
			System.out.println(account);
		} else {
			System.out.println("Account not found.");
		}
	}
}

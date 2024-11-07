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
		if (firstname.isEmpty() || lastName.isEmpty() || dni.isEmpty() || email.isEmpty()) {
			throw new IllegalArgumentException("Todos los campos son necesarios.");
		}
		if (!isDniValid.test(dni)) {
			throw new IllegalArgumentException("Formato de DNI inválido. Debe contener exactamente 8 dígitos.");
		}
		if (!isEmailValid.test(email)) {
			throw new IllegalArgumentException(
					"Formato de email inválido. Debe contener un solo '@' y al menos un '.' después del '@'.");
		}
		if (clientDAO.findClientByDni(dni) != null) {
			throw new IllegalArgumentException("DNI ya registrado. Intente con otro.");
		}

		Client client = new Client(firstname, lastName, dni, email);
		clientDAO.saveClient(client);

		System.out.println("\nCliente registrado con éxito.");
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
		if (dni.isEmpty() || accountType == null) {
			throw new IllegalArgumentException("Datos incompletos. Por favor, intente de nuevo.");
		}

		Client client = clientDAO.findClientByDni(dni);
		if (client == null) {
			throw new RuntimeException("Cliente no encontrado.");
		}

		String accountNumber = generateAccountNumber();

		BankAccount account =
				accountType == AccountType.SAVINGS ? new SavingsAccount(accountNumber) : new CheckingAccount(
						accountNumber);

		bankAccountDAO.saveAccount(account, client.getIdClient());
		client.addAccount(account);

		System.out.println("\nCuenta creada con éxito. Número de cuenta " + account.getAccountNumber());
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
			throw new IllegalArgumentException("El monton a depositar debe ser mayor a 0.");
		}
		BankAccount account = bankAccountDAO.findAccount(accountNumber);
		if (account == null) {
			throw new RuntimeException("Cuenta no encontrada.");
		}

		bankAccountDAO.updateBalance(account);
		bankAccountDAO.deposit(accountNumber, amount);
		account.deposit(amount);
		System.out.println("\nDepósito exitoso. Nuevo balance: $" + account.getBalance());
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
			throw new IllegalArgumentException("Monto a retirar debe ser mayor a 0.");
		}
		BankAccount account = bankAccountDAO.findAccount(accountNumber);
		if (account == null) {
			throw new RuntimeException("Cuenta no encontrada.");
		}
		bankAccountDAO.updateBalance(account);
		if (account.getAccountType() == AccountType.SAVINGS && (account.getBalance() - amount >= 0)) {
			bankAccountDAO.withdraw(accountNumber, amount);
			account.withdraw(amount);
			System.out.println("\nRetiro exitoso. Nuevo balance: $" + account.getBalance());
		} else if (account.getAccountType() == AccountType.CHECKING && (account.getBalance() - amount >= -500)) {
			bankAccountDAO.withdraw(accountNumber, amount);
			account.withdraw(amount);
			System.out.println("\nRetiro exitoso. Nuevo balance: $" + account.getBalance());
		} else {
			throw new RuntimeException("\nLímte de retiro excedido. Operaion no permitida.");
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
			throw new RuntimeException("Cuenta no encontrada.");
		}

		double balance = bankAccountDAO.checkBalance(accountNumber);
		System.out.println("\nBalance actual de la cuenta " + accountNumber + ": $" + balance);
		return balance;
	}
}

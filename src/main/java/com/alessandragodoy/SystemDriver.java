package com.alessandragodoy;

import com.alessandragodoy.model.AccountType;
import com.alessandragodoy.service.BankService;

import java.util.Scanner;

/**
 * The SystemDriver class is the entry point for the banking system application.
 * It provides a command-line interface for users to interact with the system.
 */
public class SystemDriver {
	private static final BankService bankService = new BankService();
	private static final Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		boolean exit = false;
		while(!exit) {
			System.out.println("\n***Bienvenido al Sistema Bancario***");
			System.out.println("1. Registrar cliente");
			System.out.println("2. Abrir cuenta bancaria");
			System.out.println("3. Depositar");
			System.out.println("4. Retirar");
			System.out.println("5. Consultar balance");
			System.out.println("6. Salir");
			System.out.println("Selecciona una opción:");

			int option = scanner.nextInt();
			scanner.nextLine();

			switch (option) {
				case 1 -> registerClient();
				case 2 -> openBankAccount();
				case 3 -> deposit();
				case 4 -> withdraw();
				case 5 -> checkBalance();
				case 6 -> {
					System.out.println("***Gracias por usar el sistema bancario***");
					exit = true;
				}
				default -> System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	private static void registerClient() {
		System.out.print("Ingrese el nombre: ");
		String firstName = scanner.nextLine();
		System.out.print("Ingrese el apellido: ");
		String lastName = scanner.nextLine();
		System.out.print("Ingrese el DNI (8 dígitos): ");
		String dni = scanner.nextLine();
		System.out.print("Ingrese el email: ");
		String email = scanner.nextLine();

		try {
			bankService.registerClient(firstName, lastName, dni, email);
		} catch (IllegalArgumentException e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}

	private static void openBankAccount() {
		System.out.print("Ingrese el DNI del cliente: ");
		String dni = scanner.nextLine();
		System.out.print("Seleccione el tipo de cuenta (1: Ahorros, 2: Corriente): ");
		int accountTypeChoice = scanner.nextInt();
		scanner.nextLine();

		AccountType accountType = (accountTypeChoice == 1) ? AccountType.SAVINGS : AccountType.CHECKING;

		try {
			bankService.openAccount(dni, accountType);
		} catch (RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void deposit() {
		System.out.print("Ingrese el número de cuenta: ");
		String accountNumber = scanner.nextLine();
		System.out.print("Ingrese el monto a depositar: ");
		double amount = scanner.nextDouble();
		scanner.nextLine();

		try {
			bankService.deposit(accountNumber, amount);
		} catch ( RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void withdraw() {
		System.out.print("Ingrese el número de cuenta: ");
		String accountNumber = scanner.nextLine();
		System.out.print("Ingrese el monto a retirar: ");
		double amount = scanner.nextDouble();
		scanner.nextLine();

		try {
			bankService.withdraw(accountNumber, amount);
		} catch (RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void checkBalance() {
		System.out.print("Ingrese el número de cuenta: ");
		String accountNumber = scanner.nextLine();

		try {
			bankService.checkBalance(accountNumber);
		} catch (RuntimeException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}

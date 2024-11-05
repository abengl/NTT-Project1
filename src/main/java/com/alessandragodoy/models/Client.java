package com.alessandragodoy.models;

import java.util.List;

public class Client {
	private final String name;	// Required
	private final String lastName;	// Required
	private final String dni; 	// Required, unique
	private final String email;	// Required, format validation
	private List<BankAccount> accounts;

	public Client(String name, String lastName, String dni, String email) {
		this.name = name;
		this.lastName = lastName;
		this.dni = dni;
		this.email = email;
	}

	public void addAccount(BankAccount account) {
		accounts.add(account);
		System.out.println("Account added");
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDni() {
		return dni;
	}

	public String getEmail() {
		return email;
	}

	public List<BankAccount> getAccounts() {
		return accounts;
	}

}

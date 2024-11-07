package com.alessandragodoy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client with personal details and associated bank accounts.
 * <p>
 * Each client has a unique ID, name, last name, DNI (unique identifier), and email.
 * Additionally, a client may have multiple bank accounts which can be managed through this class.
 * </p>
 */
public class Client {
	private final String firstname;    // Required
	private final String lastName;    // Required
	private final String dni;    // Required, unique
	private final String email;    // Required, format validation
	private final List<BankAccount> accounts;
	private int idClient;    // Unique, auto-generated

	public Client(String firstname, String lastName, String dni, String email) {
		this.firstname = firstname;
		this.lastName = lastName;
		this.dni = dni;
		this.email = email;
		accounts = new ArrayList<>();
	}

	public Client(int idClient, String firstname, String lastName, String dni, String email) {
		this.idClient = idClient;
		this.firstname = firstname;
		this.lastName = lastName;
		this.dni = dni;
		this.email = email;
		accounts = new ArrayList<>();
	}

	public void addAccount(BankAccount account) {
		accounts.add(account);
	}

	public List<BankAccount> getAccounts() {
		return accounts;
	}

	@Override
	public String toString() {
		return "--- Client Information---\nID: " + getIdClient() + "\nName: " + getFirstname() + "\nLast Name: " + getLastName() + "\nDNI: " + getDni() + "\nEmail: " + getEmail();
	}

	public int getIdClient() {
		return idClient;
	}

	public String getFirstname() {
		return firstname;
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

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
}

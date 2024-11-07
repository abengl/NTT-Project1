package com.alessandragodoy.persistence;

import com.alessandragodoy.model.BankAccount;
import com.alessandragodoy.model.CheckingAccount;
import com.alessandragodoy.model.SavingsAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.alessandragodoy.configuration.DatabaseConnection.getConnection;

/**
 * Data Access Object (DAO) for managing bank account data in the database.
 */
public class BankAccountDAO {

	/**
	 * Saves a new bank account to the database.
	 *
	 * @param account the bank account to be saved
	 * @param clientId the ID of the client who owns the account
	 * @throws RuntimeException if a database access error occurs
	 */
	public void saveAccount(BankAccount account, int clientId) {
		String sql = "INSERT INTO bank_account (account_number, account_type_id, client_id) VALUES (?, ?, ?)";
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, account.getAccountNumber());
			statement.setInt(2, account.getAccountType().ordinal() + 1);
			statement.setInt(3, clientId);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Finds a bank account in the database by its account number.
	 *
	 * @param accountNumber the account number of the bank account to be found
	 * @return the bank account with the specified account number, or null if no such account exists
	 * @throws RuntimeException if a database access error occurs
	 */
	public BankAccount findAccount(String accountNumber) {
		String sql = "SELECT * FROM bank_account WHERE account_number = ?";
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, accountNumber);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int accountTypeId = resultSet.getInt("account_type_id");
				if (accountTypeId == 1) {
					return new SavingsAccount(accountNumber);
				} else {
					return new CheckingAccount(accountNumber);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
		return null;
	}

	/**
	 * Deposits a specified amount into the given bank account.
	 *
	 * @param accountNumber the account number of the account to deposit into
	 * @param amount the amount to deposit, must be greater than zero
	 * @throws RuntimeException if a database access error occurs
	 */
	public void deposit(String accountNumber, double amount) {
		String sql = "UPDATE bank_account SET balance = balance + ? WHERE account_number = ?";
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setDouble(1, amount);
			statement.setString(2, accountNumber);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Withdraws a specified amount from the given bank account.
	 *
	 * @param accountNumber the account number of the account to withdraw from
	 * @param amount the amount to withdraw, must be greater than zero
	 * @throws RuntimeException if a database access error occurs
	 */
	public void withdraw(String accountNumber, double amount) {
		String sql = "UPDATE bank_account SET balance = balance - ? WHERE account_number = ?";
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setDouble(1, amount);
			statement.setString(2, accountNumber);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("Error during withdrawal operation", e);
		}
	}

	/**
	 * Checks and returns the balance of the specified bank account.
	 *
	 * @param accountNumber the account number of the account to check balance for
	 * @return the balance of the specified account
	 * @throws RuntimeException if a database access error occurs
	 */
	public double checkBalance(String accountNumber) {
		String sql = "SELECT balance FROM bank_account WHERE account_number = ?";
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, accountNumber);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				double balance = resultSet.getDouble("balance");
				System.out.println("\nBalance for account " + accountNumber + ": $" + balance);
				return balance;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return 0;
	}

	/**
	 * Updates the balance of the specified bank account object.
	 *
	 * @param account the bank account to update the balance for
	 * @throws RuntimeException if a database access error occurs
	 */
	public void updateBalance(BankAccount account) {
		String sql = "SELECT * FROM bank_account WHERE account_number = ?";
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, account.getAccountNumber());
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				double balance = resultSet.getDouble("balance");
				account.setBalance(balance);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

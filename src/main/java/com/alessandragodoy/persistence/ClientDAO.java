package com.alessandragodoy.persistence;

import com.alessandragodoy.model.Client;

import java.sql.*;

import static com.alessandragodoy.configuration.DatabaseConnection.getConnection;

/**
 * Data Access Object (DAO) for managing client data in the database.
 */
public class ClientDAO {

	/**
	 * Saves a new client to the database.
	 *
	 * @param client the client to be saved
	 * @throws RuntimeException if a database access error occurs
	 */
	public void saveClient(Client client) {
		String sql = "INSERT INTO client (first_name, last_name, dni, email) VALUES (?, ?, ?, ?)";
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, client.getFirstname());
			statement.setString(2, client.getLastName());
			statement.setString(3, client.getDni());
			statement.setString(4, client.getEmail());
			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				int generatedId = generatedKeys.getInt(1);
				client.setIdClient(generatedId);
			} else {
				throw new SQLException("Failed to retrieve client ID.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Finds a client in the database by their DNI.
	 *
	 * @param dni the DNI of the client to be found
	 * @return the client with the specified DNI, or null if no such client exists
	 * @throws RuntimeException if a database access error occurs
	 */
	public Client findClientByDni(String dni) {
		String sql = "SELECT * FROM client WHERE dni = ?";
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, dni);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return new Client(resultSet.getInt("client_id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("dni"), resultSet.getString("email"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}

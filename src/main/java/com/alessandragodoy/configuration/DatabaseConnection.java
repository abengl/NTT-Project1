package com.alessandragodoy.configuration;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class handles the database connection using properties defined in the `db.properties` file.
 * It loads the database URL, user, and password from the properties file and provides a method
 * to get a connection to the database.
 */
public class DatabaseConnection {
	private static final String url;
	private static final String user;
	private static final String password;

	static {
		try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (input == null) {
				throw new RuntimeException("Unable to find db.properties");
			}
			Properties prop = new Properties();
			prop.load(input);
			url = prop.getProperty("DB.URL");
			user = prop.getProperty("DB.USER");
			password = prop.getProperty("DB.PASSWORD");
		} catch (Exception e) {
			throw new RuntimeException("Failed to load database properties", e);
		}
	}

	/**
	 * Gets a connection to the database using the loaded properties.
	 *
	 * @return a {@link Connection} object to the database
	 * @throws SQLException if a database access error occurs
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
}
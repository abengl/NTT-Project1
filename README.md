# Banking System Project

This project is a banking system simulation written in Java ☕. It provides functionalities for client management, account operations (such as deposits and withdrawals), and balance checks, applying Object-Oriented Programming principles and using a local MySQL database for data persistence.

## Features

- **Client Registration**: Register clients with unique identifiers, email, and other validations.
- **Account Management**: Open checking and savings accounts, each with specific rules like overdraft limits for checking accounts.
- **Transaction Operations**: Deposit and withdraw funds, including handling for overdrafts and insufficient balances.
- **Data Persistence**: Stores client and account information in a MySQL database.

## Project Structure

- **Model Classes**: Core classes (`BankAccount`, `Client`, `SavingsAccount`, `CheckingAccount`) represent the main entities.
- **Service Layer**: The `BankService` class encapsulates business logic for client registration, account management, and transaction operations.
- **Driver Class**: `SystemDriver` class contains sample code to demonstrate system functionality and test scenarios.

## Getting Started

### Prerequisites

- **Java**: JDK 17 or later
- **Apache Maven**: Version 3.8 or higher
- **MySQL**: Local instance with appropriate user permissions

## Usage

1. **Register a Client**: Add a new client with required information like name, DNI, and email.
2. **Open an Account**: Create checking or savings accounts for registered clients.
3. **Make Transactions**: Perform deposits and withdrawals, observing the rules for each account type.
4. **Check Balance**: Retrieve the current balance of any account.

## Documentation

### Diagram

> **Model UML Class Diagram**: [UML Class Diagram](https://abgodoyluna.atlassian.net/l/cp/4qYaDY10)

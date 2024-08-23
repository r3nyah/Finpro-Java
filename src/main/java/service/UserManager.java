package service;

import model.User;
import util.DatabaseConnection;
import util.CenterScreen;

import java.sql.*;
import java.util.Scanner;

public class UserManager {
    private HashTable hashTable;

    public UserManager(HashTable hashTable) {
        this.hashTable = hashTable;
    }

    public void saveUserToDatabase(User user) {
        String query = "INSERT INTO users (username, password, salt, email) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getSalt());
            statement.setString(4, ""); // or set email if needed

            statement.executeUpdate();
            CenterScreen.animatedCenterPrint("User saved to database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            CenterScreen.animatedCenterPrint("Failed to save user to database.");
        }
    }

    public void saveUsersToDatabase() {
        for (int i = 0; i < hashTable.getCapacity(); i++) {
            for (User user : hashTable.getChain(i)) {
                saveUserToDatabase(user);
            }
        }
    }

    public boolean login(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter username: ", true);
        String password = CenterScreen.leftInput("Enter password: ", true);

        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("password");
                String storedSalt = resultSet.getString("salt");

                String hashedPassword = User.hashPassword(password, storedSalt);

                if (hashedPassword.equals(storedPasswordHash)) {
                    CenterScreen.animatedCenterPrint("Login successful!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CenterScreen.animatedCenterPrint("Error occurred during login.");
        }
        CenterScreen.animatedCenterPrint("Invalid username or password.");
        return false;
    }

    public void register(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter username: ", true);
        String password = CenterScreen.leftInput("Enter password: ", true);

        String queryCheck = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {

            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                CenterScreen.animatedCenterPrint("Username already exists. Please try another.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CenterScreen.animatedCenterPrint("Error occurred during registration.");
            return;
        }

        User newUser = new User(username, password);
        hashTable.insert(newUser);
        saveUserToDatabase(newUser);
        CenterScreen.animatedCenterPrint("Registration successful!");
    }

    public void updateUser(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter your current username: ", true);
        String password = CenterScreen.leftInput("Enter your current password: ", true);

        String queryCheck = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {

            String hashedPassword = User.hashPassword(password, getUserSalt(username));

            checkStatement.setString(1, username);
            checkStatement.setString(2, hashedPassword);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                String newUsername = CenterScreen.leftInput("Enter new username: ", true);
                String newPassword = CenterScreen.leftInput("Enter new password: ", true);

                String queryUpdate = "UPDATE users SET username = ?, password = ?, salt = ? WHERE username = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {
                    String newSalt = User.generateSalt();
                    String newHashedPassword = User.hashPassword(newPassword, newSalt);

                    updateStatement.setString(1, newUsername);
                    updateStatement.setString(2, newHashedPassword);
                    updateStatement.setString(3, newSalt);
                    updateStatement.setString(4, username);

                    updateStatement.executeUpdate();
                    CenterScreen.animatedCenterPrint("User updated successfully.");
                }
            } else {
                CenterScreen.animatedCenterPrint("Invalid credentials. Update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CenterScreen.animatedCenterPrint("Error occurred during update.");
        }
    }

    public void deleteUser(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter your username: ", true);
        String password = CenterScreen.leftInput("Enter your password: ", true);

        String queryCheck = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {

            String hashedPassword = User.hashPassword(password, getUserSalt(username));

            checkStatement.setString(1, username);
            checkStatement.setString(2, hashedPassword);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                String queryDelete = "DELETE FROM users WHERE username = ?";
                try (PreparedStatement deleteStatement = connection.prepareStatement(queryDelete)) {
                    deleteStatement.setString(1, username);
                    deleteStatement.executeUpdate();
                    CenterScreen.animatedCenterPrint("User deleted successfully.");
                }
            } else {
                CenterScreen.animatedCenterPrint("Invalid credentials. Deletion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CenterScreen.animatedCenterPrint("Error occurred during deletion.");
        }
    }

    private String getUserSalt(String username) throws SQLException {
        String query = "SELECT salt FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("salt");
            }
        }
        return "";
    }

    public void showAllUsers() {
        String query = "SELECT username, password FROM users";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            CenterScreen.clearScreen();
            CenterScreen.centerPrint("All Registered Users:");

            if (!resultSet.isBeforeFirst()) {
                CenterScreen.centerPrint("No users found.");
            } else {
                // Print table header
                String separator = "|----------|------------------------------------------------------------------|";
                String header = "| Username | Password Hash                                                    |";
                CenterScreen.centerPrint(separator);
                CenterScreen.centerPrint(header);
                CenterScreen.centerPrint(separator);

                // Print each user entry
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String passwordHash = resultSet.getString("password");
                    String userInfo = String.format("| %-8s | %-64s |", username, passwordHash);
                    CenterScreen.centerPrint(userInfo);
                }

                CenterScreen.centerPrint(separator);
            }

            // Pause after displaying users
            CenterScreen.leftInput("Press [Enter] to return to the menu...", false);
        } catch (SQLException e) {
            e.printStackTrace();
            CenterScreen.animatedCenterPrint("Error occurred while fetching users.");
        }
    }
}

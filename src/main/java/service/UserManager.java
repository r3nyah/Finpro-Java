package main.java.service;

import main.java.model.User;
import main.java.util.CenterScreen;
import main.java.util.FileManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private HashTable hashTable;
    private FileManager fileManager;

    public UserManager(HashTable hashTable, FileManager fileManager) {
        this.hashTable = hashTable;
        this.fileManager = fileManager;
    }

    public void register(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter username: ");
        String password = CenterScreen.leftInput("Enter password: ");

        if (hashTable.search(username) != null) {
            CenterScreen.centerPrint("Username already exists. Please try another.");
        } else {
            hashTable.insert(new User(username, password));
            CenterScreen.centerPrint("Registration successful!");
        }
        waitForEnter(scanner);
    }

    public boolean login(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter username: ");
        String password = CenterScreen.leftInput("Enter password: ");

        User user = hashTable.search(username);
        if (user != null) {
            String hashedPassword = user.getPasswordHash();
            if (hashedPassword.equals(user.hashPassword(password))) {
                CenterScreen.centerPrint("Login successful!");
                waitForEnter(scanner);
                return true;
            }
        }
        CenterScreen.centerPrint("Invalid username or password.");
        waitForEnter(scanner);
        return false;
    }

    public void updateUser(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter your current username: ");
        String password = CenterScreen.leftInput("Enter your current password: ");

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(user.hashPassword(password))) {
            String newUsername = CenterScreen.leftInput("Enter new username: ");
            String newPassword = CenterScreen.leftInput("Enter new password: ");
            hashTable.update(username, new User(newUsername, newPassword));
            CenterScreen.centerPrint("User updated successfully.");
        } else {
            CenterScreen.centerPrint("Invalid credentials. Update failed.");
        }
        waitForEnter(scanner);
    }

    public void deleteUser(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter your username: ");
        String password = CenterScreen.leftInput("Enter your password: ");

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(user.hashPassword(password))) {
            hashTable.delete(username);
            CenterScreen.centerPrint("User deleted successfully.");
        } else {
            CenterScreen.centerPrint("Invalid credentials. Deletion failed.");
        }
        waitForEnter(scanner);
    }

    public void showAllUsers() {
        CenterScreen.clearScreen();
        List<User> users = getAllUsers();
        if (users.isEmpty()) {
            CenterScreen.centerPrint("No users found.");
        } else {
            for (User user : users) {
                CenterScreen.centerPrint("Username: " + user.getUsername());
                CenterScreen.centerPrint("Password Hash: " + user.getPasswordHash());
                CenterScreen.centerPrint(""); // Adds a newline for better readability
            }
        }
        waitForEnter(new Scanner(System.in)); // Wait for the user to press Enter before returning
    }

    private List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < hashTable.getCapacity(); i++) {
            LinkedList<User> chain = hashTable.getChain(i);
            users.addAll(chain);
        }
        return users;
    }

    private void waitForEnter(Scanner scanner) {
        CenterScreen.leftInput("Press Enter to continue...");
    }

    public HashTable getHashTable() {
        return hashTable;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}

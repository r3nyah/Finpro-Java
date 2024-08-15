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
        String username = CenterScreen.leftInput("Enter username: ", true);
        String password = CenterScreen.leftInput("Enter password: ", true);

        if (hashTable.search(username) != null) {
            CenterScreen.animatedCenterPrint("Username already exists. Please try another.");
        } else {
            hashTable.insert(new User(username, password));
            CenterScreen.animatedCenterPrint("Registration successful!");
        }
        waitForEnter(scanner);
    }

    public boolean login(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter username: ", true);
        String password = CenterScreen.leftInput("Enter password: ", true);

        User user = hashTable.search(username);
        if (user != null) {
            String hashedPassword = user.getPasswordHash();
            if (hashedPassword.equals(user.hashPassword(password))) {
                CenterScreen.animatedCenterPrint("Login successful!");
                waitForEnter(scanner);
                return true;
            }
        }
        CenterScreen.animatedCenterPrint("Invalid username or password.");
        waitForEnter(scanner);
        return false;
    }

    public void updateUser(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter your current username: ", true);
        String password = CenterScreen.leftInput("Enter your current password: ", true);

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(user.hashPassword(password))) {
            String newUsername = CenterScreen.leftInput("Enter new username: ", true);
            String newPassword = CenterScreen.leftInput("Enter new password: ", true);
            hashTable.update(username, new User(newUsername, newPassword));
            CenterScreen.animatedCenterPrint("User updated successfully.");
        } else {
            CenterScreen.animatedCenterPrint("Invalid credentials. Update failed.");
        }
        waitForEnter(scanner);
    }

    public void deleteUser(Scanner scanner) {
        CenterScreen.clearScreen();
        String username = CenterScreen.leftInput("Enter your username: ", true);
        String password = CenterScreen.leftInput("Enter your password: ", true);

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(user.hashPassword(password))) {
            hashTable.delete(username);
            CenterScreen.animatedCenterPrint("User deleted successfully.");
        } else {
            CenterScreen.animatedCenterPrint("Invalid credentials. Deletion failed.");
        }
        waitForEnter(scanner);
    }

    public void showAllUsers() {
        List<User> users = getAllUsers();
        CenterScreen.clearScreen();
        CenterScreen.animatedCenterPrint("All Registered Users:");

        if (users.isEmpty()) {
            CenterScreen.animatedCenterPrint("No users found.");
        } else {
            for (User user : users) {
                CenterScreen.centerPrint(user.getUsername());
            }
        }
        CenterScreen.animatedCenterPrint(""); // Adds a newline
    }

    private List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < hashTable.getCapacity(); i++) {
            LinkedList<User> chain = hashTable.getChain(i);
            users.addAll(chain);
        }
        return users;
    }

    public HashTable getHashTable() {
        return hashTable;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    private void waitForEnter(Scanner scanner) {
        CenterScreen.animatedCenterPrint("Press [Enter] to continue...");
        scanner.nextLine();
    }
}

package main.java.service;

import main.java.model.User;
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
        clearScreen();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (hashTable.search(username) != null) {
            System.out.println("Username already exists. Please try another.");
        } else {
            hashTable.insert(new User(username, password));
            System.out.println("Registration successful!");
        }
        waitForEnter(scanner);
    }

    public boolean login(Scanner scanner) {
        clearScreen();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = hashTable.search(username);
        if (user != null) {
            String hashedPassword = user.getPasswordHash();
            if (hashedPassword.equals(user.hashPassword(password))) {
                System.out.println("Login successful!");
                waitForEnter(scanner);
                return true;
            }
        }
        System.out.println("Invalid username or password.");
        waitForEnter(scanner);
        return false;
    }

    public void updateUser(Scanner scanner) {
        clearScreen();
        System.out.print("Enter your current username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your current password: ");
        String password = scanner.nextLine();

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(user.hashPassword(password))) {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            hashTable.update(username, new User(newUsername, newPassword));
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Invalid credentials. Update failed.");
        }
        waitForEnter(scanner);
    }

    public void deleteUser(Scanner scanner) {
        clearScreen();
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(user.hashPassword(password))) {
            hashTable.delete(username);
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Invalid credentials. Deletion failed.");
        }
        waitForEnter(scanner);
    }

    public void showAllUsers() {
        clearScreen();
        List<User> users = getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("Registered Users:");
            for (User user : users) {
                System.out.println(user.getUsername());
            }
        }
        waitForEnter(new Scanner(System.in));
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < hashTable.getCapacity(); i++) {
            LinkedList<User> chain = hashTable.getChain(i);
            if (chain != null) {
                users.addAll(chain);
            }
        }
        return users;
    }

    public HashTable getHashTable() {
        return hashTable;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing the screen: " + e.getMessage());
        }
    }

    private void waitForEnter(Scanner scanner) {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
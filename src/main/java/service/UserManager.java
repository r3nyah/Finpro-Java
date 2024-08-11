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
    }

    public void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(new User(username, password).getPasswordHash())) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    public void updateUser(Scanner scanner) {
        System.out.print("Enter your current username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your current password: ");
        String password = scanner.nextLine();

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(new User(username, password).getPasswordHash())) {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            hashTable.update(username, new User(newUsername, newPassword));
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Invalid credentials. Update failed.");
        }
    }

    public void deleteUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = hashTable.search(username);
        if (user != null && user.getPasswordHash().equals(new User(username, password).getPasswordHash())) {
            hashTable.delete(username);
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Invalid credentials. Deletion failed.");
        }
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
}

package main.java.service;

import main.java.util.FileManager;
import java.util.Scanner;

public class LoginRegisterSystem {
    private UserManager userManager;

    public LoginRegisterSystem() {
        HashTable hashTable = new HashTable(100);
        FileManager fileManager = new FileManager();
        fileManager.loadUsers(hashTable);
        userManager = new UserManager(hashTable, fileManager);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Login/Register System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    userManager.register(scanner);
                    break;
                case 2:
                    userManager.login(scanner);
                    break;
                case 3:
                    userManager.updateUser(scanner);
                    break;
                case 4:
                    userManager.deleteUser(scanner);
                    break;
                case 5:
                    userManager.getFileManager().saveUsers(userManager.getHashTable());
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }
}

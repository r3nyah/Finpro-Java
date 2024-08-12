package main.java.service;

import java.util.Scanner;
import main.java.util.FileManager;

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
            clearScreen();
            System.out.println("Welcome to the Login/Register System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    userManager.register(scanner);
                    break;
                case 2:
                    if (userManager.login(scanner)) {
                        clearScreen();
                        manageAccounts(scanner); // Display account management options
                    }
                    break;
                case 3:
                    userManager.getFileManager().saveUsers(userManager.getHashTable());
                    clearScreen(); // Clear screen before exiting
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private void manageAccounts(Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("Account Management");
            System.out.println("1. Create Account");
            System.out.println("2. Read Account");
            System.out.println("3. Update Account");
            System.out.println("4. Delete Account");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    userManager.register(scanner);
                    break;
                case 2:
                    userManager.showAllUsers();
                    break;
                case 3:
                    userManager.updateUser(scanner);
                    break;
                case 4:
                    userManager.deleteUser(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
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
}

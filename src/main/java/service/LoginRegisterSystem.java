package main.java.service;

import java.util.Scanner;
import main.java.util.CenterScreen;
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
            CenterScreen.clearScreen();
            CenterScreen.centerPrint("Welcome to the Login/Register System");
            CenterScreen.centerPrint("1. Register");
            CenterScreen.centerPrint("2. Login");
            CenterScreen.centerPrint("3. Exit");
            String choice = CenterScreen.leftInput("Enter your choice: ");

            switch (choice) {
                case "1":
                    userManager.register(scanner);
                    break;
                case "2":
                    if (userManager.login(scanner)) {
                        CenterScreen.clearScreen();
                        manageAccounts(scanner); // Display account management options
                    }
                    break;
                case "3":
                    userManager.getFileManager().saveUsers(userManager.getHashTable());
                    CenterScreen.clearScreen(); // Clear screen before exiting
                    CenterScreen.centerPrint("Goodbye!");
                    scanner.close();
                    return;
                default:
                    CenterScreen.centerPrint("Invalid choice. Please try again.");
            }
            CenterScreen.centerPrint(""); // Adds a newline
        }
    }

    private void manageAccounts(Scanner scanner) {
        while (true) {
            CenterScreen.clearScreen();
            CenterScreen.centerPrint("Account Management");
            CenterScreen.centerPrint("1. Create Account");
            CenterScreen.centerPrint("2. Read Account");
            CenterScreen.centerPrint("3. Update Account");
            CenterScreen.centerPrint("4. Delete Account");
            CenterScreen.centerPrint("5. Logout");
            String choice = CenterScreen.leftInput("Enter your choice: ");

            switch (choice) {
                case "1":
                    userManager.register(scanner);
                    break;
                case "2":
                    userManager.showAllUsers();
                    break;
                case "3":
                    userManager.updateUser(scanner);
                    break;
                case "4":
                    userManager.deleteUser(scanner);
                    break;
                case "5":
                    return;
                default:
                    CenterScreen.centerPrint("Invalid choice. Please try again.");
            }
            CenterScreen.centerPrint(""); // Adds a newline
        }
    }
}

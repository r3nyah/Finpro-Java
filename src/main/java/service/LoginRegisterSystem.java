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
            CenterScreen.animatedCenterPrint("Welcome to the Login/Register System");
            CenterScreen.animatedCenterPrint("1. Register");
            CenterScreen.animatedCenterPrint("2. Login");
            CenterScreen.animatedCenterPrint("3. Exit");
            String choice = CenterScreen.leftInput("Enter your choice: ", true);

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
                    CenterScreen.animatedCenterPrint("Goodbye!");
                    scanner.close();
                    return;
                default:
                    CenterScreen.animatedCenterPrint("Invalid choice. Please try again.");
            }
            CenterScreen.animatedCenterPrint(""); // Adds a newline
        }
    }

    private void manageAccounts(Scanner scanner) {
        while (true) {
            CenterScreen.clearScreen();
            CenterScreen.animatedCenterPrint("Account Management");
            CenterScreen.animatedCenterPrint("1. Create Account");
            CenterScreen.animatedCenterPrint("2. Read Account");
            CenterScreen.animatedCenterPrint("3. Update Account");
            CenterScreen.animatedCenterPrint("4. Delete Account");
            CenterScreen.animatedCenterPrint("5. Logout");
            String choice = CenterScreen.leftInput("Enter your choice: ", true);

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
                    CenterScreen.animatedCenterPrint("Invalid choice. Please try again.");
            }
            CenterScreen.animatedCenterPrint(""); // Adds a newline
        }
    }
}

import service.UserManager;
import service.HashTable;
import util.CenterScreen;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HashTable hashTable = new HashTable();
        UserManager userManager = new UserManager(hashTable);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            CenterScreen.clearScreen();
            CenterScreen.centerPrint("User Management System");
            CenterScreen.centerPrint("1. Register");
            CenterScreen.centerPrint("2. Login");
            CenterScreen.centerPrint("3. Update User");
            CenterScreen.centerPrint("4. Delete User");
            CenterScreen.centerPrint("5. Show All Users");
            CenterScreen.centerPrint("6. Exit");

            String choice = CenterScreen.leftInput("Choose an option: ", false);

            switch (choice) {
                case "1":
                    userManager.register(scanner);
                    break;
                case "2":
                    if (userManager.login(scanner)) {
                        // Redirect to user menu or main application after successful login
                    }
                    break;
                case "3":
                    userManager.updateUser(scanner);
                    break;
                case "4":
                    userManager.deleteUser(scanner);
                    break;
                case "5":
                    userManager.showAllUsers();
                    break;
                case "6":
                    userManager.saveUsersToDatabase();
                    System.out.println("Exiting...");
                    return;
                default:
                    CenterScreen.animatedCenterPrint("Invalid option. Please try again.");
                    break;
            }
        }
    }
}

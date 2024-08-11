import java.util.Scanner;

public class LoginRegisterSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager();
        HashTable hashTable = new HashTable(100);
        fileManager.loadUsers(hashTable);
        UserManager userManager = new UserManager(hashTable, fileManager);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
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
                    fileManager.saveUsers(hashTable);
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

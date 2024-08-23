package util;

import java.util.Scanner;

public class CenterScreen {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String leftInput(String prompt, boolean maskInput) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return maskInput ? maskInput(scanner) : scanner.nextLine();
    }

    private static String maskInput(Scanner scanner) {
        // Implement password masking if needed
        // This is a placeholder; actual masking requires more complex handling
        return scanner.nextLine();
    }

    public static void animatedCenterPrint(String message) {
        System.out.println(message); // Simple implementation
        // For animation, you can add delays or effects here
    }

    public static void centerPrint(String message) {
        int width = 80; // Adjust based on terminal width
        int padding = (width - message.length()) / 2;
        System.out.println(" ".repeat(padding) + message);
    }
}

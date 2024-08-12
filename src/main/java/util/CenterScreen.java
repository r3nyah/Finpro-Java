package main.java.util;

import java.util.Scanner;

public class CenterScreen {

    private static final int DEFAULT_TERMINAL_WIDTH = 80;

    public static void centerPrint(String text) {
        int width = DEFAULT_TERMINAL_WIDTH;
        int padding = (width - text.length()) / 2;
        String format = "%" + padding + "s%s%n";
        System.out.printf(format, "", text);
    }

    public static String centerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        int width = DEFAULT_TERMINAL_WIDTH;
        int padding = (width - prompt.length()) / 2;
        String format = "%" + padding + "s";
        System.out.printf(format, prompt);
        return scanner.nextLine();
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
            centerPrint("Error clearing the screen: " + e.getMessage());
        }
    }
}

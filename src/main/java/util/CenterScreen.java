package main.java.util;

public class CenterScreen {

    private static final int DEFAULT_TERMINAL_WIDTH = 80;

    public static void centerPrint(String text) {
        if (text == null) {
            text = "";
        }

        int width = DEFAULT_TERMINAL_WIDTH;
        int padding = (width - text.length()) / 2;

        if (padding > 0) {
            String paddingString = " ".repeat(padding);
            System.out.println(paddingString + text);
        } else {
            System.out.println(text); // If text is too long, just print it as is
        }
    }

    public static String leftInput(String prompt) {
        System.out.print(prompt);
        return new java.util.Scanner(System.in).nextLine();
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

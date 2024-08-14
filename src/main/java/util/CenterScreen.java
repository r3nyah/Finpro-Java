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

    public static String leftInput(String prompt, boolean validate) {
        String input;
        do {
            System.out.print(prompt);
            input = new java.util.Scanner(System.in).nextLine().trim();

            if (validate && (input.isEmpty() || !isValidInput(input))) {
                centerPrint("Invalid input. Please try again.");
            } else {
                break;
            }
        } while (true);

        return input;
    }

    private static boolean isValidInput(String input) {
        return input.matches("^[a-zA-Z0-9]*$"); // Only alphanumeric characters are valid
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

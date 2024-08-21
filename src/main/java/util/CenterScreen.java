package util;

public class CenterScreen {

    private static final int DEFAULT_TERMINAL_WIDTH = 80;

    public static void animatedCenterPrint(String text) {
        if (text == null) {
            text = "";
        }

        int width = DEFAULT_TERMINAL_WIDTH;
        int padding = (width - text.length()) / 2;

        // RGB color sequence
        String[] colors = {
                "\u001B[31m", // Red
                "\u001B[32m", // Green
                "\u001B[34m", // Blue
                "\u001B[33m", // Yellow
                "\u001B[35m", // Magenta
                "\u001B[36m", // Cyan
        };

        // Loop through the colors
        for (int i = 0; i < 10; i++) { // Repeat the animation 10 times
            for (String color : colors) {
                if (padding > 0) {
                    String paddingString = " ".repeat(padding);
                    System.out.print("\r" + color + paddingString + text);
                } else {
                    System.out.print("\r" + color + text); // If text is too long, just print it as is
                }

                try {
                    Thread.sleep(1); // Adjust delay for effect speed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Reset color
        System.out.println("\u001B[0m");
    }

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

import main.java.service.LoginRegisterSystem;

public class Main {
    public static void main(String[] args) {
        clearScreen();
        showLoadingAnimation(5); // Show loading animation for 5 seconds
        clearScreen();
        LoginRegisterSystem system = new LoginRegisterSystem();
        system.run();
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

    public static void showLoadingAnimation(int seconds) {
        System.out.print("Loading");
        for (int i = 0; i < seconds; i++) {
            try {
                Thread.sleep(1000);
                System.out.print(".");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
}
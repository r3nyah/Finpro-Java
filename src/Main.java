import main.java.service.LoginRegisterSystem;
import main.java.util.CenterScreen;

public class Main {
    public static void main(String[] args) {
        CenterScreen.clearScreen();
        //showLoadingAnimation(5); // Show loading animation for 5 seconds
        CenterScreen.clearScreen();
        LoginRegisterSystem system = new LoginRegisterSystem();
        system.run();
    }

    public static void showLoadingAnimation(int seconds) {
        CenterScreen.centerPrint("Loading");
        for (int i = 0; i < seconds; i++) {
            try {
                Thread.sleep(1000);
                System.out.print("."); // This will be centered when printed in sequence.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

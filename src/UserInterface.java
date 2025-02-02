package src;

import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayGameInfo(int level, int score, int discardsLeft, Hand playerHand) {
        System.out.println("\n--- Level " + level + " ---");
        System.out.println("Score: " + score);
        System.out.println("Discards Left: " + discardsLeft);
        System.out.println("Your Hand: " + playerHand);
    }

    public String getUserAction() {
      System.out.println("\nChoose an action (draw, discard [card indices], play, reset): ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

      public void closeScanner(){
        scanner.close();
      }
}

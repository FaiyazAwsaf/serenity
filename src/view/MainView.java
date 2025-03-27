package view;

import java.util.Scanner;

public class MainView {
    private Scanner scanner;

    public MainView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println("====================================");
        System.out.println("      Welcome to Serenity");
        System.out.println("  Your Personal Health Tracker");
        System.out.println("====================================");
        System.out.println();
    }


    public int displayLoginMenu() {
        System.out.println("\nLogin Menu:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("\nEnter your choice: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1; // Invalid input
        }
    }

}


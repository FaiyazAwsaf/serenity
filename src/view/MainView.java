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

    public int displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. View Profile");
        System.out.println("2. Calculate BMI");
        System.out.println("3. Calculate BMR and Daily Calorie Needs");
        System.out.println("4. Update Weight");
        System.out.println("5. Manage Health Goals");
        System.out.println("6. Manage Dietary Preferences");
        System.out.println("7. View Progress");
        System.out.println("8. Logout");
        System.out.println("9. Exit");
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

    public void displayInvalidChoice() {
        System.out.println("\nInvalid choice. Please try again.");
    }

    public void displayExitMessage() {
        System.out.println("\nThank you for using Serenity. Goodbye!");
    }

    public void displayLoginSuccessMessage(String username) {
        System.out.println("\nWelcome back, " + username + "!");
    }

    public void displayLoginFailMessage() {
        System.out.println("\nInvalid username or password. Please try again.");
    }

    public void displayRegistrationSuccessMessage() {
        System.out.println("\nRegistration successful! You can now login.");
    }

    public String[] getLoginCredentials() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return new String[]{username, password};
    }

    public void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }


}


package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validators {

    private Scanner scanner = new Scanner(System.in);

    // VValidate gender input
    public String getValidGender() {
        String gender = "";
        while (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
            System.out.print("Gender (Male/Female): ");
            gender = scanner.nextLine();
            if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
                System.out.println("Invalid input! Please enter either 'Male' or 'Female'.");
            }
        }
        return gender;
    }

    // Validate height input
    public double getValidHeight() {
        double height = -1;
        while (height <= 0) {
            System.out.print("Height (cm): ");
            try {
                height = scanner.nextDouble();
                if (height <= 0) {
                    System.out.println("Height must be a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number for height.");
                scanner.nextLine(); // Clear the buffer
            }
        }
        scanner.nextLine(); // Consume the remaining newline character
        return height;
    }

    // Validate weight input
    public double getValidWeight() {
        double weight = -1;
        while (weight <= 0) {
            System.out.print("Weight (kg): ");
            try {
                weight = scanner.nextDouble();
                if (weight <= 0) {
                    System.out.println("Weight must be a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number for weight.");
                scanner.nextLine(); // Clear the buffer
            }
        }
        scanner.nextLine(); // Consume the remaining newline character
        return weight;
    }

    // Validate activity level input
    public String getValidActivityLevel() {
        String activityLevel = "";
        while (!isValidActivityLevel(activityLevel)) {
            System.out.print("Activity level (Light, Moderate, Active, Very Active): ");
            activityLevel = scanner.nextLine();
            if (!isValidActivityLevel(activityLevel)) {
                System.out.println("Invalid input! Please enter one of the following: Light / Moderate / Active / Very Active.");
            }
        }
        return activityLevel;
    }

    // Helper method to check if the activity level is valid
    public boolean isValidActivityLevel(String activityLevel) {
        return  activityLevel.equalsIgnoreCase("Light") ||
                activityLevel.equalsIgnoreCase("Moderate") ||
                activityLevel.equalsIgnoreCase("Active") ||
                activityLevel.equalsIgnoreCase("Very Active");
    }

    public int getValidAge() {
        int age = -1;
        while (age <= 0) {
            System.out.print("Age: ");
            try {
                age = scanner.nextInt();
                if (age <= 0) {
                    System.out.println("Age must be a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number for age.");
                scanner.nextLine(); // Clear the buffer
            }
        }
        scanner.nextLine(); // Consume the remaining newline character
        return age;
    }
}

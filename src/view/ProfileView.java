package view;

import model.BMI;
import model.BMR;
import model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProfileView {
    private Scanner scanner;

    public ProfileView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayUserProfile(User user) {
        System.out.println("\n===== User Profile =====");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender());
        System.out.printf("Height: %.1f cm\n", user.getHeight());
        System.out.printf("Weight: %.1f kg\n", user.getWeight());
        System.out.println("Activity Level: " + user.getActivityLevel());
    }

    public void displayBMIResults(BMI bmi) {
        System.out.println("\n===== BMI Results =====");
        System.out.printf("Your BMI: %.1f\n", bmi.getBmiValue());
        System.out.println("Category: " + bmi.getBmiCategory());
        System.out.println("\nHealth Recommendation:");
        System.out.println(bmi.getHealthRecommendation());
    }

    public void displayBMRResults(BMR bmr) {
        System.out.println("\n===== BMR and Calorie Needs =====");
        System.out.println(bmr.getCalorieRecommendation());
    }

    public void displayHealthGoals(List<String> goals) {
        System.out.println("\n===== Health Goals =====");
        if (goals.isEmpty()) {
            System.out.println("You haven't set any health goals yet.");
        } else {
            for (int i = 0; i < goals.size(); i++) {
                System.out.println((i + 1) + ". " + goals.get(i));
            }
        }
    }

    public void displayDietaryPreferences(List<String> preferences) {
        System.out.println("\n===== Dietary Preferences =====");
        if (preferences.isEmpty()) {
            System.out.println("You haven't set any dietary preferences yet.");
        } else {
            for (int i = 0; i < preferences.size(); i++) {
                System.out.println((i + 1) + ". " + preferences.get(i));
            }
        }
    }

    public void displayWeightHistory(Map<LocalDate, Double> weightHistory) {
        System.out.println("\n===== Weight History =====");
        if (weightHistory.isEmpty()) {
            System.out.println("No weight history available.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        weightHistory.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.printf("%s: %.1f kg\n",
                            entry.getKey().format(formatter), entry.getValue());
                });
    }

    public void displayProgressSummary(String summary) {
        System.out.println("\n===== Progress Summary =====");
        System.out.println(summary);
    }

    public String getNewHealthGoal() {
        System.out.print("\nEnter a new health goal: ");
        return scanner.nextLine();
    }

    public String getNewDietaryPreference() {
        System.out.print("\nEnter a new dietary preference: ");
        return scanner.nextLine();
    }

    public int getGoalIndexToRemove(int maxIndex) {
        System.out.print("\nEnter the number of the goal to remove (1-" + maxIndex + "): ");
        try {
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return index;
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1; // Invalid input
        }
    }

    public int getPreferenceIndexToRemove(int maxIndex) {
        System.out.print("\nEnter the number of the preference to remove (1-" + maxIndex + "): ");
        try {
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return index;
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1; // Invalid input
        }
    }

    public double getNewWeight() {
        System.out.print("\nEnter your current weight (kg): ");
        try {
            double weight = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            return weight > 0 ? weight : -1;
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1; // Invalid input
        }
    }
}
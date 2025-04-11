package view;

import model.BMI;
import model.BMR;
import model.Meal;
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
    
    /**
     * Displays meals for a specific date
     * @param meals List of meals to display
     * @param date The date for which meals are being displayed
     */
    public void displayMeals(List<Meal> meals, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("\n===== Meals for " + date.format(formatter) + " =====");
        
        if (meals.isEmpty()) {
            System.out.println("No meals recorded for this date.");
            return;
        }
        
        int totalCalories = 0;
        System.out.println("\nBreakfast:");
        List<Meal> breakfastMeals = meals.stream()
                .filter(meal -> meal.getCategory().equalsIgnoreCase("Breakfast"))
                .toList();
        if (breakfastMeals.isEmpty()) {
            System.out.println("  No breakfast recorded");
        } else {
            for (Meal meal : breakfastMeals) {
                System.out.println("  " + meal);
                totalCalories += meal.getCalories();
            }
        }
        
        System.out.println("\nLunch:");
        List<Meal> lunchMeals = meals.stream()
                .filter(meal -> meal.getCategory().equalsIgnoreCase("Lunch"))
                .toList();
        if (lunchMeals.isEmpty()) {
            System.out.println("  No lunch recorded");
        } else {
            for (Meal meal : lunchMeals) {
                System.out.println("  " + meal);
                totalCalories += meal.getCalories();
            }
        }
        
        System.out.println("\nDinner:");
        List<Meal> dinnerMeals = meals.stream()
                .filter(meal -> meal.getCategory().equalsIgnoreCase("Dinner"))
                .toList();
        if (dinnerMeals.isEmpty()) {
            System.out.println("  No dinner recorded");
        } else {
            for (Meal meal : dinnerMeals) {
                System.out.println("  " + meal);
                totalCalories += meal.getCalories();
            }
        }
        
        System.out.println("\nSnacks:");
        List<Meal> snackMeals = meals.stream()
                .filter(meal -> meal.getCategory().equalsIgnoreCase("Snack"))
                .toList();
        if (snackMeals.isEmpty()) {
            System.out.println("  No snacks recorded");
        } else {
            for (Meal meal : snackMeals) {
                System.out.println("  " + meal);
                totalCalories += meal.getCalories();
            }
        }
        
        System.out.println("\nTotal calories for the day: " + totalCalories);
    }
    
    /**
     * Gets input for a new meal from the user
     * @return A new Meal object with user input data, or null if input was invalid
     */
    public Meal getNewMeal() {
        try {
            System.out.println("\n===== Add New Meal =====");
            
            System.out.print("Enter meal name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter calories: ");
            int calories = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (calories <= 0) {
                System.out.println("Calories must be a positive number.");
                return null;
            }
            
            System.out.println("\nSelect meal category:");
            System.out.println("1. Breakfast");
            System.out.println("2. Lunch");
            System.out.println("3. Dinner");
            System.out.println("4. Snack");
            System.out.print("Enter your choice (1-4): ");
            
            int categoryChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            String category;
            switch (categoryChoice) {
                case 1: category = "Breakfast"; break;
                case 2: category = "Lunch"; break;
                case 3: category = "Dinner"; break;
                case 4: category = "Snack"; break;
                default:
                    System.out.println("Invalid category choice.");
                    return null;
            }
            
            return new Meal(name, calories, category, LocalDate.now());
            
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            System.out.println("Invalid input. Please try again.");
            return null;
        }
    }
    
    /**
     * Gets the index of a meal to remove from a list
     * @param meals The list of meals to choose from
     * @return The index of the meal to remove, or -1 if invalid input
     */
    public int getMealIndexToRemove(List<Meal> meals) {
        if (meals.isEmpty()) {
            System.out.println("No meals to remove.");
            return -1;
        }
        
        System.out.println("\n===== Remove Meal =====");
        for (int i = 0; i < meals.size(); i++) {
            System.out.println((i + 1) + ". " + meals.get(i));
        }
        
        System.out.print("\nEnter the number of the meal to remove (1-" + meals.size() + "): ");
        try {
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return index;
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1; // Invalid input
        }
    }
    
    /**
     * Displays a summary of calories consumed over a date range
     * @param caloriesByDate Map of dates to total calories consumed
     */
    public void displayCalorieSummary(Map<LocalDate, Integer> caloriesByDate) {
        System.out.println("\n===== Calorie Consumption Summary =====");
        
        if (caloriesByDate.isEmpty()) {
            System.out.println("No meal data available.");
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        caloriesByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.printf("%s: %d calories\n",
                            entry.getKey().format(formatter), entry.getValue());
                });
    }
}
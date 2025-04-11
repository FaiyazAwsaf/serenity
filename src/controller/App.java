package controller;

import model.User;
import model.UserProfile;
import view.MainView;
import view.ProfileView;
import utils.FileHandler;
import model.Meal;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class App {

    private UserHandler userHandler;
    private MainView mainView;
    private ProfileView profileView;
    private User currentUser;
    private UserProfile currentUserProfile;
    private Map<String, UserProfile> userProfiles;

    public App() {
        userHandler = new UserHandler();
        mainView = new MainView();
        profileView = new ProfileView();
        userProfiles = new HashMap<>();
        loadUserProfiles();
    }

    private void loadUserProfiles() {
        Map<String, User> users = userHandler.getAllUsers();
        for (User user : users.values()) {
            UserProfile profile = FileHandler.loadUserProfile(user);
            userProfiles.put(user.getUsername(), profile);
        }
    }

    public void runApp() {
        mainView.displayWelcomeMessage();

        boolean running = true;
        while (running) {
            int choice = mainView.displayLoginMenu();

            switch (choice) {
                case 1: // Login
                    handleLogin();
                    break;
                case 2: // Register
                    handleRegistration();
                    break;
                case 3: // Exit
                    FileHandler.saveAllProfiles(userProfiles);
                    running = false;
                    mainView.displayExitMessage();
                    break;
                default:
                    mainView.displayInvalidChoice();
            }
        }
    }

    private void handleLogin() {
        String[] credentials = mainView.getLoginCredentials();
        String username = credentials[0];
        String password = credentials[1];

        if (userHandler.authenticate(username, password)) {
            mainView.displayLoginSuccessMessage(username);
            currentUser = userHandler.getUserByUsername(username);

            if (!userProfiles.containsKey(username)) {
                currentUserProfile = new UserProfile(currentUser);
                userProfiles.put(username, currentUserProfile);
            } else {
                currentUserProfile = userProfiles.get(username);
            }

            runMainMenu();
        } else {
            mainView.displayLoginFailMessage();
        }
    }

    private void handleRegistration() {
        User newUser = userHandler.registerUser();
        if (newUser != null) {
            mainView.displayRegistrationSuccessMessage();
            UserProfile newProfile = new UserProfile(newUser);
            userProfiles.put(newUser.getUsername(), newProfile);
            FileHandler.saveUserProfile(newProfile);
        }
    }

    private void runMainMenu() {
        boolean loggedIn = true;

        while (loggedIn) {
            int choice = mainView.displayMainMenu();

            switch (choice) {
                case 1: // View Profile
                    profileView.displayUserProfile(currentUser);
                    mainView.pressEnterToContinue();
                    break;
                case 2: // Calculate BMI
                    profileView.displayBMIResults(currentUserProfile.getCurrentBMI());
                    mainView.pressEnterToContinue();
                    break;
                case 3: // Calculate BMR and Daily Calorie Needs
                    profileView.displayBMRResults(currentUserProfile.getCurrentBMR());
                    mainView.pressEnterToContinue();
                    break;
                case 4: // Update Weight
                    handleWeightUpdate();
                    break;
                case 5: // Manage Health Goals
                    handleHealthGoals();
                    break;
                case 6: // Manage Dietary Preferences
                    handleDietaryPreferences();
                    break;
                case 7: // View Progress
                    profileView.displayWeightHistory(currentUserProfile.getWeightHistory());
                    profileView.displayProgressSummary(currentUserProfile.getProgressSummary());
                    mainView.pressEnterToContinue();
                    break;
                case 8: // Meal Tracking
                    handleMealTracking();
                    break;
                case 9: // Logout
                    FileHandler.saveUserProfile(currentUserProfile);
                    loggedIn = false;
                    break;
                case 10: // Exit
                    FileHandler.saveAllProfiles(userProfiles);
                    loggedIn = false;
                    System.exit(0);
                    break;
                default:
                    mainView.displayInvalidChoice();
            }
        }
    }

    private void handleWeightUpdate() {
        double newWeight = profileView.getNewWeight();
        if (newWeight > 0) {
            currentUser.setWeight(newWeight);
            currentUserProfile.updateWeight(newWeight, LocalDate.now());
            System.out.println("\nWeight updated successfully!");
            FileHandler.saveUserProfile(currentUserProfile);
        } else {
            System.out.println("\nInvalid weight value. Update canceled.");
        }
        mainView.pressEnterToContinue();
    }

    private void handleHealthGoals() {
        boolean managing = true;
        while (managing) {
            profileView.displayHealthGoals(currentUserProfile.getHealthGoals());
            System.out.println("\n1. Add a new health goal");
            System.out.println("2. Remove a health goal");
            System.out.println("3. Return to main menu");
            System.out.print("\nEnter your choice: ");

            try {
                int choice = Integer.parseInt(mainView.getUserInput());

                switch (choice) {
                    case 1: // Add goal
                        String newGoal = profileView.getNewHealthGoal();
                        currentUserProfile.addHealthGoal(newGoal);
                        System.out.println("\nHealth goal added successfully!");
                        FileHandler.saveUserProfile(currentUserProfile);
                        break;
                    case 2: // Remove goal
                        if (currentUserProfile.getHealthGoals().isEmpty()) {
                            System.out.println("\nNo goals to remove.");
                        } else {
                            int index = profileView.getGoalIndexToRemove(currentUserProfile.getHealthGoals().size());
                            if (index > 0 && index <= currentUserProfile.getHealthGoals().size()) {
                                String goalToRemove = currentUserProfile.getHealthGoals().get(index - 1);
                                currentUserProfile.removeHealthGoal(goalToRemove);
                                System.out.println("\nHealth goal removed successfully!");
                                FileHandler.saveUserProfile(currentUserProfile);
                            } else {
                                System.out.println("\nInvalid goal number.");
                            }
                        }
                        break;
                    case 3: // Return
                        managing = false;
                        break;
                    default:
                        mainView.displayInvalidChoice();
                }
            } catch (NumberFormatException e) {
                mainView.displayInvalidChoice();
            }

            if (managing) {
                mainView.pressEnterToContinue();
            }
        }
    }
    
    private void handleMealTracking() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n===== Meal Tracking =====");
            System.out.println("1. View today's meals");
            System.out.println("2. Add a new meal");
            System.out.println("3. Remove a meal");
            System.out.println("4. View meals by date");
            System.out.println("5. View calorie summary");
            System.out.println("6. Return to main menu");
            System.out.print("\nEnter your choice: ");

            try {
                int choice = Integer.parseInt(mainView.getUserInput());

                switch (choice) {
                    case 1: // View today's meals
                        LocalDate today = LocalDate.now();
                        profileView.displayMeals(currentUserProfile.getMealsByDate(today), today);
                        break;
                    case 2: // Add a new meal
                        Meal newMeal = profileView.getNewMeal();
                        if (newMeal != null) {
                            currentUserProfile.addMeal(newMeal);
                            System.out.println("\nMeal added successfully!");
                            FileHandler.saveUserProfile(currentUserProfile);
                        }
                        break;
                    case 3: // Remove a meal
                        LocalDate removeDate = LocalDate.now();
                        java.util.List<Meal> todaysMeals = currentUserProfile.getMealsByDate(removeDate);
                        int mealIndex = profileView.getMealIndexToRemove(todaysMeals);
                        
                        if (mealIndex > 0 && mealIndex <= todaysMeals.size()) {
                            Meal mealToRemove = todaysMeals.get(mealIndex - 1);
                            currentUserProfile.removeMeal(mealToRemove);
                            System.out.println("\nMeal removed successfully!");
                            FileHandler.saveUserProfile(currentUserProfile);
                        } else if (mealIndex != -1) {
                            System.out.println("\nInvalid meal number.");
                        }
                        break;
                    case 4: // View meals by date
                        System.out.print("\nEnter date (yyyy-MM-dd): ");
                        String dateStr = mainView.getUserInput();
                        try {
                            LocalDate date = LocalDate.parse(dateStr);
                            profileView.displayMeals(currentUserProfile.getMealsByDate(date), date);
                        } catch (Exception e) {
                            System.out.println("\nInvalid date format. Please use yyyy-MM-dd.");
                        }
                        break;
                    case 5: // View calorie summary
                        // Create a map of dates to total calories for the last 7 days
                        Map<LocalDate, Integer> caloriesByDate = new HashMap<>();
                        LocalDate endDate = LocalDate.now();
                        LocalDate startDate = endDate.minusDays(6); // Last 7 days including today
                        
                        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                            caloriesByDate.put(date, currentUserProfile.getTotalCaloriesByDate(date));
                        }
                        
                        profileView.displayCalorieSummary(caloriesByDate);
                        break;
                    case 6: // Return to main menu
                        managing = false;
                        break;
                    default:
                        mainView.displayInvalidChoice();
                }
            } catch (NumberFormatException e) {
                mainView.displayInvalidChoice();
            }

            if (managing) {
                mainView.pressEnterToContinue();
            }
        }
    }

    private void handleDietaryPreferences() {
        boolean managing = true;
        while (managing) {
            profileView.displayDietaryPreferences(currentUserProfile.getDietaryPreferences());
            System.out.println("\n1. Add a new dietary preference");
            System.out.println("2. Remove a dietary preference");
            System.out.println("3. Return to main menu");
            System.out.print("\nEnter your choice: ");

            try {
                int choice = Integer.parseInt(mainView.getUserInput());

                switch (choice) {
                    case 1: // Add preference
                        String newPreference = profileView.getNewDietaryPreference();
                        currentUserProfile.addDietaryPreference(newPreference);
                        System.out.println("\nDietary preference added successfully!");
                        FileHandler.saveUserProfile(currentUserProfile);
                        break;
                    case 2: // Remove preference
                        if (currentUserProfile.getDietaryPreferences().isEmpty()) {
                            System.out.println("\nNo preferences to remove.");
                        } else {
                            int index = profileView.getPreferenceIndexToRemove(currentUserProfile.getDietaryPreferences().size());
                            if (index > 0 && index <= currentUserProfile.getDietaryPreferences().size()) {
                                String preferenceToRemove = currentUserProfile.getDietaryPreferences().get(index - 1);
                                currentUserProfile.removeDietaryPreference(preferenceToRemove);
                                System.out.println("\nDietary preference removed successfully!");
                                FileHandler.saveUserProfile(currentUserProfile);
                            } else {
                                System.out.println("\nInvalid preference number.");
                            }
                        }
                        break;
                    case 3: // Return
                        managing = false;
                        break;
                    default:
                        mainView.displayInvalidChoice();
                }
            } catch (NumberFormatException e) {
                mainView.displayInvalidChoice();
            }

            if (managing) {
                mainView.pressEnterToContinue();
            }
        }
    }

}

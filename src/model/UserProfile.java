package model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class UserProfile {
    private User user;
    private Map<LocalDate, Double> weightHistory;
    private Map<LocalDate, BMI> bmiHistory;
    private List<String> healthGoals;
    private List<String> dietaryPreferences;
    private Map<LocalDate, List<Meal>> mealsByDate;

    public UserProfile(User user) {
        this.user = user;
        this.weightHistory = new HashMap<>();
        this.bmiHistory = new HashMap<>();
        this.healthGoals = new ArrayList<>();
        this.dietaryPreferences = new ArrayList<>();
        this.mealsByDate = new HashMap<>();

        // Initialize with current weight and BMI
        LocalDate today = LocalDate.now();
        weightHistory.put(today, user.getWeight());
        bmiHistory.put(today, new BMI(user));
    }

    public void updateWeight(double newWeight, LocalDate date) {
        // Update user's weight
        user.setWeight(newWeight);

        // Record in history
        weightHistory.put(date, newWeight);

        // Update BMI with new weight
        BMI updatedBMI = new BMI(user);
        bmiHistory.put(date, updatedBMI);
    }

    public void addHealthGoal(String goal) {
        healthGoals.add(goal);
    }

    public void removeHealthGoal(String goal) {
        healthGoals.remove(goal);
    }

    public void addDietaryPreference(String preference) {
        dietaryPreferences.add(preference);
    }

    public void removeDietaryPreference(String preference) {
        dietaryPreferences.remove(preference);
    }

    public Map<LocalDate, Double> getWeightHistory() {
        return weightHistory;
    }

    public Map<LocalDate, BMI> getBmiHistory() {
        return bmiHistory;
    }

    public List<String> getHealthGoals() {
        return healthGoals;
    }

    public List<String> getDietaryPreferences() {
        return dietaryPreferences;
    }

    public User getUser() {
        return user;
    }

    public BMI getCurrentBMI() {
        return new BMI(user);
    }

    public BMR getCurrentBMR() {
        return new BMR(user);
    }

    public String getProgressSummary() {
        if (weightHistory.size() <= 1) {
            return "Not enough data to show progress. Please update your weight regularly.";
        }

        List<LocalDate> dates = new ArrayList<>(weightHistory.keySet());
        dates.sort(null); // Sort dates in ascending order

        LocalDate firstDate = dates.get(0);
        LocalDate lastDate = dates.get(dates.size() - 1);

        double initialWeight = weightHistory.get(firstDate);
        double currentWeight = weightHistory.get(lastDate);
        double weightChange = currentWeight - initialWeight;

        BMI initialBMI = bmiHistory.get(firstDate);
        BMI currentBMI = bmiHistory.get(lastDate);

        StringBuilder summary = new StringBuilder();
        summary.append("Progress Summary:\n");
        summary.append(String.format("From %s to %s\n", firstDate, lastDate));
        summary.append(String.format("Initial weight: %.1f kg\n", initialWeight));
        summary.append(String.format("Current weight: %.1f kg\n", currentWeight));

        if (weightChange < 0) {
            summary.append(String.format("Weight loss: %.1f kg\n", Math.abs(weightChange)));
        } else if (weightChange > 0) {
            summary.append(String.format("Weight gain: %.1f kg\n", weightChange));
        } else {
            summary.append("Weight maintained\n");
        }

        if (initialBMI != null && currentBMI != null) {
            summary.append(String.format("Initial BMI: %.1f (%s)\n",
                    initialBMI.getBmiValue(), initialBMI.getBmiCategory()));
            summary.append(String.format("Current BMI: %.1f (%s)\n",
                    currentBMI.getBmiValue(), currentBMI.getBmiCategory()));
        }

        return summary.toString();
    }
    
    // Meal tracking methods
    
    /**
     * Adds a meal to the user's meal history
     * @param meal The meal to add
     */
    public void addMeal(Meal meal) {
        LocalDate mealDate = meal.getDate();
        if (!mealsByDate.containsKey(mealDate)) {
            mealsByDate.put(mealDate, new ArrayList<>());
        }
        mealsByDate.get(mealDate).add(meal);
    }
    
    /**
     * Removes a meal from the user's meal history
     * @param meal The meal to remove
     * @return true if the meal was removed, false otherwise
     */
    public boolean removeMeal(Meal meal) {
        LocalDate mealDate = meal.getDate();
        if (mealsByDate.containsKey(mealDate)) {
            return mealsByDate.get(mealDate).remove(meal);
        }
        return false;
    }
    
    /**
     * Gets all meals for a specific date
     * @param date The date to get meals for
     * @return A list of meals for the specified date
     */
    public List<Meal> getMealsByDate(LocalDate date) {
        return mealsByDate.getOrDefault(date, new ArrayList<>());
    }
    
    /**
     * Gets all meals within a date range
     * @param startDate The start date (inclusive)
     * @param endDate The end date (inclusive)
     * @return A map of dates to meals within the specified range
     */
    public Map<LocalDate, List<Meal>> getMealsByDateRange(LocalDate startDate, LocalDate endDate) {
        return mealsByDate.entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(startDate) && !entry.getKey().isAfter(endDate))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, TreeMap::new));
    }
    
    /**
     * Calculates the total calories consumed on a specific date
     * @param date The date to calculate calories for
     * @return The total calories consumed on the specified date
     */
    public int getTotalCaloriesByDate(LocalDate date) {
        List<Meal> meals = getMealsByDate(date);
        return meals.stream().mapToInt(Meal::getCalories).sum();
    }
    
    /**
     * Gets all meals by category for a specific date
     * @param date The date to get meals for
     * @param category The category to filter by
     * @return A list of meals matching the category for the specified date
     */
    public List<Meal> getMealsByCategory(LocalDate date, String category) {
        return getMealsByDate(date).stream()
                .filter(meal -> meal.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets all meals for the user
     * @return A map of dates to meals
     */
    public Map<LocalDate, List<Meal>> getAllMeals() {
        return new HashMap<>(mealsByDate);
    }
}
package controller;

import model.User;

public class BMR {
    private User user;
    private double bmrValue;
    private double dailyCalorieNeeds;

    public BMR(User user) {
        this.user = user;
        calculateBMR();
        calculateDailyCalorieNeeds();
    }

    private void calculateBMR() {
        if (user.getGender().equalsIgnoreCase("Male")) {
            this.bmrValue = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            this.bmrValue = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
        }
        this.bmrValue = Math.round(this.bmrValue);
    }

    private void calculateDailyCalorieNeeds() {
        double activityMultiplier = getActivityMultiplier(user.getActivityLevel());
        this.dailyCalorieNeeds = bmrValue * activityMultiplier;
        this.dailyCalorieNeeds = Math.round(this.dailyCalorieNeeds);
    }

    private double getActivityMultiplier(String activityLevel) {
        switch (activityLevel.toLowerCase()) {
            case "light":
                return 1.375;
            case "moderate":
                return 1.55;
            case "active":
                return 1.725;
            case "very active":
                return 1.9;
            default:
                return 1.2;
        }
    }

    public double getBmrValue() {
        return bmrValue;
    }

    public double getDailyCalorieNeeds() {
        return dailyCalorieNeeds;
    }

    public String getCalorieRecommendation() {
        StringBuilder recommendation = new StringBuilder();
        recommendation.append(String.format("Your Basal Metabolic Rate (BMR) is %.0f calories per day.\n", bmrValue));
        recommendation.append(String.format("Based on your activity level, you need approximately %.0f calories per day to maintain your current weight.\n", dailyCalorieNeeds));
        recommendation.append(String.format("To lose weight: Consume about %.0f calories per day.\n", dailyCalorieNeeds - 500));
        recommendation.append(String.format("To gain weight: Consume about %.0f calories per day.\n", dailyCalorieNeeds + 500));
        recommendation.append("\nRemember to focus on nutrient-dense foods and maintain a balanced diet.");

        return recommendation.toString();
    }
}
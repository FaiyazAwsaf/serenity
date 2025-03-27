package model;

public class BMI {
    private User user;
    private double bmiValue;
    private String bmiCategory;

    public BMI(User user) {
        this.user = user;
        calculateBMI();
        determineBMICategory();
    }

    private void calculateBMI() {
        // BMI formula: weight (kg) / (height (m))²
        double heightInMeters = user.getHeight() / 100; // Convert cm to meters
        this.bmiValue = user.getWeight() / (heightInMeters * heightInMeters);
        // Round to 1 decimal place
        this.bmiValue = Math.round(this.bmiValue * 10.0) / 10.0;
    }

    private void determineBMICategory() {
        if (bmiValue < 18.5) {
            this.bmiCategory = "Underweight";
        } else if (bmiValue < 25) {
            this.bmiCategory = "Normal weight";
        } else if (bmiValue < 30) {
            this.bmiCategory = "Overweight";
        } else {
            this.bmiCategory = "Obese";
        }
    }

    public double getBmiValue() {
        return bmiValue;
    }

    public String getBmiCategory() {
        return bmiCategory;
    }

    public String getHealthRecommendation() {
        switch (bmiCategory) {
            case "Underweight":
                return "Consider increasing your caloric intake with nutrient-dense foods and consult with a healthcare provider.";
            case "Normal weight":
                return "Maintain your healthy lifestyle with balanced diet and regular exercise.";
            case "Overweight":
                return "Focus on portion control, increased physical activity, and a balanced diet.";
            case "Obese":
                return "Consult with healthcare professionals for a personalized weight management plan.";
            default:
                return "Consult with a healthcare provider for personalized advice.";
        }
    }
}

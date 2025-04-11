package model;

import java.time.LocalDate;

public class Meal {
    private String name;
    private int calories;
    private String category;
    private LocalDate date;
    
    public Meal(String name, int calories, String category, LocalDate date) {
        this.name = name;
        this.calories = calories;
        this.category = category;
        this.date = date;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getCalories() {
        return calories;
    }
    
    public String getCategory() {
        return category;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s): %d calories", name, category, calories);
    }
}
package model;

public class User {
    private String username;
    private String password;
    private String name;
    private int age;
    private String gender;
    private double height;
    private double weight;
    private String activityLevel;

    public User(String username, String password, String name, int age, String gender, double height, double weight, String activityLevel) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

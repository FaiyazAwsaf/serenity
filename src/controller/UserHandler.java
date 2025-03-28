package controller;

import model.User;
import utils.Validators;
import utils.FileHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserHandler {

    private Scanner scanner = new Scanner(System.in);
    private Validators validators = new Validators();
    private Map<String, User> users;

    public UserHandler() {
        FileHandler.initializeFileSystem();

        users = FileHandler.loadUsers();

        if (users.isEmpty()) {
            User defaultUser = new User("user", "password123", "Test User", 30, "Male", 175.0, 70.0, "Moderate");
            users.put(defaultUser.getUsername(), defaultUser);
            FileHandler.saveUsers(users);
        }
    }

    public User registerUser() {
        System.out.println("\n===== User Registration =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        // Check if username already exists
        if (users.containsKey(username)) {
            System.out.println("\nUsername already exists. Please choose a different username.");
            return null;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        int age = validators.getValidAge();
        String gender = validators.getValidGender();
        double height = validators.getValidHeight();
        double weight = validators.getValidWeight();
        String activityLevel = validators.getValidActivityLevel();

        User newUser = new User(username, password, name, age, gender, height, weight, activityLevel);
        users.put(username, newUser);

        FileHandler.saveUsers(users);

        return newUser;
    }

    public boolean authenticate(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            return user.getPassword().equals(password);
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public boolean usernameExists(String username) {
        return users.containsKey(username);
    }

    // Method to get all users
    public Map<String, User> getAllUsers() {
        return users;
    }
}

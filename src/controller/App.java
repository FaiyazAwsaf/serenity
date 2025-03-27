package controller;

import model.User;

import java.util.Scanner;

public class App {

    private Scanner scanner = new Scanner(System.in);

    private void registerUser() {
        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        System.out.print("Gender (Male/Female): ");
        String gender = scanner.next();
        System.out.print("Height (cm): ");
        double height = scanner.nextDouble();
        System.out.print("Weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Activity level (Sedentary, Light, Moderate, Active, Very Active): ");
        String activityLevel = scanner.nextLine();

        User user = new User(username, password, name, age, gender, height, weight, activityLevel);

    }

    public boolean authenticate(String username, String password) {

//        System.out.println("Please enter your username: ");
//        String username = scanner.nextLine();
//        System.out.println("Please enter your password: ");
//        String password = scanner.nextLine();

        if (username.equals("user") && password.equals("password123")) {
            return true;
        }
        return false;
    }

}

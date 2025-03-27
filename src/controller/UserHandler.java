package controller;

import model.User;
import utils.Validators;

import java.util.Scanner;

public class UserHandler {

    private Scanner scanner = new Scanner(System.in);
    private Validators v = new Validators();

     void registerUser() {
        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        int age = v.getValidAge();
        String gender = v.getValidGender();
        double height = v.getValidHeight();
        double weight = v.getValidWeight();
        String activityLevel = v.getValidActivityLevel();

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

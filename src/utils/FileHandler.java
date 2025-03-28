package utils;

import model.User;
import model.UserProfile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileHandler {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.dat";
    private static final String PROFILES_DIR = DATA_DIR + File.separator + "profiles";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void initializeFileSystem() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        File profilesDir = new File(PROFILES_DIR);
        if (!profilesDir.exists()) {
            profilesDir.mkdir();
        }
    }

    public static void saveUsers(Map<String, User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                writer.println(String.format("%s,%s,%s,%d,%s,%.1f,%.1f,%s",
                        user.getUsername(),
                        user.getPassword(),
                        user.getName(),
                        user.getAge(),
                        user.getGender(),
                        user.getHeight(),
                        user.getWeight(),
                        user.getActivityLevel()));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public static Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        File file = new File(USERS_FILE);

        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    String username = parts[0];
                    String password = parts[1];
                    String name = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    String gender = parts[4];
                    double height = Double.parseDouble(parts[5]);
                    double weight = Double.parseDouble(parts[6]);
                    String activityLevel = parts[7];

                    User user = new User(username, password, name, age, gender, height, weight, activityLevel);
                    users.put(username, user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    public static void saveUserProfile(UserProfile profile) {
        String username = profile.getUser().getUsername();
        String profileFile = PROFILES_DIR + File.separator + username + ".profile";

        try (PrintWriter writer = new PrintWriter(new FileWriter(profileFile))) {
            writer.println("[WEIGHT_HISTORY]");
            for (Map.Entry<LocalDate, Double> entry : profile.getWeightHistory().entrySet()) {
                writer.println(entry.getKey().format(DATE_FORMATTER) + "," + entry.getValue());
            }

            writer.println("[HEALTH_GOALS]");
            for (String goal : profile.getHealthGoals()) {
                writer.println(goal);
            }

            writer.println("[DIETARY_PREFERENCES]");
            for (String preference : profile.getDietaryPreferences()) {
                writer.println(preference);
            }
        } catch (IOException e) {
            System.err.println("Error saving profile for " + username + ": " + e.getMessage());
        }
    }

    public static UserProfile loadUserProfile(User user) {
        UserProfile profile = new UserProfile(user);
        String username = user.getUsername();
        String profileFile = PROFILES_DIR + File.separator + username + ".profile";
        File file = new File(profileFile);

        if (!file.exists()) {
            return profile;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String section = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[") && line.endsWith("]")) {
                    section = line;
                    continue;
                }

                if (section == null || line.trim().isEmpty()) {
                    continue;
                }

                switch (section) {
                    case "[WEIGHT_HISTORY]":
                        String[] weightParts = line.split(",");
                        if (weightParts.length == 2) {
                            LocalDate date = LocalDate.parse(weightParts[0], DATE_FORMATTER);
                            double weight = Double.parseDouble(weightParts[1]);
                            profile.updateWeight(weight, date);
                        }
                        break;
                    case "[HEALTH_GOALS]":
                        profile.addHealthGoal(line);
                        break;
                    case "[DIETARY_PREFERENCES]":
                        profile.addDietaryPreference(line);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading profile for " + username + ": " + e.getMessage());
        }

        return profile;
    }

    public static void saveAllProfiles(Map<String, UserProfile> profiles) {
        for (UserProfile profile : profiles.values()) {
            saveUserProfile(profile);
        }
    }
}
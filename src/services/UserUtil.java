package services;

import java.io.*;

public class UserUtil {
    // Updated method to validate username, password AND role
    public static boolean validateUser(String username, String password, String role) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 &&
                    parts[0].equalsIgnoreCase(username) &&
                    parts[1].equals(password) &&
                    parts[2].equalsIgnoreCase(role)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean registerUser(String username, String password, String role) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
        writer.write(username + "," + password + "," + role);
        writer.newLine();
        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
    }

}

package main.java.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String username;
    private String passwordHash;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    public static User fromString(String line) {
        String[] parts = line.split(":");
        if (parts.length == 2) {
            User user = new User(parts[0], "");
            user.passwordHash = parts[1]; // Directly set the password hash without hashing again
            return user;
        }
        return null;
    }

    @Override
    public String toString() {
        return username + ":" + passwordHash; // Format: username:passwordHash
    }
}

package model;

import java.security.SecureRandom;
import java.util.Base64;

public class User {
    private String username;
    private String passwordHash;
    private String salt;

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.salt = generateSalt(); // Generate salt when creating a user
        this.passwordHash = hashPassword(password, this.salt); // Hash password with the salt
    }

    // Public method to generate a salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    // Static method to hash a password with a given salt
    public static String hashPassword(String password, String salt) {
        // Implement your hashing logic here (e.g., using SHA-256 or bcrypt)
        return password + salt; // Replace this with actual hashing logic
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }
}

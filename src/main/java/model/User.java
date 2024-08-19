package main.java.model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class User {
    private String username;
    private String passwordHash;
    private String salt;

    private static final int SALT_LENGTH = 16; // 16 bytes for salt
    private static final int HASH_ITERATIONS = 10000; // Number of iterations for PBKDF2
    private static final int KEY_LENGTH = 256; // Length of the derived key in bits

    public User(String username, String password) {
        this.username = username;
        this.salt = generateSalt();
        this.passwordHash = hashPassword(password, salt);
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public static String hashPassword(String password, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), HASH_ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static User fromString(String line) {
        String[] parts = line.split(":");
        if (parts.length == 3) {
            User user = new User(parts[0], "");
            user.salt = parts[2];
            user.passwordHash = parts[1];
            return user;
        }
        return null;
    }

    @Override
    public String toString() {
        return username + ":" + passwordHash + ":" + salt; // Format: username:passwordHash:salt
    }
}

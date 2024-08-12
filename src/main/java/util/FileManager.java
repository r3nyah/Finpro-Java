package main.java.util;

import java.io.*;
import java.util.LinkedList;
import main.java.model.User;
import main.java.service.HashTable;

public class FileManager {
    private static final String FILE_NAME = "users.txt";
    private static final String FILE_PATH = "src/main/resources/" + FILE_NAME;

    public void loadUsers(HashTable hashTable) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File not found: " + FILE_PATH);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Reading line: " + line); // Debugging line
                User user = User.fromString(line);
                if (user != null) {
                    System.out.println("Loaded user: " + user.getUsername()); // Debugging line
                    hashTable.insert(user);
                } else {
                    System.out.println("Failed to parse user from line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading users.");
            e.printStackTrace();
        }
    }

    public void saveUsers(HashTable hashTable) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < hashTable.getCapacity(); i++) {
                LinkedList<User> chain = hashTable.getChain(i);
                if (chain != null) {
                    for (User user : chain) {
                        writer.write(user.toString());
                        writer.newLine();
                    }
                }
            }
            System.out.println("Users saved to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving users.");
            e.printStackTrace();
        }
    }
}

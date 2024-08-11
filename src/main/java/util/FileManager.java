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
                User user = User.fromString(line);
                hashTable.insert(user);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading users.");
            e.printStackTrace();
        }
    }

    public void saveUsers(HashTable hashTable) {
        File file = new File(FILE_PATH);
        // Ensure the parent directory exists
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < hashTable.getCapacity(); i++) {
                LinkedList<User> chain = hashTable.getChain(i);
                if (chain != null) {
                    for (User user : chain) {
                        writer.write(user.toString());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving users.");
            e.printStackTrace();
        }
    }
}

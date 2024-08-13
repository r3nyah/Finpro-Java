package main.java.util;

import main.java.model.User;
import main.java.service.HashTable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private static final String FILE_NAME = "users.txt";

    public void loadUsers(HashTable hashTable) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) {
                    hashTable.insert(user);
                }
            }
        } catch (IOException e) {
            CenterScreen.centerPrint("Error loading users: " + e.getMessage());
        }
    }

    public void saveUsers(HashTable hashTable) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < hashTable.getCapacity(); i++) {
                for (User user : hashTable.getChain(i)) {
                    writer.write(user.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            CenterScreen.centerPrint("Error saving users: " + e.getMessage());
        }
    }
}

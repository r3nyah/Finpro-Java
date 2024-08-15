package main.java.util;

import main.java.model.User;
import main.java.service.HashTable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    // Use an absolute path to avoid confusion with relative paths
    private static final String FILE_PATH = "D:/Telkom/Java-Projects/Finpro/Finpro-Java/src/main/java/resources/User.txt";
    //private static final String FILE_PATH = "src/main/java/resources/User.txt";

    public void loadUsers(HashTable hashTable) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) {
                    hashTable.insert(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers(HashTable hashTable) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < hashTable.getCapacity(); i++) {
                for (User user : hashTable.getChain(i)) {
                    writer.write(user.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

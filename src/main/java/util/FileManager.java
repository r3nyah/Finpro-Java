package main.java.util;

import java.io.*;
import java.util.LinkedList;
import main.java.model.User;
import main.java.service.HashTable;
import main.java.util.CenterScreen;

public class FileManager {
    private static final String FILE_NAME = "users.txt";
    private static final String FILE_PATH = "src/main/resources/" + FILE_NAME;
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println("Reading line: " + line); // Debugging line
//                User user = User.fromString(line);
//                if (user != null) {
//                    System.out.println("Loaded user: " + user.getUsername()); // Debugging line
//                    hashTable.insert(user);
//                } else {
//                    System.out.println("Failed to parse user from line: " + line);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred while loading users.");
//            e.printStackTrace();
//        }
        public void loadUsers(HashTable hashTable) {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                CenterScreen.centerPrint("User data file not found. A new file will be created upon saving.");
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        User user = new User(parts[0], parts[1]);
                        hashTable.insert(user);
                    }
                }
            } catch (IOException e) {
                CenterScreen.centerPrint("Error loading users: " + e.getMessage());
            }
        }

        public void saveUsers(HashTable hashTable) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (int i = 0; i < hashTable.getCapacity(); i++) {
                    LinkedList<User> chain = hashTable.getChain(i);
                    if (chain != null) {
                        for (User user : chain) {
                            bw.write(user.getUsername() + ":" + user.getPasswordHash());
                            bw.newLine();
                        }
                    }
                }
            } catch (IOException e) {
                CenterScreen.centerPrint("Error saving users: " + e.getMessage());
            }
        }
    }
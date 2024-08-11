import java.io.*;
import java.util.List;
import java.util.LinkedList;

public class FileManager {
    private static final String FILE_NAME = "users.txt";

    public void loadUsers(HashTable hashTable) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                hashTable.insert(user);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading users.");
        }
    }

    public void saveUsers(HashTable hashTable) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < hashTable.getSize(); i++) {
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
        }
    }
}
